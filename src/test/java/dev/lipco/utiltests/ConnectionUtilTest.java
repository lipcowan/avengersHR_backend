package dev.lipco.utiltests;

import dev.lipco.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionUtilTest {

    @Test
    void make_connection() {
        Connection conn = ConnectionUtil.makeConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }

    @Test
    void get_env_vars() {
        String env = System.getenv("CONN_AVENGERS");
        System.out.println(env);
        Assertions.assertNotNull(env);
    }

}
