package com.DEPI.DAO;

import com.DEPI.Model.Loan;
import com.DEPI.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    public List<Loan> getAllLoans() {
        String sql = "SELECT * FROM loan;";
        List<Loan> loans = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id_loan"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getShort("interest_base")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching loans", e);
        }
        return loans;
    }

    public boolean createLoan(Loan loan) {
        String sql = "INSERT INTO loan (name, description, interest_base) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, loan.getName());
            stmt.setString(2, loan.getDescription());
            stmt.setShort(3, loan.getInterest_base());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        loan.setId_loan(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating loan", e);
        }
        return false;
    }
}
