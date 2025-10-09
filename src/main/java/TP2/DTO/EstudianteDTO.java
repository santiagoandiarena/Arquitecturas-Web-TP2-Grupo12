package TP2.DTO;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private String nombres;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad_residencia;
    private String nro_libreta_universitaria;

    @Override
    public String toString(){
        return String.format("Nombre completo: " + nombres + ", " + apellido + ". Edad: " + edad);
    }
}
