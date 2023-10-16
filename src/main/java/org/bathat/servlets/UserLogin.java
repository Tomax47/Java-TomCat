package org.bathat.servlets;
import org.bathat.repository.AccountRepository;
import org.bathat.repository.AccountRepositoryJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// THIS IS THE LOGIN SERVELT FOR THE LAST "MINIMAL" HW

@WebServlet("/login")
public class UserLogin extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";

    private AccountRepository accountRepository;

    @Override
    public void init() throws ServletException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            this.accountRepository = new AccountRepositoryJDBC(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/userlogin.html").forward(request,response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            int resp = accountRepository.login(email, password,request);
            if (resp == 1) {
                // Case 1
//                response.sendRedirect("/minimal");
                response.getWriter().println("Welcome "+email);
            } else {
                // Case 0
                response.sendRedirect("/login");
            }

            // Exception case!
        } catch (SQLException e) {
            response.getWriter().println("Something went wrong!");
            response.sendRedirect("/login");
            throw new RuntimeException(e);
        }
    }
}