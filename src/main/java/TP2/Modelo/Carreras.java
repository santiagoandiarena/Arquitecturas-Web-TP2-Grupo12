package TP2.Modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Table(name = "carrera")
@Entity
public class Carreras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_carrera;

    @Column
    private String carrera;

    @Column
    private int duracion;

    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;
}