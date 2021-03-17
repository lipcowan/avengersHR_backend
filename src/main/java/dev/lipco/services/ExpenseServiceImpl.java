package dev.lipco.services;


import dev.lipco.daos.ExpenseDAO;
import dev.lipco.entities.*;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;


public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseDAO edao;
    static Logger logger = Logger.getLogger(ExpenseServiceImpl.class.getName());

    public ExpenseServiceImpl(ExpenseDAO edao){
        if(edao == null){
            throw new NullPointerException();
        }
        this.edao = edao;
    }

    @Override
    public Avenger getAvenger(Avenger avenger){
        return edao.getAvenger(avenger);
    }

    @Override
    public Avenger login(LoginCredentials loginCredentials) throws IllegalAccessException{
        if(loginCredentials.getUsername() == null || loginCredentials.getPassword() == null) {
            throw new IllegalAccessException("incomplete credentials");
        }
        return edao.checkLogin(loginCredentials);
    }

    @Override
    public Expense newRequest(Avenger avenger, Expense expense){
        expense.setRequester(avenger.getId());
        return edao.createExpense(expense);
    }

    @Override
    public Set<Expense> getAllSubmissions(Avenger avenger) throws IllegalAccessException{
        if(!avenger.isManager()){
            logger.warn("Illegal access attempted by user " + avenger.getUsername());
            throw new IllegalAccessException("access denied, not a manager");
        }
        return edao.getAllExpenses();
    }

    @Override
    public Expense reviewRequest(Avenger avenger, Expense expense) throws IllegalAccessException {

        Expense requestedExp = edao.getExpenseById(expense.getExpenseId());
        if (requestedExp == null){
           throw new NullPointerException();
        }

        Instant reviewIn = Instant.now();
        Timestamp reviewTS = Timestamp.from(reviewIn);

        expense.setReviewer(avenger.getId());
        expense.setDecisionTime(reviewTS);
        return edao.updateExpense(expense);
    }
}
