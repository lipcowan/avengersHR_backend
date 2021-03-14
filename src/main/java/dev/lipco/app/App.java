package dev.lipco.app;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;


public class App {

    public static void main(String[] args) {
        Javalin avengersApp = Javalin.create(JavalinConfig::enableCorsForAllOrigins);
        avengersApp.start();
    }

}
