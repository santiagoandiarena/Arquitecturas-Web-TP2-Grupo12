package TP2.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "estudianteCarrera")
@Entity
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carreras carrera;

    @Column
    private Integer inscripcion;

    @Column
    private Integer graduacion;

    @Column
    private Integer antiguedad;

}
