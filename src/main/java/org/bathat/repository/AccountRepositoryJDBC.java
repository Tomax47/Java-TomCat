package org.bathat.repository;

import org.bathat.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;


// MINIMAL HW
public class AccountRepositoryJDBC implements AccountRepository {

    private static final String SQL_INSERT = "INSERT INTO users_table(name,surname,age,email,password) VALUES ";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM users_table WHERE email=";

    private final Connection connection;
    private Statement statement;

    public AccountRepositoryJDBC(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql_insert_final = SQL_INSERT + "(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_insert_final);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3,user.getAge());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());

        preparedStatement.executeUpdate();
        System.out.println("Done!");

    }

    @Override
    public int login(String email, String password, HttpServletRequest request) {

        User user = findUserByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {

                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                return 1;
            } else {
                System.out.println("Incorrect user credentials!");
            }
        } else {
            System.out.println("User ain't been found!");
        }
        return 0;
    }


    public User findUserByEmail(String email) {

        User user = null;
        try {

            String USER_BY_EMAIL = SQL_SELECT_BY_EMAIL+"'"+email+"'";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USER_BY_EMAIL);

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
            }

            if (user == null) {
                System.out.println("No user has been found by the email "+email);
            }
            System.out.println("The user "+email+" has been found!");
            return user;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
