package dev.lipco.entities;


import java.math.BigDecimal;
import java.sql.Timestamp;

public class Expense {

    private String username;
    private int expenseId;
    private BigDecimal amount;
    private String expenseComments;
    private Status status;
    private Timestamp createdTime;
    private int requester;
    private Timestamp decisionTime;
    private String reviewerComments;
    private int reviewer;

    public Expense() {
        this.username = "";
        this.expenseId = 0;
        this.amount = BigDecimal.valueOf(0.01);
        this.expenseComments = null;
        this.status = Status.pending;
        this.createdTime = null;
        this.requester = 0;
        this.decisionTime = null;
        this.reviewerComments = "";
        this.reviewer = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return status.toString();
    }

    public void setStatus(String status) {
        if(status.equals("pending")){
            this.status = Status.pending;
        }
        if(status.equals("approved")){
            this.status = Status.approved;
        }
        if(status.equals("denied")){
            this.status = Status.denied;
        }
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

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "username='" + username + '\'' +
                ", expenseId=" + expenseId +
                ", amount=" + amount +
                ", expenseComments='" + expenseComments + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", requester=" + requester +
                ", decisionTime=" + decisionTime +
                ", reviewerComments='" + reviewerComments + '\'' +
                ", reviewer=" + reviewer +
                '}';
    }
}
