package com.DEPI.Service;

import com.DEPI.DAO.UserDAO;
import com.DEPI.DTO.RequestUserApplicationLoanDTO;
import com.DEPI.Model.User;
import com.DEPI.Util.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public List<User> getAllUsers() {
        logger.info("Searching all users:");
        return this.userDAO.getAllUsers();
    }

    public User getUserById(int id_user) {
        logger.info("Searching user with ID: {}", id_user);
        return this.userDAO.getUserById(id_user);
    }

    public RequestUserApplicationLoanDTO getLoansByUser(int id_user) {
        return this.userDAO.getLoansByUser(id_user);
    }

    public boolean createUser(User user) {
        logger.info("User created: {}", user.getName());
        //String password = hashPassword(user.getPassword());
        String password = Hashing.hashPassword(user.getPassword());
        user.setPassword(password);
        return userDAO.createUser(user);
    }

    public boolean updateUser(User user) {
        logger.info("User updated: {}", user.getName());
        //String password = hashPassword(user.getPassword());
        String password = Hashing.hashPassword(user.getPassword());
        user.setPassword(password);
        return this.userDAO.updateUser(user);
    }
    public boolean updateMyUser(User user) {
        logger.info("User updated: {}", user.getName());
        if(user.getPassword()!=null){
        String password = Hashing.hashPassword(user.getPassword());
        user.setPassword(password);
        }
        return this.userDAO.updateMyUser(user);
    }

    public boolean deleteUser(User user) {
        logger.info("User deleted: {}", user.getName());
        return this.userDAO.deleteUser(user);
    }
}


