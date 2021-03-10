package dev.lipco.utiltests;

import dev.lipco.utils.HibernateUtil;
import org.junit.jupiter.api.Test;
import org.hibernate.SessionFactory;

public class HibernateUtilTest {
    @Test
    void create_session_factory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        System.out.println(sf);
    }
}
