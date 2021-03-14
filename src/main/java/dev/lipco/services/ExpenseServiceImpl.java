package dev.lipco.services;

import dev.lipco.entities.Expense;
import dev.lipco.daos.ExpenseDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.daos.AvengerDAO;

import java.util.HashSet;
import java.util.Set;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseDAO edao;
    private AvengerDAO adao;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO, AvengerDAO avengerDAO){
        this.edao = expenseDAO;
        this.adao = avengerDAO;
    }

    @Override
    public Expense createRequest(Expense expense) {
        String status = "pending";

        expense.setStatus(status);

       return edao.createExpense(expense);
    }

    @Override
    public Set<Expense> viewSubmissions(int reviewer){
        Avenger member = adao.getMemberById(reviewer);
        boolean manager = member.isManager();
        Set<Expense> memberExpenses = new HashSet<>();
        Set<Expense> allExpenses = edao.getAllExpenses();
        if (manager) {
            return allExpenses;
        } else {
            for(Expense e : allExpenses) {
                if(e.getRequester() == member.getId()) {
                    memberExpenses.add(e);
                }
                return memberExpenses;
            }
        }
        return  null;
    }

    @Override
    public Expense reviewExpense(int id, int reviewer) {
        Avenger member = adao.getMemberById(reviewer);
        Expense expense = edao.getExpenseById(id);
        if (expense.getRequester() == member.getId() || member.isManager()) {
            return expense;
        }
        return null;
    }

    @Override
    public Expense finalizeDecision(int id, int reviewer, boolean decision, String reviewerComments){
        Avenger managingMember = adao.getMemberById(reviewer);
        Expense reviewedExpense  = edao.getExpenseById(id);
        // preventing managers from approving their own expenses - can approve all other members
        if (managingMember.isManager() && managingMember.getId() != reviewedExpense.getRequester()){
            reviewedExpense.setReviewer(managingMember.getId());
            reviewedExpense.setReviewerComments(reviewerComments);
            return edao.updateExpense(reviewedExpense);
        }
        return null;
    }

}
