package dev.lipco.daos;

import dev.lipco.entities.Expense;

import java.math.BigDecimal;
import java.util.Set;

public interface ExpenseDAO {
   Expense createExpense(int requester, BigDecimal amount, String expenseComments);
   Expense getExpenseById(int expenseId);
   Set<Expense>  getAllExpenses();
   Expense updateExpense(Expense reviewedExpense);
    // expenses cannot be deleted by design
}
