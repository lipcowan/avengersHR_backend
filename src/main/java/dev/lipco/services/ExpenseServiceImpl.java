package dev.lipco.services;

import dev.lipco.entities.Expense;
import dev.lipco.daos.ExpenseDAO;

import java.util.Set;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseDAO edao;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO){
        this.edao = expenseDAO;
    }

    @Override
    public Expense 

}
