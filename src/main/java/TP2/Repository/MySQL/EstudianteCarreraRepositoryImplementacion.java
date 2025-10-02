package TP2.Repository.MySQL;

import TP2.DTO.EstudianteDTO;
import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;
import TP2.Modelo.EstudianteCarrera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

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
    public void matricularEstudiante(Estudiante estudiante, Carreras carrera, int antiguedad, int inscripcion, int egreso, boolean graduado) {
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, antiguedad, inscripcion, egreso);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(inscripcion);
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
