package dev.lipco.controllers;

import com.auth0.jwt.exceptions.JWTDecodeException;
import dev.lipco.entities.*;
import dev.lipco.services.ExpenseService;
import dev.lipco.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class ExpenseController {

    private ExpenseService eservice;
    private final Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());


    public ExpenseController(ExpenseService expenseService) {
        if(expenseService == null) {
            throw new NullPointerException();
        }
        eservice = expenseService;
    }

    private Avenger avengerVerified (Context ctx) {
        String authorization = ctx.header("Authorization");
        if(authorization == null) {
            return null;
        }
        DecodedJWT jwt = JwtUtil.isValidJWT(authorization);
        if(jwt == null) {
            return null;
        }
        Avenger user = new Avenger();
        user.setId(jwt.getClaim("memberId").asInt());
        user.setUsername(jwt.getClaim("username").asString());
        user.setManager(jwt.getClaim("isManager").asBoolean());
        return user;
    }

    public Handler getUser = (ctx) -> {
      Avenger user = avengerVerified(ctx);
        if(user == null) {
            ctx.status(403);
            ctx.result("Missing or invalid JWT. Please log in");
        }
        Avenger avengerUser = eservice.getAvenger(user);
        if(avengerUser == null) {
            ctx.status(404);
            ctx.result("Could not find userdata");
        }
        ctx.status(200);
        ctx.result(gson.toJson(avengerUser));
    };


    // anyone can create an expense
    public Handler newRequest = (Context ctx) -> {
        Avenger user = avengerVerified(ctx);
        if(user == null) {
            ctx.status(403);
            ctx.result("Missing or invalid JWT. Please log in");
        }
        try{
            Expense expense = gson.fromJson(ctx.body(), Expense.class);
            if(expense == null){
                ctx.status(400);
                ctx.result("Expense fields empty");
            }
            Expense newExpense = eservice.newRequest(user, expense);
            if (newExpense == null) {
                ctx.status(500);
                ctx.result("Could not create expense");
            }
            ctx.status(201);
            ctx.result(gson.toJson(newExpense));
        }catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (JsonSyntaxException n) {
            ctx.status(400);
            ctx.result(n.getMessage());
        }
    };

//    public Handler getExpense = (ctx) -> {
//        Avenger user = avengerVerified(ctx);
//        if(user == null) {
//            ctx.status(403);
//            ctx.result("Missing or invalid JWT. Please log in");
//            return;
//        }
//        try {
//            Expense expense = new Expense();
//            expense.setExpenseId(Integer.parseInt(ctx.pathParam("expenseId")));
//            if(fullExpense == null) {
//                ctx.status(404);
//                ctx.result("Expense not found");
//                return;
//            }
//            ctx.status(200);
//            ctx.result(gson.toJson(fullExpense));
//        } catch (IllegalAccessException i) {
//            ctx.status(403);
//            ctx.result(i.getMessage());
//        } catch (IllegalArgumentException n) {
//            ctx.status(400);
//            ctx.result(n.getMessage());
//        }
//    };

    public Handler getAllExpenses = (ctx) -> {
        Avenger user = avengerVerified(ctx);
        if(user == null) {
            ctx.status(403);
            ctx.result("Missing or invalid JWT. Please log in");
            return;
        }
        try {
            Set<Expense> expenses = eservice.getAllSubmissions(user);
            if(expenses == null) {
                ctx.status(500);
                ctx.result("Could not retrieve expenses");
                return;
            }
            ctx.status(200);
            ctx.result(gson.toJson(expenses));
        } catch (IllegalAccessException i ) {
            ctx.status(403);
            ctx.result(i.getMessage());
        }
    };

    public Handler updateExpense = (ctx) -> {
        Avenger user = avengerVerified(ctx);
        if(user == null) {
            ctx.status(403);
            ctx.result("Missing or invalid JWT. Please log in");
            return;
        }
        try {
            Expense expense = gson.fromJson(ctx.body(), Expense.class);
            expense.setExpenseId(Integer.parseInt(ctx.pathParam("expenseId")));
            Expense updatedExpense = eservice.reviewRequest(user, expense);
            if(updatedExpense == null) {
                ctx.status(404);
                ctx.result("Could not update expense or expense doesn't exist");
                return;
            }
            ctx.status(200);
            ctx.result(gson.toJson(updatedExpense));
        } catch (IllegalAccessException i) {
            ctx.status(403);
            ctx.result(i.getMessage());
        } catch (IllegalArgumentException n) {
            ctx.status(400);
            ctx.result(n.getMessage());
        } catch (NullPointerException e) {
            ctx.status(400);
            ctx.result("No expense info given");
        }
    };

    public Handler getUserLogin = ctx -> {
        LoginCredentials login = gson.fromJson(ctx.body(), LoginCredentials.class);
        if(login == null) {
            ctx.status(400);
            ctx.result("No login information provided");
            logger.warn("Login attempt made with empty request payload");
            return;
        }
        try{
            Avenger user = eservice.login(login);
            if (user == null) {
                ctx.status(403);
                ctx.result("Login failed");
                logger.warn("Failed attempted login with username: " + login.getUsername());
                return;
            }
            String jwt = JwtUtil.makeJWT(user);
            user.setJwt(jwt);
            ctx.status(200);
            ctx.result(gson.toJson(user));
            logger.info("Login for user " + login.getUsername());
        } catch (IllegalAccessException i) {
            ctx.status(403);
            ctx.result(i.getMessage());
        }
    };

}
