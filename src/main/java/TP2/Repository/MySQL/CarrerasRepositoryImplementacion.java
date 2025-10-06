package TP2.Repository.MySQL;

import TP2.DTO.ReporteCarreraDTO;
import TP2.Modelo.Carreras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /*public List<ReporteCarreraDTO> generarReporte() {
        try {
            String jpql = "SELECT new TP2.DTO.ReporteCarreraDTO(\n" +
                    "        c.carrera,\n" +
                    "        FUNCTION('YEAR', ec.inscripcion),\n" +
                    "        FUNCTION('YEAR', ec.graduacion),\n" +
                    "        SUM(CASE WHEN ec.graduacion IS NULL THEN 1 ELSE 0 END),\n" +
                    "        SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END)) \n" +
                    "    FROM EstudianteCarrera ec\n" +
                    "    JOIN ec.carrera c\n" +
                    "    GROUP BY c.carrera, FUNCTION('YEAR', ec.inscripcion), FUNCTION('YEAR', ec.graduacion)\n" +
                    "    ORDER BY c.carrera ASC, FUNCTION('YEAR', ec.inscripcion) ASC, FUNCTION('YEAR', ec.graduacion) ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }*/
    /*public List<ReporteCarreraDTO> generarReporte() {
        try {
            String jpql = "SELECT new TP2.DTO.ReporteCarreraDTO(\n" +
                    "        c.carrera,\n" +
                    "        YEAR(ec.inscripcion),\n" +
                    "        YEAR(ec.graduacion),\n" +  // Sin CASE WHEN complejos
                    "        COUNT(ec),\n" +
                    "        SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END))\n" +
                    "    FROM EstudianteCarrera ec\n" +
                    "    JOIN ec.carrera c\n" +
                    "    WHERE ec.graduacion IS NULL OR ec.inscripcion <= ec.graduacion\n" +
                    "    GROUP BY c.carrera, YEAR(ec.inscripcion), YEAR(ec.graduacion)\n" +
                    "    ORDER BY c.carrera, YEAR(ec.inscripcion), YEAR(ec.graduacion)";

            List<ReporteCarreraDTO> resultados = em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();

            //Post-procesamiento para manejar el valor 0
            return resultados.stream()
                    .map(dto -> {
                        //Si el año de egreso es 0, tratarlo como null
                        if (dto.getAnioEgreso() != null && dto.getAnioEgreso() == 0) {
                            return new ReporteCarreraDTO(
                                    dto.getNombreCarrera(),
                                    dto.getAnioInscripcion(),
                                    null,  //Egreso null para valor 0
                                    dto.getCantidadInscriptos(),
                                    dto.getCantidadEgresados()
                            );
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }*/
    public void generarReporte() {
        try {
            String jpql = "SELECT new TP2.DTO.ReporteCarreraDTO(\n" +
                    "        c.carrera,\n" +
                    "        YEAR(ec.inscripcion),\n" +
                    "        YEAR(ec.graduacion),\n" +
                    "        COUNT(ec),\n" +
                    "        SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END))\n" +
                    "    FROM EstudianteCarrera ec\n" +
                    "    JOIN ec.carrera c\n" +
                    "    WHERE ec.graduacion IS NULL OR YEAR(ec.graduacion) = 0 OR ec.inscripcion <= ec.graduacion\n" +
                    "    GROUP BY c.carrera, YEAR(ec.inscripcion), YEAR(ec.graduacion)\n" +
                    "    ORDER BY c.carrera, YEAR(ec.inscripcion), YEAR(ec.graduacion)";

            List<ReporteCarreraDTO> resultados = em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();

            //CORREGIDO: Asignar el resultado del post-procesamiento
            List<ReporteCarreraDTO> resultadosProcesados = resultados.stream()
                    .map(dto -> {
                        if (dto.getAnioEgreso() != null && dto.getAnioEgreso() == 0) {
                            return new ReporteCarreraDTO(
                                    dto.getNombreCarrera(),
                                    dto.getAnioInscripcion(),
                                    null,  // Convertir 0 a null
                                    dto.getCantidadInscriptos(),
                                    dto.getCantidadEgresados()
                            );
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

            //Usar la lista procesada
            String currentCareer = "";
            for (ReporteCarreraDTO rr: resultadosProcesados) {  // Cambiado aquí
                if(!rr.getNombreCarrera().equals(currentCareer)) {
                    currentCareer = rr.getNombreCarrera();
                    System.out.println("Carrera: " + currentCareer);
                }
                System.out.println("  Año Inscripción: " + rr.getAnioInscripcion() +
                        " | Año Egreso: " + (rr.getAnioEgreso() != null ? rr.getAnioEgreso() : "N/A") +
                        " | Inscriptos: " + rr.getCantidadInscriptos() +
                        " | Egresados: " + rr.getCantidadEgresados());
            }
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
