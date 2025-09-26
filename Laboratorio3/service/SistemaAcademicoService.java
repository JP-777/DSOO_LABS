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
    private final AlumnoRepo alumnoRepo;
    private final DocenteRepo docenteRepo;
    private final CursoRepo cursoRepo;
    private final MatriculaRepo matriculaRepo;

    public SistemaAcademicoService(AlumnoRepo a, DocenteRepo d, CursoRepo c, MatriculaRepo m) {
        this.alumnoRepo = a;
        this.docenteRepo = d;
        this.cursoRepo = c;
        this.matriculaRepo = m;
    }

    public void agregarAlumno(String codigo, String nombres, String apellidos, String dni, int edad) {
        alumnoRepo.agregarAlumno(new Alumno(codigo, nombres, apellidos, dni, edad));
    }
    public ArrayList<Alumno> listarAlumnos() { return alumnoRepo.listarAlumnos(); }
    public Alumno buscarAlumno(String codigo) { return alumnoRepo.buscarPorCodigo(codigo); }

    public void agregarDocente(String dni, String nombres, String apellidos, String especialidad, int aniosExperiencia) {
        docenteRepo.agregarDocente(new Docente(dni, nombres, apellidos, especialidad, aniosExperiencia));
    }
    public ArrayList<Docente> listarDocentes() { return docenteRepo.mostrarTodos(); }
    public Docente buscarDocente(String dni) { return docenteRepo.buscarPorDni(dni); }

    public void agregarCurso(String codigo, String nombre, String dniDocente) {
        Docente doc = docenteRepo.buscarPorDni(dniDocente);
        if (doc == null) throw new IllegalArgumentException("Docente no encontrado.");
        cursoRepo.agregarCurso(new Curso(codigo, nombre, doc));
    }
    public ArrayList<Curso> listarCursos() { return cursoRepo.mostrarTodos(); }
    public Curso buscarCurso(String codigo) { return cursoRepo.buscarPorCodigo(codigo); }

    public void agregarMatricula(String codigoAlumno, String codigoCurso) {
        Alumno a = alumnoRepo.buscarPorCodigo(codigoAlumno);
        if (a == null) throw new IllegalArgumentException("Alumno no encontrado.");
        Curso c = cursoRepo.buscarPorCodigo(codigoCurso);
        if (c == null) throw new IllegalArgumentException("Curso no encontrado.");

        for (Matricula m : matriculaRepo.buscarPorAlumno(codigoAlumno)) {
            if (m.getCursoRef().getCodigo().equalsIgnoreCase(codigoCurso)) {
                throw new IllegalArgumentException("El alumno ya está matriculado en ese curso.");
            }
        }
        matriculaRepo.agregarMatricula(new Matricula(a, c));
    }

    public void registrarNotas(String codigoAlumno, String codigoCurso, List<Double> notas) {
        if (notas == null || notas.size() != 3)
            throw new IllegalArgumentException("Se requieren exactamente 3 notas.");
        Matricula m = obtenerMatricula(codigoAlumno, codigoCurso);
        for (Double n : notas) {
            if (n == null || n < 0 || n > 20) throw new IllegalArgumentException("Nota fuera de rango [0,20].");
            m.agregarNota(n);
        }
    }

    public void agregarNotaAlumnoCurso(String codigoAlumno, String codigoCurso, double nota) {
        if (nota < 0 || nota > 20) throw new IllegalArgumentException("Nota fuera de rango [0,20].");
        Matricula m = obtenerMatricula(codigoAlumno, codigoCurso);
        m.agregarNota(nota);
    }

    public double promedioDe(String codigoAlumno, String codigoCurso) {
        return obtenerMatricula(codigoAlumno, codigoCurso).calcularPromedio();
    }

    public boolean aprobado(String codigoAlumno, String codigoCurso) {
        return obtenerMatricula(codigoAlumno, codigoCurso).isAprobado();
    }

    public Map<String, List<Matricula>> aprobadosYDesaprobadosPorCurso(String codigoCurso) {
        ArrayList<Matricula> mats = matriculaRepo.buscarPorCurso(codigoCurso);
        Map<String, List<Matricula>> r = new LinkedHashMap<>();
        List<Matricula> aprob = new ArrayList<>(), desap = new ArrayList<>();
        for (Matricula m : mats) (m.isAprobado() ? aprob : desap).add(m);
        r.put("aprobados", aprob);
        r.put("desaprobados", desap);
        return r;
    }

    public Map.Entry<Alumno, Double> mejorAlumno() {
        Alumno a = matriculaRepo.obtenerAlumnoConMejorPromedio(alumnoRepo);
        if (a == null) return null;
        double p = matriculaRepo.obtenerPromedioAlumno(a.getCodigo());
        return new AbstractMap.SimpleEntry<>(a, p);
    }

    private Matricula obtenerMatricula(String codigoAlumno, String codigoCurso) {
        ArrayList<Matricula> mats = matriculaRepo.buscarPorAlumno(codigoAlumno);
        for (Matricula m : mats) {
            if (m.getCursoRef().getCodigo().equalsIgnoreCase(codigoCurso)) return m;
        }
        throw new IllegalArgumentException("El alumno no está matriculado en ese curso.");
    }
}
