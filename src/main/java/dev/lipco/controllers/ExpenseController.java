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

            String body = ctx.body();
            Gson gson = new Gson();
            Expense bodyExpense = gson.fromJson(body,Expense.class);
            Expense requestedExpense = expenseService.createRequest(bodyExpense);
            Avenger requestingMember = avengerService.getMemberById(requestedExpense.getRequester());
            String expenseJSON = gson.toJson(requestedExpense);
            ctx.result(expenseJSON);
            ctx.status(200);
            logger.info("Member " + requestingMember.getFirstName() + " has submitted a new expense request, for $" + bodyExpense.getAmount());

        }catch(JWTDecodeException e){
            ctx.status(400);
            logger.error(e);
        }
    };

    public Handler viewSubmissionsHandler = (Context ctx) -> {
       try{
           String jwtToken = ctx.header("Authorization");
           DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);
           int memberID = tokenContents.getClaim("id").asInt();
           if(memberID == 0) {
               throw new JWTDecodeException("Unable to authorize member");
           }
           Set<Expense> submittedExpenses = expenseService.viewSubmissions(memberID);
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
            int reviewerID = tokenContents.getClaim("id").asInt();
            if(reviewerID == 0 || jwtToken == null){
                throw new JWTDecodeException("Unable to authorize user");
            }
            int expenseId = Integer.parseInt(ctx.pathParam("expenseId"));
            Expense expense = expenseService.reviewExpense(expenseId, reviewerID);
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

            if(jwtToken == null){
                throw new JWTDecodeException("Unable to authorize user");
            }

            DecodedJWT tokenContents = JwtUtil.isValidJWT(jwtToken);
            int reviewerID = tokenContents.getClaim("id").asInt();
            boolean canManage = tokenContents.getClaim("manager").asBoolean();

            if(!canManage || reviewerID == 0){
                throw new JWTDecodeException("User not permitted to update status");
            }

            int expenseId = Integer.parseInt(ctx.pathParam("expenseId"));
            String body = ctx.body();
            Gson gson = new Gson();
            Expense reviewedExpense = gson.fromJson(body, Expense.class);

            reviewedExpense.setExpenseId(expenseId);
            String checkStatus = expenseService.reviewExpense(expenseId, reviewerID).getStatus();
            String decision = reviewedExpense.getStatus();
            String comments = reviewedExpense.getReviewerComments();

            if(!checkStatus.equals(decision)) {
                expenseService.finalizeDecision(expenseId,reviewerID, decision.equals("approved"),comments);
                ctx.result(gson.toJson(reviewedExpense));
            }
            else{
                ctx.result(gson.toJson(expenseService.reviewExpense(expenseId,reviewerID)));
            }
        }catch(JWTDecodeException e){
            logger.error(e);
            ctx.status(403);
        }
    };



}
