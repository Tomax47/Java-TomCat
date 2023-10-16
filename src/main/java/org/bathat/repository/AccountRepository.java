package org.bathat.repository;

import org.bathat.models.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

// MINIMAL HW

public interface AccountRepository {

    void save(User user) throws SQLException;

    int login(String email, String password, HttpServletRequest request) throws SQLException;

}
