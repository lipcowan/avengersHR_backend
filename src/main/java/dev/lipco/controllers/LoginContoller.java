package dev.lipco.controllers;

import dev.lipco.daos.PsqlAvengerDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.services.AvengerService;
import dev.lipco.services.AvengerServiceImpl;
import com.google.gson.Gson;
import dev.lipco.utils.JwtUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;


public class LoginContoller {
    private static Logger logger = Logger.getLogger(LoginContoller.class.getName());

    private AvengerService aService = new AvengerServiceImpl(new PsqlAvengerDAO());

    public Handler loginHandler = (Context ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Avenger avenger = gson.fromJson(body, Avenger.class);
        if (avenger == null){
            ctx.status(400);
        }else {
            avenger = aService.getMemberById(avenger.getId());
            boolean manager = aService.checkManager(avenger.getId());
            String token = JwtUtil.makeJWT(avenger.getId(), manager);
            ctx.cookie("Authorization", token, 80000);
            ctx.result(gson.toJson(token));
            logger.info("Member" + avenger.getFirstName() + " successfully signed in!");
            ctx.status(200);
        }
    };
}
