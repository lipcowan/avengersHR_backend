package dev.lipco.services;

import dev.lipco.entities.Avenger;
import dev.lipco.daos.AvengerDAO;

import java.util.HashSet;
import java.util.Set;

public class AvengerServiceImpl implements AvengerService{

    private AvengerDAO adao;

    public AvengerServiceImpl(AvengerDAO avengerDAO){
        this.adao = avengerDAO;
    }

    @Override
    public Set<Avenger> getAllMembers() {
        return this.adao.getAllMembers();
    }

    @Override
    public Set<Avenger> getManagers() {
        Set<Avenger> allMembers = this.adao.getAllMembers();
        Set<Avenger> managers = new HashSet<>();

        for(Avenger a : allMembers){
            if(a.isManager()){
                managers.add(a);
            }
        }
        return managers;
    }

    @Override
    public Set<Avenger> getEmployees() {
        Set<Avenger> allMembers = this.adao.getAllMembers();
        Set<Avenger> employees = new HashSet<>();

        for(Avenger a : allMembers){
            if(!a.isManager()){
                employees.add(a);
            }
        }
        return employees;
    }

    @Override
    public Avenger getMemberById(int id){
        return this.adao.getMemberById(id);
    }

    @Override
    public Boolean checkManager(int id){
        Avenger a = this.adao.getMemberById(id);
        return a.isManager();
    }


}
