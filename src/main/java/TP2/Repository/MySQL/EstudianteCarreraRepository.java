package TP2.Repository.MySQL;

import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;

public interface EstudianteCarreraRepository {
    void matricularEstudiante(Long id, Estudiante estudiante, Carreras carrera, int inscripcion, int graduacion, int antiguedad);
}
