package dev.lipco.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.lipco.entities.Avenger;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.log4j.Logger;

public class JwtUtil {
    private static final String jwtSecret = System.getenv("API_SECRET");
    private static final Algorithm algo = Algorithm.HMAC256(jwtSecret);
    static Logger logger = Logger.getLogger(JwtUtil.class.getName());


    public static String makeJWT(Avenger avenger){
        return JWT.create()
                .withClaim("memberId", avenger.getId())
                .withClaim("username", avenger.getUsername())
                .withClaim("isManager", avenger.isManager())
                .sign(algo);
    }

    public static DecodedJWT isValidJWT(String token){
        try {
            return JWT.require(algo)
                    .withClaimPresence("memberId")
                    .withClaimPresence("username")
                    .withClaimPresence("isManager")
                    .build()
                    .verify(token);
        }catch(JWTVerificationException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
