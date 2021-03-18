package dev.lipco.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.lipco.entities.Avenger;
import org.apache.log4j.Logger;

public class JwtUtil {
    private static final String secret = System.getenv("API_SECRET");
    private static final Algorithm algo = Algorithm.HMAC256(secret);
    static Logger logger = Logger.getLogger(JwtUtil.class.getName());


    public static String makeJWT(Avenger user){
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("userName", user.getUsername())
                .withClaim("isManager", user.isManager())
                .sign(algo);
    }

    public static DecodedJWT isValidJWT(String token){
        try{
            DecodedJWT jwt = JWT.require(algo)
                    .withClaimPresence("id")
                    .withClaimPresence("userName")
                    .withClaimPresence("isManager")
                    .build()
                    .verify(token);

            return jwt;

        } catch (JWTVerificationException e){
            logger.error(e.getMessage());
            return null;
        }
    }

}
