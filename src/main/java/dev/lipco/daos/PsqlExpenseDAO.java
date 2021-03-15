package dev.lipco.daos;

import dev.lipco.entities.Expense;
import dev.lipco.utils.ConnectionUtil;

import java.sql.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.util.TimeZone;

public class PsqlExpenseDAO implements ExpenseDAO{

    public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public enum Status {
        pending,
        denied,
        approved;
    }


    @Override
    public Expense createExpense(Expense expense) {
        try(Connection connection = ConnectionUtil.makeConnection()){
            if (connection!=null){
                Instant instant = Instant.now();
                Timestamp ts = instant != null ? new Timestamp(instant.toEpochMilli()) : null;

                String createSql = "insert into expense (amount, expense_comments, requester_id) values(?,?,?)";
                PreparedStatement ps = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);

                ps.setBigDecimal(1, expense.getAmount());
                ps.setString(2, expense.getExpenseComments());
                ps.setInt(3, expense.getRequester());

                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();

                int generatedKey = rs.getInt("expense_id");
                expense.setExpenseId(generatedKey);

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
    public Expense getExpenseById(int expenseId){
        try(Connection connection = ConnectionUtil.makeConnection()){

            if (connection!=null){
                String getSql = "select * from expense where expense_id = ?";

                PreparedStatement ps =  connection.prepareStatement(getSql);

                ps.setInt(1, expenseId);
                ResultSet rs = ps.executeQuery();
                rs.next();

                Timestamp createdTS = rs.getTimestamp("created_at", tzUTC);
                Instant instantCreated = createdTS !=null ? Instant.ofEpochMilli(createdTS.getTime()) : null;
                Timestamp reviewedTS = rs.getTimestamp("decision_at", tzUTC);
                Instant instantReviewed = reviewedTS !=null ? Instant.ofEpochMilli(reviewedTS.getTime()) : null;

                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getBigDecimal("amount"));
                expense.setExpenseComments(rs.getString("expense_comments"));
                expense.setStatus(rs.getString("expense_status"));
                expense.setCreatedTime(instantCreated);
                expense.setRequester(rs.getInt("requester_id"));
                expense.setReviewer(rs.getInt("reviewer_id"));
                expense.setDecisionTime(instantReviewed);
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

                    Timestamp createdTS = rs.getTimestamp("created_at", tzUTC);
                    Instant instantCreated = createdTS !=null ? Instant.ofEpochMilli(createdTS.getTime()) : null;
                    Timestamp reviewedTS = rs.getTimestamp("decision_at", tzUTC);
                    Instant instantReviewed = reviewedTS !=null ? Instant.ofEpochMilli(reviewedTS.getTime()) : null;

                    exp.setExpenseId(rs.getInt("expense_id"));
                    exp.setAmount(rs.getBigDecimal("amount"));
                    exp.setExpenseComments(rs.getString("expense_comments"));
                    exp.setStatus(rs.getString("expense_status"));
                    exp.setCreatedTime(instantCreated);
                    exp.setRequester(rs.getInt("requester_id"));
                    exp.setReviewer(rs.getInt("reviewer_id"));
                    exp.setDecisionTime(instantReviewed);
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
    public Expense updateExpense(Expense expense){
        try(Connection connection = ConnectionUtil.makeConnection()){
            if(connection!=null){
                String updateSql = "update expense set expense_status = ?::status, reviewer_id = ?, decision_at = ?, decision_comments = ? where expense_id = ?";
                PreparedStatement ps = connection.prepareStatement(updateSql);

               Instant reviewedTS = new Timestamp(System.currentTimeMillis()).toInstant();



                ps.setString(1, expense.getStatus());
                ps.setInt(2, expense.getReviewer());
                ps.setTimestamp(3, Timestamp.from(reviewedTS), tzUTC);
                ps.setString(4, expense.getReviewerComments());
                ps.setInt(5, expense.getExpenseId());

                ps.execute();
                return expense;
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
