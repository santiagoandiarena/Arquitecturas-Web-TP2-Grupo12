package TP2;

import TP2.DTO.ReporteCarreraDTO;
import TP2.Utils.CargarDatos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import TP2.Modelo.*;
import TP2.Repository.MySQL.*;

import java.util.List;

public class Ejecutable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TP2JPA");
        EntityManager em = emf.createEntityManager();

        EstudianteRepositoryImplementacion repoEstudiante = EstudianteRepositoryImplementacion.getInstance(em);
        CarrerasRepositoryImplementacion repoCarreras = CarrerasRepositoryImplementacion.getInstance(em);
        EstudianteCarreraRepositoryImplementacion repoMatriculas = EstudianteCarreraRepositoryImplementacion.getInstance(em);

        System.out.println("Cargando datos...");
        CargarDatos cargarDatos = new CargarDatos();

       /* *//*2-A. Dar de alta un estudiante *//*
        Estudiante estudiante = new Estudiante();
        estudiante.setNro_documento(71779555L);
        estudiante.setNombres("Juan");
        estudiante.setApellido("Perez");
        estudiante.setEdad(25);
        estudiante.setGenero("Masculino");
        estudiante.setCiudad_residencia("Buenos Aires");
        estudiante.setNro_libreta_universitaria("12345678");

        repoEstudiante.addEstudiante(estudiante);

        *//*2-B. Matricular un estudiante en una carrera *//*
        repoMatriculas.matricularEstudiante(1L, estudiante, repoCarreras.devolverCarreraPorId(5L), 2022, 2026, 0);

        *//*2-C. Recuperar todos los estudiantes y especificar algún criterio de ordenamiento simple *//*
        System.out.println("\nestudiantes por edad: " + repoEstudiante.devolverEstudiantes());

        *//*2-D. Recuperar un estudiante en base a su número de libreta universitaria *//*
        System.out.println("\nEstudiante buscado por LU:" + repoEstudiante.obtenerEstudianteLibreta("34978"));

        *//*2-E. Recuperar todos los estudiantes en base a su género *//*
        System.out.println("\nestudiantes por genero: " + repoEstudiante.devolverEstudiantesPorGenero("Male"));

        *//*2-F. Recuperar las carreras con estudiantes inscriptos y ordenar por cantidad de inscriptos *//*
        System.out.println("\nCarreras con estudiantes inscriptos:");
        for (Object[] resultado : repoCarreras.carrerasConEstudiantes()) {
            Carreras carrera = (Carreras) resultado[0];
            Long cantidad = (Long) resultado[1];
            System.out.println("Carrera: " + carrera.getCarrera() + " - Cantidad de inscriptos: " + cantidad);
        }

        *//*2-G. Recuperar los estudiantes de una determinada carrera filtrado por ciudad de residencia *//*
        System.out.println("\nEstudiantes de una determinada carrera y ciudad:" +repoEstudiante.devolverEstudiantesDeXCarreraPorCiudad(1, "Paquera"));
*/
        /*3. Generar un reporte de las carreras, que para cada carrera incluya información de los inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar los años de manera cronológica*/
        repoCarreras.generarReporte();


        // Cerrar los EntityManager
        repoCarreras.close();
        repoEstudiante.close();
        repoMatriculas.close();
        }
    }
    //matricularEstudiante(Long id, Estudiante estudiante, Carreras carrera, int inscripcion, int graduacion, int antiguedad)

    //Vamos a importar los DTO y los modelos

