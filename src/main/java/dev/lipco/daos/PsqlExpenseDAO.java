package dev.lipco.daos;

import dev.lipco.entities.Expense;
import dev.lipco.utils.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.Set;
import java.util.HashSet;

public class PsqlExpenseDAO implements ExpenseDAO{

    public enum Status {
        pending,
        denied,
        approved;
    }


    @Override
    public Expense createExpense(int requester, BigDecimal amount, String expenseComments) {
        try(Connection connection = ConnectionUtil.makeConnection()){
            if (connection!=null){

                Expense newExpense = new Expense();

                String createSql = "insert into expense (amount, expense_comments, requester_id) values(?,?,?)";
                PreparedStatement ps = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);

                ps.setBigDecimal(1, amount);
                newExpense.setAmount(amount);
                ps.setString(2, expenseComments);
                newExpense.setExpenseComments(expenseComments);
                ps.setInt(3, requester);
                newExpense.setRequester(requester);
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();

                int generatedIdKey = rs.getInt("expense_id");
                newExpense.setExpenseId(generatedIdKey);

                return newExpense;
            }

            else {
                throw new SQLException();
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense getExpenseById(int expenseId){
        try(Connection connection = ConnectionUtil.makeConnection()){

            if (connection!=null){
                String getSql = "select * from expense where expense_id = ?";

                PreparedStatement ps =  connection.prepareStatement(getSql);

                ps.setInt(1, expenseId);
                ResultSet rs = ps.executeQuery();
                rs.next();


                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getBigDecimal("amount"));
                expense.setExpenseComments(rs.getString("expense_comments"));
                expense.setStatus(rs.getString("expense_status"));
                expense.setCreatedTime(rs.getTimestamp("created_at"));
                expense.setRequester(rs.getInt("requester_id"));
                expense.setReviewer(rs.getInt("reviewer_id"));
                expense.setDecisionTime(rs.getTimestamp("decision_at"));
                expense.setReviewerComments(rs.getString("decision_comments"));
                return expense;
            }

            else {
                throw new SQLException();
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Expense> getAllExpenses() {
        Set<Expense> allExpenses = new HashSet<Expense>();
        try(Connection connection = ConnectionUtil.makeConnection()){
            if (connection!=null) {
                String getAllSql = "select * from expense";
                PreparedStatement ps = connection.prepareStatement(getAllSql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    Expense exp = new Expense();
                    exp.setExpenseId(rs.getInt("expense_id"));
                    exp.setAmount(rs.getBigDecimal("amount"));
                    exp.setExpenseComments(rs.getString("expense_comments"));
                    exp.setStatus(rs.getString("expense_status"));
                    exp.setCreatedTime(rs.getTimestamp("created_at"));
                    exp.setRequester(rs.getInt("requester_id"));
                    exp.setReviewer(rs.getInt("reviewer_id"));
                    exp.setDecisionTime(rs.getTimestamp("decision_at"));
                    exp.setReviewerComments(rs.getString("decision_comments"));
                    allExpenses.add(exp);
                }
                return allExpenses;
            }
            else {
                throw new SQLException();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return allExpenses;
        }
    }

    @Override
    public Expense updateExpense(Expense reviewedExpense){
        // updating to standard sql timestamp format for simplicity
        try(Connection connection = ConnectionUtil.makeConnection()){
            if(connection!=null){
                String updateSql = "update expense set expense_status = ?::status, reviewer_id = ?, decision_at = ?, decision_comments = ? where expense_id = ?";
                PreparedStatement ps = connection.prepareStatement(updateSql);

                Instant updatedExpInstant = Instant.now();
                Timestamp decisionTS = Timestamp.from(updatedExpInstant);

                ps.setString(1, reviewedExpense.getStatus());
                ps.setInt(2, reviewedExpense.getReviewer());
                ps.setTimestamp(3, decisionTS);
                ps.setString(4, reviewedExpense.getReviewerComments());
                ps.setInt(5, reviewedExpense.getExpenseId());
                ps.execute();
                return reviewedExpense;
            }
            else{
                throw new SQLException();
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
