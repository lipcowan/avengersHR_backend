package dev.lipco.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    public static Algorithm getAlgo() {
        String jwtSecret = System.getenv("CONN_AVENGERS");
        return Algorithm.HMAC256(jwtSecret);
    }

    public static String makeJWT(int id, boolean is_manager){
        return JWT.create()
                .withClaim("manager", is_manager)
                .withClaim("userID", id)
                .sign(getAlgo());
    }

    public static DecodedJWT isValidJWT(String token){
        return JWT.require(getAlgo()).build().verify(token);
    }

}
