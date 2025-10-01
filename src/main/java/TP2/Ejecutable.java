package TP2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Ejecutable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("nombreDeLaBDD");
        EntityManager em = emf.createEntityManager();
    }
    //Vamos a importar los DTO y los modelos
}
