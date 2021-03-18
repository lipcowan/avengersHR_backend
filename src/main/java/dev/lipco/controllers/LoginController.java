package dev.lipco.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import dev.lipco.daos.AvengerDAO;
import dev.lipco.daos.PsqlAvengerDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.entities.Login;
import dev.lipco.services.AvengerService;
import dev.lipco.services.AvengerServiceImpl;
import dev.lipco.utils.JwtUtil;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class.getName());
    private AvengerService avengerService = new AvengerServiceImpl(new PsqlAvengerDAO());

    public Handler loginHandler = (ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Login login = gson.fromJson(body, Login.class);
        try{
            String username = login.getUsername();
            String password = login.getPassword();
            Avenger user = avengerService.getMemberByUsername(username, password);
            String memberJwt = JwtUtil.makeJWT(user);
            ctx.result(memberJwt);
            logger.info("Login by " + user.getFirstName() + " was successful");
        }catch(JWTVerificationException e){
            ctx.result("Username not found");
            logger.error(e);
        }

    };

}
