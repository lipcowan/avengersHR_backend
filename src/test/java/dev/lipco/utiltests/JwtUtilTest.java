package dev.lipco.utiltests;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import dev.lipco.utils.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtilTest {

    @Test
    void make_JWT(){
        String testJWT = JwtUtil.makeJWT(1, "ironlady1", true);
        System.out.println(testJWT);
    }

    @Test
    void decode_JWT() {
        // confirm this test isn't dependant on the results of previous test by creating new jwt
        String testJWT = JwtUtil.makeJWT(1, "ironlady1", true);
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(testJWT);
        int testID = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("user").asString();
        boolean manager = decodedJWT.getClaim("manager").asBoolean();
        Assertions.assertEquals(1, testID);
        Assertions.assertEquals("ironlady1", username);
        Assertions.assertTrue(manager);
        System.out.println(testID + username);
    }

    @Test
    void invalid_JWT() {
        try {
            DecodedJWT falseJWT = JwtUtil.isValidJWT(JwtUtil.makeJWT(1, "ironlady1", false));
            int falseID = falseJWT.getClaim("id").asInt();
        } catch(JWTDecodeException e){
            e.getStackTrace();
            Assertions.assertTrue(true);
        }

    }

}
