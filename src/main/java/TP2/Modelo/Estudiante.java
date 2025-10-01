package TP2.Modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Table(name = "estudiante")
@Entity
public class Estudiante {

    @Column
    private String nombres;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nro_documento;

    @Column
    private String ciudad_residencia;

    @Column
    private String nro_libreta_universitaria;

    @OneToMany(mappedBy = "id_carrera", fetch = FetchType.LAZY)
    private List<Carreras> carreras;

}
