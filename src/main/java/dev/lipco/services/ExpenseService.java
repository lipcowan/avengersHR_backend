package dev.lipco.services;

import dev.lipco.entities.Expense;

import java.math.BigDecimal;
import java.util.Set;

public interface ExpenseService {
    Expense newRequest(int requester, BigDecimal amount, String expenseComments);
    Set<Expense> viewMemberSubmissions(int reviewer, boolean manager);
    Expense reviewExpense(int expenseId, int reviewer);
    Expense finalizeDecision(int expenseId, int reviewer, boolean decision, String reviewerComments);
}
