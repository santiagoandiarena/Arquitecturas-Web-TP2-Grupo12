package TP2.Utils;

import TP2.Factory.JPAUtil;
import TP2.Modelo.Carreras;
import TP2.Modelo.Estudiante;

import java.io.FileReader;

import TP2.Modelo.EstudianteCarrera;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

public class CargarDatos {

    public CargarDatos(){
        cargar();
    }

    public void cargar(){
        cargarCarreras("src/main/resources/DBData/carreras.csv");
        cargarEstudiantes("src/main/resources/DBData/estudiantes.csv");
        cargarMatriculas("src/main/resources/DBData/estudianteCarrera.csv");
        System.out.println("Datos cargados correctamente");
    }

    private void cargarEstudiantes(String path){

        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(path))){
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();

            while((linea = reader.readNext()) != null){
                Estudiante estudiante = new Estudiante();
                estudiante.setNro_documento(Long.parseLong(linea[0]));
                estudiante.setNombres(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudad_residencia(linea[5]);
                estudiante.setNro_libreta_universitaria(linea[6]);

                em.persist(estudiante);
            }

            em.getTransaction().commit();

            System.out.println("Datos de estudiantes cargados correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void cargarCarreras(String path){

        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(path))){
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();

            while((linea = reader.readNext()) != null) {
                Carreras carrera = new Carreras();
                carrera.setId_carrera(Integer.parseInt(linea[0]));
                carrera.setCarrera(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));
                em.persist(carrera);
            }

            em.getTransaction().commit();

            System.out.println("Datos de carreras cargados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void cargarMatriculas(String path){
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();
            while((linea = reader.readNext()) != null){

                String dniEstudiante = linea[0];
                Estudiante estudiante = em.find(Estudiante.class, dniEstudiante);

                int idCarrera = Integer.parseInt(linea[0]);
                Carreras carrera = em.find(Carreras.class, idCarrera);

                if (estudiante != null && carrera != null){
                    Integer graduacion = "0".equals(linea[4]) ? null : Integer.parseInt(linea[4]);

                    EstudianteCarrera matricula = new EstudianteCarrera();
                    matricula.setEstudiante(estudiante);
                    matricula.setCarrera(carrera);
                    matricula.setInscripcion(Integer.parseInt(linea[3]));
                    matricula.setGraduacion(graduacion);
                    matricula.setAntiguedad(Integer.parseInt(linea[5]));

                    em.persist(matricula);

                    System.out.println("Matricula cargada correctamente");
                } else {System.out.println("Estudiante o Carrera no encontrado");}
            }

            em.getTransaction().commit();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}

/*
* @Data
All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor!*/