package TP2.Repository.MySQL;

import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;

import java.time.LocalDate;

public interface EstudianteCarreraRepository {
    void matricularEstudiante(Estudiante estudiante, Carreras carrera,
                              int antiguedad, int inscripcion,
                              int egreso, boolean graduado);
}
