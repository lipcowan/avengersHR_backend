package dev.lipco.services;

import dev.lipco.entities.Expense;
import dev.lipco.daos.ExpenseDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.daos.AvengerDAO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseDAO edao;
    private AvengerDAO adao;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO, AvengerDAO avengerDAO){
        this.edao = expenseDAO;
        this.adao = avengerDAO;
    }

    public enum Status {
        pending,
        denied,
        approved;
    }

    @Override
    public Expense newRequest(int requester, BigDecimal amount, String expenseComments) {
        // no need to set anything else since db is setting default values and other values are null still
//        String status = "pending";
        return edao.createExpense(requester, amount, expenseComments);
    }

    @Override
    public Set<Expense> viewMemberSubmissions(int reviewer, boolean manager){
        Avenger reviewingMember = adao.getMemberById(reviewer);
        Set<Expense> memberExpenses = new HashSet<>();
        Set<Expense> allExpenses = edao.getAllExpenses();

        for(Expense e : allExpenses) {
            if (e.getRequester() == reviewingMember.getId()) {
                memberExpenses.add(e);
            }
        }
        return  manager? allExpenses: memberExpenses;
    }

    @Override
    public Expense reviewExpense(int expenseId, int reviewer) {
        Avenger reviewingMember = adao.getMemberById(reviewer);
        Expense reviewedExpense = edao.getExpenseById(expenseId);
        boolean manager = reviewingMember.isManager();
        if (manager || reviewedExpense.getRequester() == reviewer){
            return  reviewedExpense;
        }
        return null;
    }

    @Override
    public Expense finalizeDecision(int expenseId, int reviewer, String decision, String reviewerComments){
        Avenger manager = adao.getMemberById(reviewer);
        Expense reviewedExpense  = edao.getExpenseById(expenseId);
        // will add additional parameter checking to prevent managers from approving their own expenses - can approve all other members
        if (manager.isManager()){
            reviewedExpense.setReviewer(reviewer);
            reviewedExpense.setReviewerComments(reviewerComments);
            reviewedExpense.setStatus(decision);
        }

        return edao.updateExpense(reviewedExpense);
    }

}
