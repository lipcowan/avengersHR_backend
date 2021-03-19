package dev.lipco.entities;
import java.math.BigDecimal;
import java.sql.Timestamp;


public class Expense {

    private int expenseId;

    private BigDecimal amount;

    private String expenseComments;

    private String status;

    private Timestamp createdTime;

    private int requester;

    private int reviewer;

    private Timestamp decisionTime;

    private String reviewerComments;

    public Expense() {}

    public Expense(BigDecimal amount, String expenseComments, int requester) {
        this.amount = amount;
        this.expenseComments = expenseComments;
        this.requester = requester;
    }

    public Expense(int expenseId, BigDecimal amount, String expenseComments, String status, Timestamp createdTime, int requester, int reviewer, Timestamp decisionTime, String reviewerComments) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.expenseComments = expenseComments;
        this.status = status;
        this.createdTime = createdTime;
        this.requester = requester;
        this.reviewer = reviewer;
        this.decisionTime = decisionTime;
        this.reviewerComments = reviewerComments;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getExpenseComments() {
        return expenseComments;
    }

    public void setExpenseComments(String expenseComments) {
        this.expenseComments = expenseComments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public int getRequester() {
        return requester;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public Timestamp getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(Timestamp decisionTime) {
        this.decisionTime = decisionTime;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments(String reviewerComments) {
        this.reviewerComments = reviewerComments;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amount=" + amount +
                ", expenseComments='" + expenseComments + '\'' +
                ", status='" + status + '\'' +
                ", createdTime=" + createdTime +
                ", requester=" + requester +
                ", reviewer=" + reviewer +
                ", decisionTime=" + decisionTime +
                ", reviewerComments='" + reviewerComments + '\'' +
                '}';
    }

}
