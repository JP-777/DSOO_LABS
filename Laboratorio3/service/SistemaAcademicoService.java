package service;
import model.Alumno;
import model.Curso;
import model.Docente;
import model.Matricula;
import repo.AlumnoRepo;
import repo.CursoRepo;
import repo.DocenteRepo;
import repo.MatriculaRepo;
import java.util.*;

public class SistemaAcademicoService {
    private AlumnoRepo alumnoRepo;
    private CursoRepo cursoRepo;
    private DocenteRepo docenteRepo;
    private MatriculaRepo matriculaRepo;

    public SistemaAcademicoService(){
        alumnoRepo = new AlumnoRepo();
        cursoRepo = new CursoRepo();
        docenteRepo = new DocenteRepo();
        matriculaRepo = new MatriculaRepo();
    }

    // Metodos para Alumno
    public void agregarAlumno(Alumno a){
        alumnoRepo.agregarAlumno(a);
    }

    public Alumno buscarAlumno(String codigo){
        return alumnoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Alumno> listarAlumnos(){
        return alumnoRepo.listarAlumnos();
    }

    // Metodos para Curso
    public void agregarCurso(Curso c){
        cursoRepo.agregarCurso(c);
    }

    public Curso buscarCurso(String codigo){
        return cursoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Curso> listarCursos(){
        return cursoRepo.mostrarTodos();
    }

    // Metodos para Docente
    public void agregarDocente(Docente d){
        docenteRepo.agregarDocente(d);
    }

    public Docente buscarDocente(String codigo){
        return docenteRepo.buscarPorDni(codigo);
    }

    public ArrayList<Docente> listarDocentes(){
        return docenteRepo.mostrarTodos();
    }

    // Metodos para Matricula
    public void agregarMatricula(Matricula m){
        matriculaRepo.agregarMatricula(m);
    }

    public ArrayList<Matricula> buscarMatriculasPorAlumno(String codigoA){
        return matriculaRepo.buscarPorAlumno(codigoA);
    }

    public ArrayList<Matricula> buscarMatriculasPorCurso(String codigoC){
        return matriculaRepo.buscarPorCurso(codigoC);
    }

    public double obtenerPromedioAlumno(String codigoA){
        return matriculaRepo.obtenerPromedioAlumno(codigoA);
    }
}