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

    public void generarReporte() {
        try {
            String jpql = "SELECT new TP2.DTO.ReporteCarreraDTO(\n" +
                    "        c.carrera,\n" +
                    "        ec.inscripcion,\n" +
                    "        ec.graduacion,\n" +
                    "        COUNT(ec),\n" +
                    "        SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END))\n" +
                    "    FROM EstudianteCarrera ec\n" +
                    "    JOIN ec.carrera c\n" +
                    "    WHERE ec.graduacion IS NULL OR ec.graduacion = 0 OR ec.inscripcion <= ec.graduacion\n" +
                    "    GROUP BY c.carrera, ec.inscripcion, ec.graduacion\n" +
                    "    ORDER BY c.carrera, ec.inscripcion, ec.graduacion";

            List<ReporteCarreraDTO> resultados = em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();

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

            String currentCareer = "";
            for (ReporteCarreraDTO rr: resultadosProcesados) {
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
