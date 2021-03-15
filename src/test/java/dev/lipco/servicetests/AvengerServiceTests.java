package dev.lipco.servicetests;

import dev.lipco.daos.AvengerDAO;
import dev.lipco.entities.Avenger;
import dev.lipco.services.AvengerService;
import dev.lipco.services.AvengerServiceImpl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.Set;

@MockitoSettings(strictness = Strictness.LENIENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class AvengerServiceTests {
    private AvengerService aService;

    @Mock
    private AvengerDAO aDao = null;

    @BeforeEach
    void setup(){
        Avenger avenger = new Avenger();
        Mockito.when(this.aDao.getMemberById(1)).thenReturn(
                new Avenger(1, "pepper", "potts", true)
        );

        Set<Avenger> members = new HashSet<>();
        members.add(
                new Avenger(1, "pepper", "potts", true)
        );
        members.add(
                new Avenger(3, "peter", "parker", false)
        );

        Mockito.when(this.aDao.getAllMembers()).thenReturn(members);

        aService = new AvengerServiceImpl(aDao);
    }

    @Order(1)
    @Test
    void get_all_members(){
        Set<Avenger> members = this.aService.getAllMembers();
        Assertions.assertNotNull(members);
        Assertions.assertTrue(members.size()>=1);
        System.out.println(members);
    }

    @Order(2)
    @Test
    void get_managers() {
        // should only s-out Pepper
        Set<Avenger> managers = this.aService.getManagers();
        Assertions.assertNotNull(managers);
        Assertions.assertTrue(managers.size()>=1);
        System.out.println(managers);
    }

    @Order(3)
    @Test
    void get_employees() {
        // should only s-out Peter
        Set<Avenger> employees = this.aService.getEmployees();
        Assertions.assertNotNull(employees);
        Assertions.assertTrue(employees.size()>=1);
        System.out.println(employees);
    }

    @Order(4)
    @Test
    void get_member_by_id() {
        Avenger testMember = this.aService.getMemberById(1);
        Assertions.assertNotNull(testMember);
        Assertions.assertEquals(1, testMember.getId());
        System.out.println(testMember);
    }

    @Order(5)
    @Test
    void check_manager() {
        boolean manager = this.aService.checkManager(1);
        Assertions.assertTrue(manager);
    }
}
