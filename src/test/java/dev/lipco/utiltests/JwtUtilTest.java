package dev.lipco.utiltests;

import org.junit.jupiter.api.*;
import dev.lipco.entities.Avenger;
import dev.lipco.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtUtilTest {
    private static String jwtMember;
    private static String jwtManager;
    private static Avenger testMember;
    private static Avenger testManager;

    @Test
    @Order(1)
    void manager_JWT(){
        testManager = new Avenger();
        testManager.setId(1);
        testManager.setFirstName("pepper");
        testManager.setLastName("potts");
        testManager.setUsername("ironlady1");
        testManager.setManager(true);
        jwtManager = JwtUtil.makeJWT(testManager);
        System.out.println(jwtManager);
    }

    @Test
    @Order(2)
    void decode_Manager_JWT() {
        DecodedJWT decodedManager = JwtUtil.isValidJWT(jwtManager);
        Assertions.assertEquals(testManager.getId(), decodedManager.getClaim("memberId").asInt());
        Assertions.assertEquals(testManager.getUsername(), decodedManager.getClaim("username").asString());
        Assertions.assertEquals(testManager.isManager(), decodedManager.getClaim("isManager").asBoolean());
    }

    @Test
    @Order(3)
    void member_JWT(){
        testMember = new Avenger();
        testMember.setId(3);
        testMember.setFirstName("peter");
        testMember.setLastName("parker");
        testMember.setUsername("spiderman");
        testMember.setManager(false);
        jwtMember = JwtUtil.makeJWT(testMember);
        System.out.println(jwtMember);
    }

    @Test
    @Order(4)
    void decode_JWT() {
        DecodedJWT decodedMember = JwtUtil.isValidJWT(jwtMember);
        Assertions.assertEquals(testMember.getId(), decodedMember.getClaim("memberId").asInt());
        Assertions.assertEquals(testMember.getUsername(), decodedMember.getClaim("username").asString());
        Assertions.assertEquals(testMember.isManager(), decodedMember.getClaim("isManager").asBoolean());
    }

}
