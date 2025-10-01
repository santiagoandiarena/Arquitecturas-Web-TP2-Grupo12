package TP2.DTO;

import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EstudianteCarreraDTO {

    private Estudiante estudiante;
    private Carreras carrera;
    private Integer inscripcion;
    private Integer graduacion;
    private Integer antiguedad;
}
