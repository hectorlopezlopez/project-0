package com.DEPI.Util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.DEPI.DAO.UserDAO;
import com.DEPI.Model.User;

public class Hashing {

    UserDAO userDAO;

    public Hashing() {
        this.userDAO = new UserDAO();
    }

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }


    public static boolean comparePasswords(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }

    private String getHashedPassword(String password) {

        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
