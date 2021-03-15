package dev.lipco.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "expense_comments")
    private String expenseComments;

    @Column(name = "expense_status")
    private String status;

    @Column(name = "created_at")
    private Instant createdTime;

    @Column(name = "requester_id")
    private int requester;

    @Column(name = "reviewer_id")
    private int reviewer;

    @Column(name = "decision_at")
    private Instant decisionTime;

    @Column(name = "decision_comments")
    private String reviewerComments;

    public Expense() {}

    public Expense(BigDecimal amount, String expenseComments, int requester) {
        this.amount = amount;
        this.expenseComments = expenseComments;
        this.requester = requester;
    }

    public Expense(int expenseId, BigDecimal amount, String expenseComments, String status, Instant createdTime, int requester, int reviewer, Instant decisionTime, String reviewerComments) {
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

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
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

    public Instant getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(Instant decisionTime) {
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
