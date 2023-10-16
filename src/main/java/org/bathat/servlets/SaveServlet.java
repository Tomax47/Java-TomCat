package org.bathat.servlets;

import org.bathat.models.User;
import org.bathat.repository.AccountRepository;
import org.bathat.repository.AccountRepositoryJDBC;
import org.postgresql.jdbc.PreferQueryMode;

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


// REGISTER SERVLET FOR THE "MINIMAL" HW

@WebServlet("/save")
public class SaveServlet extends HttpServlet {

    private AccountRepository accountRepository;

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";

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
            accountRepository = new AccountRepositoryJDBC(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/save.html").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int age = Integer.parseInt(req.getParameter("age"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = User.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .email(email)
                .password(password)
                .build();

        try {
            accountRepository.save(user);
            resp.sendRedirect("/minimal");
        } catch (SQLException e) {
            resp.sendRedirect("/save");
            throw new RuntimeException(e);
        }
    }
}
