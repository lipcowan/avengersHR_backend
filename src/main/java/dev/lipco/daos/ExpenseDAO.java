package dev.lipco.daos;

import dev.lipco.entities.Expense;
import dev.lipco.entities.LoginCredentials;
import dev.lipco.entities.Avenger;

import java.util.Set;

public interface ExpenseDAO {
   Expense createExpense(Expense expense);
   Expense getExpenseById(int expenseId);
   Set<Expense>  getAllExpenses();
   Expense updateExpense(Expense reviewedExpense);

   Avenger getAvenger(Avenger avenger);
   Avenger checkLogin(LoginCredentials loginCredentials);

    // expenses cannot be deleted by design
}
