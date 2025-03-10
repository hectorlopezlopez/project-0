package com.DEPI.Controller;

import com.DEPI.DTO.RequestUserApplicationLoanDTO;
import com.DEPI.DTO.RequestUserDTO;
import com.DEPI.Model.User;
import com.DEPI.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserController {
    AuthController authController;
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(){
        this.userService = new UserService();
        this.authController = new AuthController();
    }

    public void registerRoutes(Javalin app) {
        app.post("/user", this::createUser);
        app.patch("/updatemyuser", this::updateMyUser);
        app.get("/users", this::getUsers);
        app.get("/user/{id_user}", this::getUserById);
        app.get("/user", this::getMyUserInfoById);
        app.get("/userloanapplication/{id_user}", this::getLoansByUser);
        app.delete("/user/{id_user}", this::deleteUser);
        app.put("/updateuser/{id_user}", this::updateUser);
    }



    public void getUsers(Context ctx){

        if(authController.checkLogin(ctx)) {
            ctx.json(userService.getAllUsers());
        }

    }

    public void getUserById(Context ctx) {
        if (authController.checkLogin(ctx)) {
            if(authController.getRole(ctx)==1) {
                int userId = Integer.parseInt(ctx.pathParam("id_user"));
                User user = userService.getUserById(userId);
                if (user != null) {
                    ctx.json(user);
                } else {
                    ctx.status(404).result("User not found");
                }
            }else {
                    ctx.status(400).result("You cannot get an specific user because you are not a manager");
                }
        } else {
            ctx.status(401).result("not logged in");
        }
    }

    public void getMyUserInfoById(Context ctx) {
        if (authController.checkLogin(ctx)) {
                int userId = authController.getUserId(ctx);
                User user = userService.getUserById(userId);
                if (user != null) {
                    ctx.json(user);
                } else {
                    ctx.status(404).result("Your information cannot be found");
                }
        } else {
            ctx.status(401).result("not logged in");
        }
    }

    public void updateMyUser(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }

        int userId = authController.getUserId(ctx);
        RequestUserDTO userDto = ctx.bodyAsClass(RequestUserDTO.class);

        String result = userService.updateMyUser(userId, userDto);

        if (result.equals("User updated")) {
            ctx.status(200).json("{\"message\": \"User updated successfully\"}");
        } else {
            ctx.status(400).json("{\"error\": result}");
        }
    }



    public void getLoansByUser(Context ctx) {
        if (authController.checkLogin(ctx)) {
            int userId = Integer.parseInt(ctx.pathParam("id_user"));
            RequestUserApplicationLoanDTO requestUserApplicationLoanDTO = userService.getLoansByUser(userId);
            if (requestUserApplicationLoanDTO != null) {
                ctx.json(requestUserApplicationLoanDTO);
            } else {
                ctx.status(404).result("Application not found");
            }
        } else {
            ctx.status(401).result("not logged in");
        }
    }


    public void createUser(Context ctx) throws JsonProcessingException {
        if(authController.checkLogin(ctx)) {
            RequestUserDTO userDto = ctx.bodyAsClass(RequestUserDTO.class);
            if (userDto.getName().trim().isEmpty()
                    && userDto.getLastName().isEmpty()
                    && userDto.getAddress().trim().isEmpty()
                    && userDto.getPassword().trim().isEmpty()
                    && userDto.getPhone().trim().isEmpty()
                    && userDto.getMail().trim().isEmpty()) {
                logger.error("Failed to create an user by missing data : {}", authController.getUserId(ctx));
                ctx.status(400).json("{\"error\":\"Missing data\"}");

                return;
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setLastName(userDto.getLastName());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());
            user.setMail(userDto.getMail());
            user.setPassword(userDto.getPassword());
            user.setRol(userDto.getRol());

            if (userService.createUser(user)) {
                ctx.status(200).json("User created");
                logger.info("User create: {}", user.getId()+" with name "+user.getName());
            } else {
                ctx.status(500).json("Something went wrong with creating the user");
                logger.info("User create: {}", user.getId()+" with name "+user.getName());
            }
        }

    }

    public void updateUser (Context ctx) {
        if(authController.checkLogin(ctx)) {
            if (authController.getRole(ctx) == 1) {
                int userId = Integer.parseInt(ctx.pathParam("id_user"));
                RequestUserDTO userDto = ctx.bodyAsClass(RequestUserDTO.class);
                if (userDto.getName().trim().isEmpty()
                        && userDto.getLastName().isEmpty()
                        && userDto.getAddress().trim().isEmpty()
                        && userDto.getPassword().trim().isEmpty()
                        && userDto.getPhone().trim().isEmpty()
                        && userDto.getMail().trim().isEmpty()) {
                    logger.error("Failed to update an user by missing data : {}", authController.getUserId(ctx));
                    ctx.status(400).json("{\"error\":\"Missing data\"}");

                    return;
                }

                User user = new User();
                user.setName(userDto.getName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
                user.setAddress(userDto.getAddress());
                user.setMail(userDto.getMail());
                user.setPassword(userDto.getPassword());
                user.setRol(userDto.getRol());
                user.setId(userId);

                if (userService.updateUser(user)) {
                    ctx.status(200).json("User updated->" + user.getName());
                    logger.info("User updated: {}", user.getId() + " with name " + user.getName());
                } else {
                    ctx.status(500).json("Something wrong with updating user->" + user.getName());
                    logger.error("Failed to update an user : {}", authController.getUserId(ctx));
                }


            } else {
                ctx.status(400).result("You cannot update an specific user because you are not a manager");
            }
        }else {
                ctx.status(401).result("not logged in");
            }
    }

    public void deleteUser (Context ctx) {
        if(authController.checkLogin(ctx)) {
            int userId = Integer.parseInt(ctx.pathParam("id_user"));
            if (userId==0) {
                ctx.status(400).json("{\"error\":\"Missing data\"}");

                return;
            }

            User user = new User();
            user.setId(userId);
            if (userService.deleteUser(user)) {
                ctx.status(200).json("User deleted");
            } else {
                ctx.status(500).json("Something wrong with deleting user");
            }

        }
    }


}
