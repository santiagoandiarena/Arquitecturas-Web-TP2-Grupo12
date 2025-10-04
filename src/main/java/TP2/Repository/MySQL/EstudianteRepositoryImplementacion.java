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

        try {
            em.merge(estudiante);
            transaction.commit();
            System.out.println("Estudiante insertado correctamente!");
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar estudiante! " + e.getMessage());
            throw e;
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

    //Punto 2.e devolver estudiantes en base a su genero
    public List<EstudianteDTO> devolverEstudiantesPorGenero(String genero) {
        List<EstudianteDTO> estudiantes = new ArrayList<EstudianteDTO>();

        try {
            Query query = em.createQuery("SELECT e FROM Estudiante e  WHERE e.genero=:genero ORDER BY e.edad DESC")
                    .setParameter("genero", genero);
            estudiantes = query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }

    @Override
    public EstudianteDTO obtenerEstudianteLibreta(String lu) {
        try {
            System.out.println("num"+ lu);
            Query query = em.createQuery("SELECT e FROM Estudiante e WHERE e.nro_libreta_universitaria = :lu")
                    .setParameter("lu", lu);
            Estudiante e = (Estudiante) query.getSingleResult();

            return new EstudianteDTO(
                    e.getNombres(),
                    e.getApellido(),
                    e.getEdad(),
                    e.getGenero(),
                    e.getCiudad_residencia(),
                    e.getNro_libreta_universitaria()
            );

        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener el estudiante por LU: " + e.getMessage());
            return null;
        }
    }

    //2-G. Recuperar los estudiantes de una determinada carrera filtrado por ciudad de residencia
    public List<EstudianteDTO> devolverEstudiantesDeXCarreraPorCiudad(int idCarrera, String ciudad) {
        List<EstudianteDTO> estudiantes = new ArrayList<>();

        try {
            Query query = em.createQuery(
                    "SELECT e FROM Estudiante e " +
                    "JOIN EstudianteCarrera ec ON ec.estudiante = e " +
                    "JOIN Carreras c ON ec.carrera = c " +
                    "WHERE c.id_carrera = :idCarrera AND e.ciudad_residencia = :ciudad")
                    .setParameter("idCarrera", idCarrera)
                    .setParameter("ciudad", ciudad);
            
            List<Estudiante> resultados = query.getResultList();

            for (Estudiante est : resultados) {
                estudiantes.add(new EstudianteDTO(
                    est.getNombres(),
                    est.getApellido(),
                    est.getEdad(),
                    est.getGenero(),
                    est.getCiudad_residencia(),
                    est.getNro_libreta_universitaria()
                ));
            }
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }



}