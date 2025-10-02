package TP2.Repository.MySQL;

import TP2.Modelo.Estudiante;
import TP2.DTO.EstudianteDTO;

import java.util.ArrayList;
import java.util.List;

public interface EstudianteRepository {
    void addEstudiante(Estudiante estudiante);
    EstudianteDTO obtenerEstudianteLibreta(int LU);
    List<Estudiante> devolverEstudiantes();
    List<Estudiante> devolverEstudiantesPorGenero(String genero);
    void cargarTodos(ArrayList<Estudiante> estudiantes);
}
