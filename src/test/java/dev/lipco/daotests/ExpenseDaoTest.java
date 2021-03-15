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

    @Order(1)
    @Test
    void create_expense() {
        Expense testExpense = this.testExpenseDao.createExpense(new Expense(BigDecimal.valueOf(1201.50), "new web slinger for Spidey dao test", 3));
        System.out.println(testExpense);
        Assertions.assertNotEquals(0, testExpense.getExpenseId());
        Assertions.assertNotNull(testExpense);
    }

    @Order(2)
    @Test
    void get_expense_by_id() {
        Expense e = this.testExpenseDao.getExpenseById(18);
        System.out.println(e.getExpenseId());
    }

//    @Order(3)
//    @Test
//    void get_all_expenses(){
//        Set<Expense> allExpenses = this.edao.getAllExpenses();
//        System.out.println(allExpenses);
//        Assertions.assertTrue(allExpenses.size() >= 1);
//    }

}
