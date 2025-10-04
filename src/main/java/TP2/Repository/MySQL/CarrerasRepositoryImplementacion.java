package TP2.Repository.MySQL;

import TP2.Modelo.Carreras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CarrerasRepositoryImplementacion implements CarrerasRepository {
    private EntityManager em;
    private static CarrerasRepositoryImplementacion instance;

    public void cargarTodos(ArrayList<Carreras> carreras) {

    }


    //2.e recuperar las carreras con estudiantes inscriptos y ordenar por cantidad de inscriptos
    //Se utiliza list de Object ya que no retorna solo valores de Carrera
    public List<Object[]> carrerasConEstudiantes() {
        List<Object[]> resultados = new ArrayList<Object[]>();

        try {
            String jpql = "SELECT c, COUNT(ce) " +
                    "FROM Carrera c JOIN c.estudianteCarrera ce " +
                    "WHERE ce.inscripcion > 0 " +
                    "GROUP BY c " +
                    "ORDER BY COUNT(ce) DESC";

            Query query = em.createQuery(jpql);
            resultados = query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("No se pudo obtener la lista de carreras: " + e.getMessage());
        }

        return resultados;
    }
}
