package com.DEPI.DAO;

import com.DEPI.Model.Loan;
import com.DEPI.Model.User;
import com.DEPI.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    Connection connection = ConnectionUtil.getConnection();

    public LoanDAO() {
    }

    public List<Loan> getAllLoans(){

        List<Loan> loans = new ArrayList<>();
        try {
            String sql = "SELECT*FROM loan;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Loan loan = new Loan(rs.getInt("id_loan"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getShort("interest_base")
                        );
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }
    public boolean createLoan(Loan loan){
        String sql = "INSERT INTO loan (name,description,interest_base) VALUES ( ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, loan.getName());
            stmt.setString(2, loan.getDescription());
            stmt.setShort(3, loan.getInterest_base());
            int nr=stmt.executeUpdate();
            if(nr>0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        loan.setId_loan(newId);
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
}
