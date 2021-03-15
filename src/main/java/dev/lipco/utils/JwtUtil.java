package dev.lipco.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    public static Algorithm getAlgo() {
        String jwtSecret = System.getenv("API_SECRET");
        return Algorithm.HMAC256(jwtSecret);
    }

    public static String makeJWT(int id, String username, String password){
        return JWT.create()
                .withClaim("id", id)
                .withClaim("empName", username)
                .withClaim("password", password)
                .sign(getAlgo());
    }

    public static DecodedJWT isValidJWT(String token){
        return JWT.require(getAlgo()).build().verify(token);
    }

}
