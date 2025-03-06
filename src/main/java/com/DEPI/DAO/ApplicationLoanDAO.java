package com.DEPI.DAO;

import com.DEPI.Controller.ApplicationLoanController;
import com.DEPI.Model.ApplicationLoan;
import com.DEPI.Model.Loan;
import com.DEPI.Util.ConnectionUtil;
import com.DEPI.Util.LoanStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationLoanDAO {
    Connection connection ;

    public ApplicationLoanDAO() {
    }

    public List<ApplicationLoan>getAllLoansApplications(){
        connection=ConnectionUtil.getConnection();
        List<ApplicationLoan> loans = new ArrayList<>();
        try {
            String sql = "SELECT*FROM loan_applications;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ApplicationLoan loan = new ApplicationLoan(
                        rs.getInt("id_application"),
                        rs.getDouble("amount"),
                        rs.getShort("interest"),
                        rs.getDate("date_application").toLocalDate(),
                        LoanStatus.valueOf(rs.getString("status")),
                        rs.getInt("id_loan"),
                        rs.getInt("id_user")
                );
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }

    public List<ApplicationLoan> getApplicationByUser(int idUser){
        connection = ConnectionUtil.getConnection();
        List<ApplicationLoan> applicationLoans = new ArrayList<>();

            String sql = "select * from loan_applications la inner join loan l on la.id_application = l.id_loan where la.id_user = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1,idUser);
                ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                ApplicationLoan applicationLoan = new ApplicationLoan(
                        rs.getInt("id_application"),
                        rs.getDouble("amount"),
                        rs.getShort("interest"),
                        rs.getDate("date_application").toLocalDate(),
                        LoanStatus.valueOf(rs.getString("status")),
                        rs.getInt("id_loan"),
                        rs.getInt("id_user")
                );
                applicationLoans.add(applicationLoan);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return applicationLoans;
    }


    public boolean createLoan(ApplicationLoan loanApplication){
        connection=ConnectionUtil.getConnection();
        String sql = "INSERT INTO loan_applications (amount,interest,date_application,id_loan,id_user) VALUES ( ?, ?, ?, ?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1,loanApplication.getAmount());
            stmt.setShort(2,loanApplication.getInterest());
            stmt.setDate(3, java.sql.Date.valueOf(loanApplication.getDate_application()));
            stmt.setInt(4,loanApplication.getId_loan());
            stmt.setInt(5,loanApplication.getId_user());

            int nr=stmt.executeUpdate();
            if(nr>0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        loanApplication.setId_loan(newId);
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

    public boolean updateLoanApplication(ApplicationLoan loanApplication){
        connection=ConnectionUtil.getConnection();
        String sql = "UPDATE loan_applications SET amount = ?, interest = ?,date_application = ?, id_loan = ?, id_user = ? WHERE id_application = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1,loanApplication.getAmount());
            stmt.setShort(2,loanApplication.getInterest());
            stmt.setDate(3, java.sql.Date.valueOf(loanApplication.getDate_application()));
            stmt.setInt(4,loanApplication.getId_loan());
            stmt.setInt(5,loanApplication.getId_user());
            stmt.setInt(6,loanApplication.getId_application());
            int nr=stmt.executeUpdate();
            if(nr>0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        loanApplication.setId_loan(newId);
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

    public boolean deleteLoanApplication(ApplicationLoan applicationLoan){

        connection=ConnectionUtil.getConnection();

        String sql = "DELETE FROM loan_applications WHERE id_application = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(applicationLoan);

            stmt.setInt(1,applicationLoan.getId_application());

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int nr= preparedStatement.executeUpdate();
           if(nr>0){
               return true;
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
