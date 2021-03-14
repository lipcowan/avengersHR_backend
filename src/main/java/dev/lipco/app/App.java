package dev.lipco.app;

import dev.lipco.controllers.LoginContoller;
import dev.lipco.controllers.ExpenseController;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;


public class App {

    public static void main(String[] args) {
        Javalin avengersApp = Javalin.create(JavalinConfig::enableCorsForAllOrigins);

        LoginContoller loginContoller = new LoginContoller();
        ExpenseController expenseController = new ExpenseController();

        avengersApp.post("/login", loginContoller.loginHandler);

        avengersApp.post("/expenses",expenseController.createRequestHandler);

        avengersApp.get("/expenses", expenseController.viewSubmissionsHandler);

        avengersApp.get("/expenses:expenseId", expenseController.reviewExpenseHandler);

        avengersApp.patch("/expenses:expenseId", expenseController.finalizeDecisionHandler);

        avengersApp.start();
    }

}
