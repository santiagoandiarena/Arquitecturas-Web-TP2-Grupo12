package TP2;

import TP2.Utils.CargarDatos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Ejecutable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TP2JPA");
        EntityManager em = emf.createEntityManager();

        System.out.println("Cargando datos...");
        CargarDatos cargarDatos = new CargarDatos();





    }
    //Vamos a importar los DTO y los modelos
}
