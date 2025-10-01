package TP2.Factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("TP2JPA");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
