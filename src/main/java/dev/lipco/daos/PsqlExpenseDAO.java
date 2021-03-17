package dev.lipco.daos;

import dev.lipco.entities.*;
import dev.lipco.utils.ConnectionUtil;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.Set;
import java.util.HashSet;

public class PsqlExpenseDAO implements ExpenseDAO{

    static Logger logger = Logger.getLogger(PsqlExpenseDAO.class.getName());

    @Override
    public Expense createExpense(Expense expense){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "insert into expense (amount, expense_comments, requester_id) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setBigDecimal(1, expense.getAmount());
            ps.setString(2, expense.getExpenseComments());
            ps.setInt(3, expense.getRequester());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            expense.setExpenseId(rs.getInt("expense_id"));
            return expense;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Expense getExpenseById(int expenseId){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "select a.username, e.* from avengers a join expense e on a.id = e.requester_id where e.expense_id = ? ";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, expenseId);
            ResultSet rs = ps.executeQuery();
            Expense exp = new Expense();
            exp.setUsername(rs.getString("username"));
            exp.setExpenseId(rs.getInt("expense_id"));
            exp.setAmount(rs.getBigDecimal("amount"));
            exp.setExpenseComments(rs.getString("expense_comments"));
            exp.setStatus(rs.getString("expense_status"));
            exp.setCreatedTime(rs.getTimestamp("created_at"));
            exp.setRequester(rs.getInt("requester_id"));
            exp.setDecisionTime(rs.getTimestamp("decision_at"));
            exp.setReviewerComments(rs.getString("decision_comments"));
            exp.setReviewer(rs.getInt("reviewer_id"));
            return exp;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Expense> getAllExpenses(){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "select a.username, e.* from avengers a join expense e on a.id = e.requester_id";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            Set<Expense> expenses = new HashSet<>();
            while(rs.next()) {
                Expense exp = new Expense();
                exp.setUsername(rs.getString("username"));
                exp.setExpenseId(rs.getInt("expense_id"));
                exp.setAmount(rs.getBigDecimal("amount"));
                exp.setExpenseComments(rs.getString("expense_comments"));
                exp.setStatus(rs.getString("expense_status"));
                exp.setCreatedTime(rs.getTimestamp("created_at"));
                exp.setRequester(rs.getInt("requester_id"));
                exp.setDecisionTime(rs.getTimestamp("decision_at"));
                exp.setReviewerComments(rs.getString("decision_comments"));
                exp.setReviewer(rs.getInt("reviewer_id"));
                expenses.add(exp);
            }
            return expenses;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Expense updateExpense(Expense expense){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "update expense set status = ?, reviewer_id = ?, decision_at = ?, decision_comments = ? where expense_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, expense.getStatus());
            ps.setInt(2, expense.getReviewer());
            ps.setTimestamp(3, expense.getDecisionTime());
            ps.setString(4, expense.getReviewerComments());
            ps.setInt(5, expense.getExpenseId());
            int rs = ps.executeUpdate();
            return rs > 0 ? expense : null;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Avenger getAvenger(Avenger avenger){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "select a.username, e.* from avengers a join expense e on a.id = e.requester_id where a.id = ? ";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, avenger.getId());
            ResultSet rs = ps.executeQuery();

            HashSet<Expense> expenses = new HashSet<>();
            while(rs.next()) {
                Expense exp = new Expense();
                exp.setUsername(rs.getString("username"));
                exp.setExpenseId(rs.getInt("expense_id"));
                exp.setAmount(rs.getBigDecimal("amount"));
                exp.setExpenseComments(rs.getString("expense_comments"));
                exp.setStatus(rs.getString("expense_status"));
                exp.setCreatedTime(rs.getTimestamp("created_at"));
                exp.setRequester(rs.getInt("requester_id"));
                exp.setDecisionTime(rs.getTimestamp("decision_at"));
                exp.setReviewerComments(rs.getString("decision_comments"));
                exp.setReviewer(rs.getInt("reviewer_id"));
                expenses.add(exp);
            }

            avenger.setMemberExpenses(expenses);
            return avenger;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Avenger checkLogin(LoginCredentials loginCredentials){
        try(Connection conn = ConnectionUtil.makeConnection()){
            String query = "select id, username, is_manager from avengers where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loginCredentials.getUsername());
            ps.setString(2, loginCredentials.getPassword());
            ResultSet rs = ps.executeQuery();
            Avenger user = new Avenger();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setManager(rs.getBoolean("is_manager"));
            return user;
        }catch(SQLException e){
            logger.error(e.getMessage());
            return null;
        }
    }

}
