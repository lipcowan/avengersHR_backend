package dev.lipco.controllers;

import com.google.gson.Gson;
import dev.lipco.daos.AvengerHibernateDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.services.AvengerService;
import dev.lipco.services.AvengerServiceImpl;
import io.javalin.http.Handler;

import java.util.Set;

public class AvengerController {
    private AvengerService aService = new AvengerServiceImpl(new AvengerHibernateDAO());

    public Handler getAllMembers = (ctx) -> {
        Set<Avenger> allMembers = this.aService.getAllMembers();
        Gson gson = new Gson();
        String avengersJSON = gson.toJson(allMembers);
        ctx.result(avengersJSON);
        ctx.status(200);
    };

    

}
