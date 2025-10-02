package TP2.DTO;

import TP2.Modelo.Carreras;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad_residencia;
    private String nro_libreta_universitaria;
    /*Creo que esto no va - Pedro Islas*/ /*private List<Carreras> carreras;*/
}
