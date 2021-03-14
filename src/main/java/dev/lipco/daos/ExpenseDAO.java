package dev.lipco.daos;

import dev.lipco.entities.Expense;
import java.util.Set;

public interface ExpenseDAO {
   Expense createExpense(Expense expense);
   Expense getExpenseById(int expenseId);
   Set<Expense>  getAllExpenses();
   Expense updateExpense(Expense expense);
    // expenses cannot be deleted by design
}
