package com.DEPI.DAO;

import com.DEPI.DTO.RequestApplicationLoanDTO;
import com.DEPI.DTO.RequestUserApplicationLoanDTO;
import com.DEPI.Model.User;
import com.DEPI.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    Connection connection ;

    public UserDAO() {

    }

    public List<User> getAllUsers(){
        connection=ConnectionUtil.getConnection();
        List<User> users = new ArrayList<>();
        try {
            //Write  SQL logic here
            String sql = "SELECT*FROM profile;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                User user = new User(rs.getInt("id_user"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getString("password_user"),
                        rs.getInt("id_rol"));
                users.add(user);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    public User getUserById(int id_user) {
        connection = ConnectionUtil.getConnection();
        User user = null;

        String sql = "SELECT * FROM profile WHERE id_user = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_user);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getString("password_user"),
                        rs.getInt("id_rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public RequestUserApplicationLoanDTO getLoansByUser(int id_user) {
        connection = ConnectionUtil.getConnection();

        RequestUserApplicationLoanDTO loanapplication = null;

        String sql = "select p.name,p.last_name,p.address,la.amount,la.interest,la.date_application from profile p inner join loan_applications la on p.id_user = la.id_user where p.id_user =?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_user);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loanapplication = new RequestUserApplicationLoanDTO(
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getDouble("amount"),
                        rs.getShort("interest"),
                        rs.getDate("date_application").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanapplication;
    }

    public String getPasswordById(User user) {
        connection = ConnectionUtil.getConnection();

        String sql = "SELECT password_user FROM profile WHERE id_user = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("password_user")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.getPassword();
    }


    public boolean createUser(User user){
        connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO profile (name,last_name,phone,address,mail,password_user,id_rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getMail());
            stmt.setString(6, user.getPassword());
            stmt.setInt(7, user.getRol());
            int nr=stmt.executeUpdate();
            if(nr>0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        user.setId(newId);
                    }
                }
                return true;
            }else {
            return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user){
        connection = ConnectionUtil.getConnection();
        String sql = "UPDATE profile SET name = ?,last_name = ?,phone = ?,address = ?,mail = ?,password_user = ?,id_rol = ? WHERE id_user = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getMail());
            stmt.setString(6, user.getPassword());
            stmt.setInt(7, user.getRol());
            stmt.setInt(8, user.getId());
            int nr=stmt.executeUpdate();
            if(nr>0){ return true;}else {return false;}


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMyUser(User user) {
        Connection connection = ConnectionUtil.getConnection();
        StringBuilder sql = new StringBuilder("UPDATE profile SET ");
        List<Object> params = new ArrayList<>();

        if (user.getName() != null) {
            sql.append("name = ?, ");
            params.add(user.getName());
        }
        if (user.getLastName() != null) {
            sql.append("last_name = ?, ");
            params.add(user.getLastName());
        }
        if (user.getPhone() != null) {
            sql.append("phone = ?, ");
            params.add(user.getPhone());
        }
        if (user.getAddress() != null) {
            sql.append("address = ?, ");
            params.add(user.getAddress());
        }
        if (user.getMail() != null) {
            sql.append("mail = ?, ");
            params.add(user.getMail());
        }
        if (user.getPassword() != null) {
            sql.append("password_user = ?, ");
            params.add(user.getPassword());
        }
        if (user.getRol() != 0) {
            sql.append("id_rol = ?, ");
            params.add(user.getRol());
        }

        if (params.isEmpty()) {
            return false;
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id_user = ?");
        params.add(user.getId());

        try (
             PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }


    public boolean deleteUser(User user){
        connection = ConnectionUtil.getConnection();
        String sql = "DELETE FROM profile WHERE id_user = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, user.getId());
            int nr=stmt.executeUpdate();
            if(nr>0){ return true;}else {return false;}

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
