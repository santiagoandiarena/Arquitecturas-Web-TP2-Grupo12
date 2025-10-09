package TP2.Repository.MySQL;


import TP2.Modelo.Carreras;
import java.util.List;

public interface CarrerasRepository {
    void generarReporte();
    Carreras devolverCarreraPorId(Long id);
    List<Object[]> carrerasConEstudiantes();
}
