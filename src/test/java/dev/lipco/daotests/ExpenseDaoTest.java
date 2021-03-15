package dev.lipco.daotests;

import dev.lipco.daos.ExpenseDAO;
import dev.lipco.daos.PsqlExpenseDAO;
import dev.lipco.entities.Expense;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Set;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDaoTest {
    private ExpenseDAO testExpenseDao = new PsqlExpenseDAO();
    private static Expense testExpense = null;

    @Order(1)
    @Test
    void create_expense() {
        this.testExpense = this.testExpenseDao.createExpense(new Expense(BigDecimal.valueOf(1201.50), "new web slinger for Spidey dao test", 3));
        System.out.println(this.testExpense);
        Assertions.assertNotEquals(0, this.testExpense.getExpenseId());
        Assertions.assertNotNull(this.testExpense);
    }

    @Order(2)
    @Test
    void get_expense_by_id() {
        // using test from dBeaver 1 - 500.00 - testing dBeaver 1 with Wanda - pending - 2021-03-14 16:31:45
        Expense e = this.testExpenseDao.getExpenseById(1);
        System.out.println(e);
        Assertions.assertNotNull(e);
        Assertions.assertEquals("pending", e.getStatus());
        Assertions.assertEquals(4, e.getRequester());
    }

    @Order(3)
    @Test
    void get_all_expenses() {
        Set<Expense> allExpenses = this.testExpenseDao.getAllExpenses();
        System.out.println(allExpenses);
        Assertions.assertTrue(allExpenses.size()>= 1);
    }

    @Order(4)
    @Test
    void update_expense(){
        Expense e = this.testExpenseDao.getExpenseById(1);
        e.setStatus("denied");
        e.setReviewerComments("Testing dao");
        e.setReviewer(1);
        this.testExpenseDao.updateExpense(e);
        System.out.println(e);

    }
}
