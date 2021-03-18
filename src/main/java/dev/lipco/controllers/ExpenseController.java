package dev.lipco.controllers;

import com.auth0.jwt.exceptions.JWTDecodeException;
import dev.lipco.entities.Expense;
import dev.lipco.daos.PsqlExpenseDAO;
import dev.lipco.services.ExpenseService;
import dev.lipco.services.ExpenseServiceImpl;

import dev.lipco.entities.Avenger;
import dev.lipco.daos.PsqlAvengerDAO;
import dev.lipco.services.AvengerService;
import dev.lipco.services.AvengerServiceImpl;

import dev.lipco.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Set;

public class ExpenseController {

    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());

    private ExpenseService expenseService = new ExpenseServiceImpl(new PsqlExpenseDAO(), new PsqlAvengerDAO());
    private AvengerService avengerService = new AvengerServiceImpl(new PsqlAvengerDAO());

    // anyone can create an expense
    public Handler createRequestHandler = (Context ctx) -> {
        try{
            String jwtToken = ctx.header("Authorization");
            DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);

            if(tokenContents == null) {
                throw new JWTDecodeException("Unable to authorize");
            }

            int userId = tokenContents.getClaim("id").asInt();
            Avenger requester = avengerService.getMemberById(userId);


            String body = ctx.body();
            Gson gson = new Gson();
            Expense newExpense = gson.fromJson(body, Expense.class);
            BigDecimal amount = newExpense.getAmount();
            String expenseComments = newExpense.getExpenseComments();
            Expense requestedExpense = expenseService.newRequest(userId, amount, expenseComments);
            String expenseJSON = gson.toJson(requestedExpense);
            ctx.result(expenseJSON);
            ctx.status(200);
            logger.info("Member " + requester.getFirstName() + " has submitted a new expense request, for $" + newExpense.getAmount());

        }catch(JWTDecodeException e){
            ctx.status(400);
            logger.error(e);
        }
    };

    public Handler viewSubmissionsHandler = (Context ctx) -> {
       try{
           String jwtToken = ctx.header("Authorization");
           DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);
           Avenger reviewer = avengerService.getMemberById(tokenContents.getClaim("id").asInt());
           if(reviewer.getId() == 0) {
               throw new JWTDecodeException("Unable to authorize member");
           }
           Set<Expense> submittedExpenses = expenseService.viewMemberSubmissions(reviewer.getId(), reviewer.isManager());
           Gson gson = new Gson();
           String submittedExpensesJSON = gson.toJson(submittedExpenses);
           ctx.result(submittedExpensesJSON);
           ctx.status(200);
       }catch(JWTDecodeException e){
           ctx.status(403);
           logger.error(e);
       }
    };

    public Handler reviewExpenseHandler = (Context ctx) -> {
        try{
            String jwtToken = ctx.header("Authorization");
            DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);
            Avenger reviewer = avengerService.getMemberById(tokenContents.getClaim("id").asInt());
            if(reviewer.getId() == 0 || jwtToken == null){
                throw new JWTDecodeException("Unable to authorize user");
            }
            int expenseId = Integer.parseInt(ctx.pathParam("expenseId"));
            Expense expense = expenseService.reviewExpense(expenseId, reviewer.getId());
            Gson gson = new Gson();
            String expenseJSON = gson.toJson(expense);
            ctx.result(expenseJSON);
            ctx.status(200);
        }catch(JWTDecodeException e){
            logger.error(e);
            ctx.status(403);
        }
    };

    public Handler finalizeDecisionHandler = (Context ctx) -> {
        try{
            String jwtToken = ctx.header("Authorization");
            DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);
            Avenger reviewingManager = avengerService.getMemberById(tokenContents.getClaim("id").asInt());

            if(jwtToken == null){
                throw new JWTDecodeException("Unable to authorize user");
            }


            if(!reviewingManager.isManager() || reviewingManager.getId() == 0){
                throw new JWTDecodeException("User not permitted to update status");
            }

            int expenseId = Integer.parseInt(ctx.pathParam("expenseId"));

            String body = ctx.body();
            Gson gson = new Gson();
            Expense reviewedExpense = gson.fromJson(body, Expense.class);
            String decision = reviewedExpense.getStatus();
            int reviewer = reviewingManager.getId();
            String comments = reviewedExpense.getReviewerComments();
            reviewedExpense = expenseService.finalizeDecision(expenseId, reviewer, decision, comments);
            logger.info("Expense " + expenseId + " has been reviewed and is" + reviewedExpense.getStatus());
            ctx.result(gson.toJson(reviewedExpense));
            ctx.status(200);
        }catch(JWTDecodeException e){
            logger.error(e);
            ctx.status(403);
        }
    };



}
