package dev.lipco.daotests;

import dev.lipco.daos.AvengerDAO;
import dev.lipco.daos.PsqlAvengerDAO;
import dev.lipco.entities.Avenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Set;

public class AvengerDaoTest {
    private AvengerDAO adao = new PsqlAvengerDAO();

    @Test
    void get_all_members(){
        Set<Avenger> members = this.adao.getAllMembers();
        System.out.println(members);
        Assertions.assertTrue(members.size() >= 1);
    }
}