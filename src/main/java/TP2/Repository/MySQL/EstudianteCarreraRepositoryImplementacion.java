package TP2.Repository.MySQL;

import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;
import TP2.Modelo.EstudianteCarrera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class EstudianteCarreraRepositoryImplementacion implements EstudianteCarreraRepository{
    private EntityManager em;
    private static EstudianteRepositoryImplementacion instance;

    private EstudianteCarreraRepositoryImplementacion(EntityManager em) {
        this.em = em;
    }

    //Singleton
    public static EstudianteRepositoryImplementacion getInstance(EntityManager em) {
        if (instance == null) {
            instance = new EstudianteRepositoryImplementacion(em);
        }
        return instance;
    }

    //cerrar entityManager
    public void close() {
        if(em != null && em.isOpen()) {
            em.close();
        }
    }

    //2b matricular un estudiante en una carrera
    public void matricularEstudiante(Long id, Estudiante estudiante, Carreras carrera, int inscripcion, int graduacion, int antiguedad) {
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera(id, estudiante, carrera, inscripcion, graduacion, antiguedad);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(estudianteCarrera);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al matricular estudiante " + e.getMessage());
            throw e;
        }
    }

    public void guardarMatricula(EstudianteCarrera matricula) {
    }

}
