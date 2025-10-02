package TP2.Repository.MySQL;

import TP2.Modelo.Estudiante;
import TP2.DTO.EstudianteDTO;

import java.util.ArrayList;

public interface EstudianteRepository {
    void addEstudiante(Estudiante estudiante);
    EstudianteDTO obtenerEstudianteLibreta(int LU);

    void cargarTodos(ArrayList<Estudiante> estudiantes);
}
