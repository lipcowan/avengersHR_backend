package dev.lipco.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection makeConnection() {
        String avengersDbUrl = System.getenv("CONN_AVENGERS");
        try {
            return DriverManager.getConnection(avengersDbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
