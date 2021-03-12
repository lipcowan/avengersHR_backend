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
        String testJWT = JwtUtil.makeJWT(1,true);
        System.out.println(testJWT);
    }

    @Test
    void decode_JWT() {
        // confirm this test isn't dependant on the results of previous test by creating new jwt
        String testJWT = JwtUtil.makeJWT(2, true);
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(testJWT);
        int testID = decodedJWT.getClaim("userID").asInt();
        boolean manager = decodedJWT.getClaim("manager").asBoolean();
        Assertions.assertEquals(2, testID);
        Assertions.assertTrue(manager);
        System.out.println(testID);
    }

    @Test
    void invalid_JWT() {
        try {
            DecodedJWT falseJWT = JwtUtil.isValidJWT("yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJNYW5hZ2VyIjp0cnVlLCJ1c2VySUQiOjF9.fBGPHxCGJWH-sJ5vpQR8y-OaH8-XXGvdn0-593kD7mg");
            int falseID = falseJWT.getClaim("userID").asInt();
        } catch(JWTDecodeException e){
            e.getStackTrace();
            Assertions.assertTrue(true);
        }

    }

}
