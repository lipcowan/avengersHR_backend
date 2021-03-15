package dev.lipco.servicetests;

import dev.lipco.daos.ExpenseDAO;
import dev.lipco.daos.PsqlAvengerDAO;
import dev.lipco.daos.PsqlExpenseDAO;
import dev.lipco.entities.Expense;
import dev.lipco.services.AvengerServiceImpl;
import dev.lipco.services.ExpenseService;
import dev.lipco.services.ExpenseServiceImpl;

import dev.lipco.daos.AvengerDAO;
import dev.lipco.entities.Avenger;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@MockitoSettings(strictness = Strictness.LENIENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

    private ExpenseService eService = new ExpenseServiceImpl(new PsqlExpenseDAO(), new PsqlAvengerDAO());
    private AvengerServiceTests aService;
    private static Expense testExpense = null;

    @Mock
    private ExpenseDAO eDao;

    @BeforeEach
    void setup() {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(1255.22));
        expense.setExpenseComments("testing mockito");
        expense.setRequester(3);

        Mockito.when(this.eDao.createExpense(expense.getRequester(), expense.getAmount(), expense.getExpenseComments())).thenAnswer(
                (Answer<Expense>) invocation -> {
                    Expense e = invocation.getArgument(0);
                    e.setExpenseId(1);
                    return e;
                }
        );

        Mockito.when(this.eDao.getExpenseById(1)).thenReturn(
                testExpense
        );
        Set<Expense> expenses = new HashSet<>();
        expenses.add(testExpense);
        Mockito.when(this.eDao.getAllExpenses()).thenReturn(expenses);

        Mockito.when(this.eDao.updateExpense(Mockito.any(Expense.class))).thenAnswer(
                (Answer<Expense>) invocation -> invocation.getArgument(0)
        );

    }

    @Order(1)
    @Test
    void create_request() {
        Expense expense = new Expense(BigDecimal.valueOf(1255.22), "testing mockito", 3);
        testExpense = eService.newRequest(expense.getRequester(), expense.getAmount(), expense.getExpenseComments());
        Assertions.assertTrue(testExpense.getExpenseId() != 0);
        System.out.println(testExpense);
    }

}
