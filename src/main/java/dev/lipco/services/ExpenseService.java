package dev.lipco.services;

import dev.lipco.entities.Expense;

import java.util.Set;

public interface ExpenseService {
    Expense createRequest(Expense expense);

    Set<Expense> viewSubmissions(int reviewer);

    Expense reviewExpense(int id, int reviewer);

    Expense finalizeDecision(int id, int reviewer, boolean decision, String reviewerComments);
}
