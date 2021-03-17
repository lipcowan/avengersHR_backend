package dev.lipco.app;

import dev.lipco.controllers.ExpenseController;
import dev.lipco.daos.ExpenseDAO;
import dev.lipco.daos.PsqlExpenseDAO;
import dev.lipco.services.ExpenseService;
import dev.lipco.services.ExpenseServiceImpl;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.apache.log4j.Logger;


public class App {
    static Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Javalin avengersApp = Javalin.create(JavalinConfig::enableCorsForAllOrigins);

        try{
            ExpenseDAO dao = new PsqlExpenseDAO();
            ExpenseService eservice = new ExpenseServiceImpl(dao);
            ExpenseController controller = new ExpenseController(eservice);

            avengersApp.post("/avenger/login", controller.getUserLogin);
            avengersApp.get("/avenger", controller.getUser);
            avengersApp.get("/avenger/expense", controller.getAllExpenses);
            avengersApp.post("/avenger/expense", controller.newRequest);
//            avengersApp.get("/avenger/expense/:expenseId", controller.getExpense);
            avengersApp.put("/avenger/expense/:expenseId", controller.updateExpense);

            avengersApp.start();
        }
        catch (NullPointerException n){
            logger.error(n.getMessage());
        }
    }

}
