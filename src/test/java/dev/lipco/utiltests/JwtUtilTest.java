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
        // working with Pepper Potts as a testUser, this is a manager type
        String testJWT = JwtUtil.makeJWT(1, "ironlady1", "tonysgirl!");
        System.out.println("Token: " + testJWT);
    }

    @Test
    void decode_JWT() {
        // confirm this test isn't dependant on the results of previous test by creating new jwt
        String testJWT = JwtUtil.makeJWT(1, "ironlady1", "tonysgirl!");
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(testJWT);
        int testID = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("empName").asString();
        String password = decodedJWT.getClaim("password").asString();
        Assertions.assertEquals(1, testID);
        Assertions.assertEquals("ironlady1", username);
        Assertions.assertEquals("tonysgirl!", password);
        System.out.println("Member ID: " + testID + " , username: " + username);
    }

}
