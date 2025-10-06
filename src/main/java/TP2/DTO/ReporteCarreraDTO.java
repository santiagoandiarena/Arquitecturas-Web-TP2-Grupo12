package TP2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCarreraDTO {
    private String nombreCarrera;
    private Integer anioInscripcion;
    private Integer anioEgreso;
    private Long cantidadInscriptos;
    private Long cantidadEgresados;
}
