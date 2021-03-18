package dev.lipco.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.lipco.entities.Avenger;
import org.junit.jupiter.api.*;
import dev.lipco.utils.JwtUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtUtilTest {
    private static String jwt;
    private static Avenger avenger;

    @Test
    @Order(1)
    void make_JWT(){
        avenger = new Avenger();
        avenger.setId(1);
        avenger.setUsername("ironlady1");
        avenger.setManager(false);
        jwt = JwtUtil.makeJWT(avenger);
        System.out.println("Token: " + jwt);
    }

    @Test
    @Order(2)
    void decode_JWT() {
        // confirm this test isn't dependant on the results of previous test by creating new jwt

        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        Assertions.assertEquals(avenger.getId(), decodedJWT.getClaim("id").asInt());
        Assertions.assertEquals(avenger.getUsername(), decodedJWT.getClaim("userName").asString());
        Assertions.assertEquals(avenger.isManager(), decodedJWT.getClaim("isManager").asBoolean());
        System.out.println("Member ID: " + decodedJWT.getClaim("id").asInt() + " , username: " + avenger.getUsername());
    }

}
