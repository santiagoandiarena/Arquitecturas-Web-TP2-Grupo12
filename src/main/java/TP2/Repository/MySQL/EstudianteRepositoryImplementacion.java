package TP2.Repository.MySQL;

import TP2.DTO.EstudianteDTO;
import TP2.Modelo.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class EstudianteRepositoryImplementacion implements EstudianteRepository {
    private EntityManager em;
    private static EstudianteRepositoryImplementacion instance;

    EstudianteRepositoryImplementacion(EntityManager em) {
        this.em = em;
    }

    //Singleton
    public static EstudianteRepositoryImplementacion getInstance(EntityManager em) {
        if (instance == null) {
            instance = new EstudianteRepositoryImplementacion(em);
        }
        return instance;
    }

    //cerrar entityManeger
    public void close() {
        if(em != null && em.isOpen()) {
            em.close();
        }
    }

    //dar de alta estudiante (2a)
    public void addEstudiante(Estudiante estudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (estudiante.getId() == 0) {
            try {
                em.persist(estudiante);
                transaction.commit();
                System.out.println("Estudiante insertado correctamente!");
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar estudiante! " + e.getMessage());
                throw e;
            }
        } else {   // Si ya tiene ID, hacemos un update
            try {
                em.merge(estudiante);
                transaction.commit();
                System.out.println("Estudiante actualizado correctamente!");
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar estudiante! " + e.getMessage());
                throw e;
            }
        }
    }

    //matricular un estudiante en una carrera (2b) (hecho en EstudianteCarreraRepository)

/*
    @Override
    public EstudianteDTO obtenerEstudianteLibreta(int LU) {
        try {
            return em.createQuery(
                            "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                    "FROM Estudiante e " +
                                    "WHERE e.lu = :lu", EstudianteDTO.class)
                    .setParameter("lu", LU)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por LU! " + e.getMessage());
            throw e;
        }
    }
*/
    /*
    public EstudianteDTO obtenerEstudiantePorLu(long lu) {
        try {
            return em.createQuery(
                        "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                "FROM Estudiante e " +
                                "WHERE e.lu = :lu"
                            , EstudianteDTO.class)
                    .setParameter("lu", lu)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        }
    }
    */
}