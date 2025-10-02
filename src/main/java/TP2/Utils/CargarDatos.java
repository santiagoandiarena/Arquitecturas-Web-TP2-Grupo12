package TP2.Utils;

import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import TP2.Modelo.EstudianteCarrera;
import TP2.Repository.MySQL.CarrerasRepositoryImplementacion;
import TP2.Repository.MySQL.EstudianteCarreraRepositoryImplementacion;
import TP2.Repository.MySQL.EstudianteRepository;
import TP2.Repository.MySQL.EstudianteRepositoryImplementacion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;



public class CargarDatos {

    public void cargar(){
        cargarCarreras("src/main/java/TP2/Archivos/carreras.csv");
        cargarEstudiantes("src/main/java/TP2/Archivos/estudiantes.csv");
        cargarMatriculas("src/main/java/TP2/Archivos/matriculas.csv");
    }

    private void cargarEstudiantes(String path){
        try {
            ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
            CSVParser parse = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord record : parse) {
                Estudiante estudiante = new Estudiante(
                        Long.parseLong(record.get(0)),
                        record.get(1),
                        record.get(2),
                        record.parseInt(record.get(3)),
                        record.get(4),
                        record.get(5),
                        Integer.parseInt(record.get(6))
                );
                estudiantes.add(estudiante);
            }
            EstudianteRepository estudianteRepository = new EstudianteRepositoryImplementacion();
            estudianteRepository.cargarTodos(estudiantes);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo estudiantes: " + e.getMessage(), e);
        }
    }
    private void cargarCarreras(String path){
        try {
            ArrayList<Carreras> carreras = new ArrayList<Carreras>();
            CSVParser parse = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));
            for (CSVRecord record : parse) {
                Carreras carrera = new Carreras(
                        Integer.parseInt(record.get("id_carrera")),
                        record.get("carrera"),
                        Integer.parseInt(record.get("duracion"))
                );
                carreras.add(carrera);
            }
            CarrerasRepositoryImplementacion carrerasRepository = new CarrerasRepositoryImplementacion();
            carrerasRepository.cargarTodos(carreras);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo estudiantes: " + e.getMessage(), e);
        }
    }

    private void  cargarMatriculas(String path){
        try {
            CSVParser parse = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path));

            EstudianteRepositoryImplementacion estudianteRepo = new EstudianteRepositoryImplementacion();
            CarrerasRepositoryImplementacion carrerasRepo = new CarrerasRepositoryImplementacion();
            EstudianteCarreraRepositoryImplementacion estudianteCarreraRepo = new EstudianteCarreraRepositoryImplementacion();

            for (CSVRecord record : parse) {
                Carreras carrera = carrerasRepo.buscarPorId(Long.parseLong(record.get(1)));
                Estudiante estudiante = estudianteRepo.buscarPorId(Long.parseLong(record.get(0)));

                if (carrera != null && estudiante != null) {
                    EstudianteCarrera matricula = new EstudianteCarrera(
                            estudiante,
                            carrera,
                            Integer.parseInt(record.get("antiguedad")),
                            Integer.parseInt(record.get("inscripcion")),
                            Integer.parseInt(record.get("graduacion"))
                    );
                    estudianteCarreraRepo.guardarMatricula(matricula);
                } else {
                    System.out.println("Error: No se encontró el estudiante o la carrera para la matrícula: " + record);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo estudiantes: " + e.getMessage(), e);
        }
    }
}
