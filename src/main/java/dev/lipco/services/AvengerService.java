package dev.lipco.services;

import dev.lipco.entities.Avenger;
import java.util.Set;

public interface AvengerService {

    Set<Avenger> getAllMembers();
    Set<Avenger> getManagers();
    Set<Avenger> getEmployees();

    Avenger getMemberByUsername(String username, String password);
    Avenger getMemberById(int id);
    Boolean checkManager(int id);

}
