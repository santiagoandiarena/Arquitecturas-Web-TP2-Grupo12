package TP2.Repository.MySQL;

import TP2.DTO.EstudianteDTO;
import TP2.Modelo.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;


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

        if (estudiante.getNro_documento() == 0) {
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

    //Punto 2.c devolver todos los estudiantes
    public List<EstudianteDTO> devolverEstudiantes() {
        List<EstudianteDTO> estudiantes = new ArrayList<EstudianteDTO>();

        try {
            Query query = em.createQuery("SELECT e FROM Estudiante e ORDER BY e.edad DESC");
            estudiantes = query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }

    //Punto 2.d devolver estudiantes en base a su genero
    public List<EstudianteDTO> devolverEstudiantesPorGenero(String genero) {
        List<EstudianteDTO> estudiantes = new ArrayList<EstudianteDTO>();

        try {
            Query query = em.createQuery("SELECT e FROM Estudiante e  WHERE e.genero=:genero ORDER BY e.edad DESC");
            estudiantes = query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }

    public EstudianteDTO obtenerEstudianteLibreta(int LU) {
        return null;
    }

    public void cargarTodos(ArrayList<Estudiante> estudiantes) {

    }

    //matricular un estudiante en una carrera (2b) (hecho en EstudianteCarreraRepository)


    @Override
    public EstudianteDTO obtenerEstudianteLibreta(String nro_libreta_universitaria) {
        try {
            Estudiante estudiante = em.find(Estudiante.class, nro_libreta_universitaria);

            if (estudiante == null) {
                return null;
            }

            return new EstudianteDTO(
                    estudiante.getNombres(),
                    estudiante.getApellido(),
                    estudiante.getEdad(),
                    estudiante.getGenero(),
                    estudiante.getCiudad_residencia(),
                    estudiante.getNro_libreta_universitaria()
            );
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por nro_libreta_universitaria D:" + e.getMessage());
            return null;
        }
    }

}