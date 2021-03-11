package dev.lipco.daos;

import dev.lipco.entities.Avenger;
import java.util.*;

public interface AvengerDAO {
    // only need to read all avengers from table and get specific member to check status
    Set<Avenger> getAllMembers();
    Avenger getMemberById(int id);
}
