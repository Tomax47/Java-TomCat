package org.bathat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/profile")
public class TestServlet extends HttpServlet {

    private static final String INSERT_USER = "INSERT INTO students_info (email, password) VALUES (?, ?)";
    private static final String FIND_USER = "SELECT * FROM students_info WHERE email = ? AND password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM students_info";

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/students";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("get_users")) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        StringBuilder userList = new StringBuilder();
                        while (resultSet.next()) {
                            String email = resultSet.getString("email");
                            String password = resultSet.getString("password");
                            userList.append("<li>").append(email).append(" ").append(password).append("</li>");
                        }

                        String usersHtml = "<html><body><h1>Users in Database</h1><ul>" + userList.toString() + "</ul></body></html>";
                        response.getWriter().println(usersHtml);
                    }
                }
            } catch (SQLException e) {
                throw new ServletException("Database access failed", e);
            }
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("register")) {
                // Registration req
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (email.length() > 10 && password.length() > 4) {
                    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
                            statement.setString(1, email);
                            statement.setString(2, password);
                            statement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        throw new ServletException("Database access failed", e);
                    }
                    response.getWriter().println("Registration successful!");
                } else {
                    response.getWriter().println("Invalid email or password. Please try again.");
                }
            } else if (action.equals("login")) {
                // Login req
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    try (PreparedStatement statement = connection.prepareStatement(FIND_USER)) {
                        statement.setString(1, email);
                        statement.setString(2, password);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                response.getWriter().println("Welcome, " + email + "!");
                            } else {
                                response.getWriter().println("Invalid credentials. Please try again.");
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new ServletException("Database access failed", e);
                }
            }
        }
    }

}
