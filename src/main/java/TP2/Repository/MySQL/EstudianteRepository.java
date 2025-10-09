package TP2.Repository.MySQL;

import TP2.Modelo.Estudiante;
import TP2.DTO.EstudianteDTO;
import java.util.List;

public interface EstudianteRepository {
    void addEstudiante(Estudiante estudiante);
    EstudianteDTO obtenerEstudianteLibreta(String nro_libreta_universitaria);
    List<EstudianteDTO> devolverEstudiantes();
    List<EstudianteDTO> devolverEstudiantesPorGenero(String genero);
    List<EstudianteDTO> devolverEstudiantesDeXCarreraPorCiudad(int idCarrera, String ciudad);
}
