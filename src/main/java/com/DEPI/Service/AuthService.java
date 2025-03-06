package com.DEPI.Service;

import com.DEPI.DAO.AuthDAO;
import com.DEPI.Model.User;


public class AuthService {

    AuthDAO authDAO ;

    public AuthService() {
        this.authDAO = new AuthDAO();
    }

    public   User getUserFromDB(String mail) {
    return this.authDAO.getUserFromDB(mail);
    }
}
