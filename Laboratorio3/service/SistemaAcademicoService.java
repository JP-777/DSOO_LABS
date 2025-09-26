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

    public SistemaAcademicoService(AlumnoRepo alRepo, DocenteRepo docRepo, CursoRepo curRepo, MatriculaRepo matRepo) {
        this.alumnoRepo = alRepo;
        this.cursoRepo = curRepo;
        this.docenteRepo = docRepo;
        this.matriculaRepo = matRepo;
    }

    // Metodos para Alumno
    public void agregarAlumno(String codigo, String nombres, String apellidos, String dni, int edad){
        alumnoRepo.agregarAlumno(new Alumno(codigo, nombres, apellidos, dni, edad));
    }

    public Alumno buscarAlumno(String codigo){
        return alumnoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Alumno> listarAlumnos(){
        return alumnoRepo.listarAlumnos();
    }

    // Metodos para Curso
    public void agregarCurso(String codigo, String nombre, Docente docente){
        cursoRepo.agregarCurso(new Curso(codigo,nombre,docente));
    }

    public Curso buscarCurso(String codigo){
        return cursoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Curso> listarCursos(){
        return cursoRepo.mostrarTodos();
    }

    // Metodos para Docente
    public void agregarDocente(String dni, String nombres, String apellidos, String especialidad, int añosExperiencia){
        docenteRepo.agregarDocente(new Docente(dni,nombres,apellidos,especialidad,añosExperiencia));
    }

    public Docente buscarDocente(String codigo){
        return docenteRepo.buscarPorDni(codigo);
    }

    public ArrayList<Docente> listarDocentes(){
        return docenteRepo.mostrarTodos();
    }

    // Metodos para Matricula
    public void agregarMatricula(String alumnoCod, String cursoCod){
        Alumno alumnoRef = alumnoRepo.buscarPorCodigo(alumnoCod);
        Curso cursoRef = cursoRepo.buscarPorCodigo(cursoCod);
        matriculaRepo.agregarMatricula(new Matricula(alumnoRef,cursoRef));
    }
    
    // Agregar nota a un alumno en un curso
    public void agregarNotaAlumnoCurso(String codigoAlumno, String codigoCurso, double nota) {
        ArrayList<Matricula> matriculasAlumno = matriculaRepo.buscarPorAlumno(codigoAlumno);
        for (Matricula m : matriculasAlumno) {
            if (m.getCursoRef().getCodigo().equals(codigoCurso)) {
                m.agregarNota(nota);
                return;
            }
        }
        System.out.println("No se encontró la matrícula del alumno en ese curso.");
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