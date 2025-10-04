package TP2.Repository.MySQL;

import TP2.DTO.ReporteCarreraDTO;
import TP2.Modelo.Carreras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CarrerasRepositoryImplementacion implements CarrerasRepository {
    private EntityManager em;
    private static CarrerasRepositoryImplementacion instance;

    CarrerasRepositoryImplementacion(EntityManager em) {
        this.em = em;
    }

    //Singleton
    public static CarrerasRepositoryImplementacion getInstance(EntityManager em) {
        if (instance == null) {
            instance = new CarrerasRepositoryImplementacion(em);
        }
        return instance;
    }

    //cerrar entityManeger
    public void close() {
        if(em != null && em.isOpen()) {
            em.close();
        }
    }

    //2.f recuperar las carreras con estudiantes inscriptos y ordenar por cantidad de inscriptos
    //Se utiliza list de Object ya que no retorna solo valores de Carrera
    public List<Object[]> carrerasConEstudiantes() {
        List<Object[]> resultados = new ArrayList<Object[]>();

        try {
            Query query = em.createQuery("SELECT c, COUNT(ce) FROM Carreras c JOIN c.estudiantes ce WHERE ce.inscripcion > 0 GROUP BY c ORDER BY COUNT(ce) DESC");
            resultados = query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de carreras: " + e.getMessage());
        }

        return resultados;
    }

    public Carreras devolverCarreraPorId(Long id) {
        try {
            Query query = em.createQuery("SELECT c FROM Carreras c WHERE c.id = :id");
            query.setParameter("id", id);
            return (Carreras) query.getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la carrera: " + e.getMessage());
            return null;
        }
    }

    //3) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
    // los años de manera cronológica
    /*public void generarReporte() {
        try {
            // Query para obtener inscriptos por año y carrera
            String jpqlInscriptos = "SELECT ec.inscripcion, c.carrera, COUNT(ec.id) " +
                    "FROM EstudianteCarrera ec " +
                    "JOIN ec.carrera c " +
                    "GROUP BY ec.inscripcion, c.carrera " +
                    "ORDER BY ec.inscripcion ASC, c.carrera ASC";
            
            // Query para obtener egresados por año y carrera
            String jpqlEgresados = "SELECT ec.graduacion, c.carrera, COUNT(ec.id) " +
                    "FROM EstudianteCarrera ec " +
                    "JOIN ec.carrera c " +
                    "WHERE ec.graduacion IS NOT NULL AND ec.graduacion > 0 " +
                    "GROUP BY ec.graduacion, c.carrera";
            
            List<Object[]> inscriptos = em.createQuery(jpqlInscriptos).getResultList();
            List<Object[]> egresados = em.createQuery(jpqlEgresados).getResultList();
            
            // Crear un mapa para los egresados: "año-carrera" -> cantidad
            java.util.Map<String, Long> mapaEgresados = new java.util.HashMap<>();
            for (Object[] eg : egresados) {
                Integer anio = (Integer) eg[0];
                String carrera = (String) eg[1];
                Long cantidad = ((Number) eg[2]).longValue(); // Convertir Number a Long
                mapaEgresados.put(anio + "-" + carrera, cantidad);
            }
            
            System.out.println("\n========== REPORTE DE CARRERAS POR AÑO ==========\n");
            
            Integer anioActual = null;
            
            for (Object[] resultado : inscriptos) {
                Integer anio = (Integer) resultado[0];
                String carrera = (String) resultado[1];
                Long cantInscriptos = ((Number) resultado[2]).longValue(); // Convertir Number a Long
                Long cantEgresados = mapaEgresados.getOrDefault(anio + "-" + carrera, 0L);
                
                // Imprimir encabezado de año cuando cambia
                if (anioActual == null || !anioActual.equals(anio)) {
                    if (anioActual != null) {
                        System.out.println(); // Línea en blanco entre años
                    }
                    System.out.println("AÑO " + anio + ":");
                    System.out.println("-".repeat(50));
                    anioActual = anio;
                }
                
                System.out.printf("  %-30s | Inscriptos: %3d | Egresados: %3d%n", 
                    carrera, cantInscriptos, cantEgresados);
            }
            
            System.out.println("\n" + "=".repeat(52));
            
        } catch (PersistenceException e) {
            System.out.println("No se pudo generar el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }*/

    public List<ReporteCarreraDTO> generarReporte() {
        try {
            String jpql = "SELECT new TP2.DTO.ReporteCarreraDTO(c.carrera, " +
                    "YEAR(ec.inscripcion), " +
                    "YEAR(ec.graduacion), " +
                    "(SELECT COUNT(ec1) FROM EstudianteCarrera ec1 WHERE ec1.graduacion IS NULL AND carrera = c), " + // Inscripciones sin egreso
                    "(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 WHERE ec2.graduacion IS NOT NULL AND ec2.carrera = c), " + // Inscripciones con egreso
                    "e.nro_documento) " +
                    "FROM EstudianteCarrera ec " +
                    "JOIN ec.carrera c " +
                    "JOIN ec.estudiante e " +
                    "ORDER BY c.carrera ASC, YEAR(ec.inscripcion) ASC, YEAR(ec.graduacion) ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }

}
