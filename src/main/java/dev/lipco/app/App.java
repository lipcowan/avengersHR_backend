package dev.lipco.app;

import dev.lipco.controllers.ExpenseController;
import dev.lipco.controllers.LoginController;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;


public class App {

    public static void main(String[] args) {
        Javalin avengersApp = Javalin.create(JavalinConfig::enableCorsForAllOrigins);

        LoginController lc = new LoginController();
        ExpenseController expenseController = new ExpenseController();

        avengersApp.post("/expenses",expenseController.createRequestHandler);

        avengersApp.get("/expenses", expenseController.viewSubmissionsHandler);

        avengersApp.get("/expenses/:expenseId", expenseController.reviewExpenseHandler);

        avengersApp.patch("/expenses/:expenseId", expenseController.finalizeDecisionHandler);

        avengersApp.post("/login", lc.loginHandler);

        avengersApp.start();
    }

}
