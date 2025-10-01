package TP2.DTO;

import TP2.Modelo.Carreras;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EstudianteDTO {
    private String nombres;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad_residencia;
    private String nro_libreta_universitaria;
    private List<Carreras> carreras;
}
