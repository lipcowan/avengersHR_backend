//package dev.lipco.servicetests;
//
//import dev.lipco.daos.ExpenseDAO;
//import dev.lipco.entities.Expense;
//import dev.lipco.services.ExpenseService;
//import dev.lipco.services.ExpenseServiceImpl;
//
//import dev.lipco.daos.AvengerDAO;
//import dev.lipco.entities.Avenger;
//
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.mockito.stubbing.Answer;
//
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.Set;
//
//
//@MockitoSettings(strictness = Strictness.LENIENT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(MockitoExtension.class)
//public class ExpenseServiceTests {
//
//    private ExpenseService eService;
//    private static Expense testExpense = null;
//
//    @Mock
//    private ExpenseDAO eDao;
//    private AvengerDAO aDao;
//
//    @BeforeEach
//    void setup() {
//        Mockito.when(this.eDao.createExpense(Mockito.any(Expense.class))).thenAnswer(
//                (Answer<Expense>) invocation -> {
//                    Expense e = invocation.getArgument(0);
//                    e.setExpenseId(1);
//                    return e;
//                }
//        );
//
//        Mockito.when(this.eDao.getExpenseById(1)).thenReturn(
//                testExpense
//        );
//        Set<Expense> expenses = new HashSet<>();
//        expenses.add(testExpense);
//        Mockito.when(this.eDao.getAllExpenses()).thenReturn(expenses);
//
//        Mockito.when(this.eDao.updateExpense(Mockito.any(Expense.class))).thenAnswer(
//                (Answer<Expense>) invocation -> {
//                    Expense e = invocation.getArgument(0);
//                    return e;
//                }
//        );
//
//        Avenger avenger = new Avenger();
//        Mockito.when(this.aDao.getMemberById(1)).thenReturn(
//                new Avenger(1, "pepper", "potts", true)
//        );
//
//        Set<Avenger> members = new HashSet<>();
//        members.add(
//                new Avenger(1, "pepper", "potts", true)
//        );
//        members.add(
//                new Avenger(3, "peter", "parker", false)
//        );
//
//        Mockito.when(this.aDao.getAllMembers()).thenReturn(members);
//
//
//        eService = new ExpenseServiceImpl(eDao, aDao);
//
//    }
//
//    @Order(1)
//    @Test
//    void create_request() {
//        Expense expense = new Expense(BigDecimal.valueOf(1250.22), "Working on Service Tests", 3);
//        testExpense = eService.createRequest(expense);
//        Assertions.assertTrue(testExpense.getExpenseId() != 0);
//        System.out.println(testExpense);
//    }
//
//}
