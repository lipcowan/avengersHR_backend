package dev.lipco.daos;

import dev.lipco.entities.Avenger;
import dev.lipco.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.Set;

public class AvengerHibernateDAO implements AvengerDAO{
    private SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public Set<Avenger> getAllMembers(){
        return null;
    }

    @Override
    public Avenger getMemberById(int id){
        Session session = sf.openSession();
        Avenger avenger = session.get(Avenger.class,id);
        session.close();
        return avenger;
    }
}
