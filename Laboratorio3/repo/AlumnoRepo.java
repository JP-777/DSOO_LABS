package DSOO_LABS.Laboratorio3.repo;

import java.util.ArrayList;

import DSOO_LABS.Laboratorio3.model.Alumno;

public class AlumnoRepo {
    private ArrayList<Alumno> alumnos;

    public AlumnoRepo() {
        alumnos = new ArrayList<>();
    }

    //registrar alumno
    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno); }

    //listar todos los alumnos
    public ArrayList<Alumno> listarAlumnos() {
        return alumnos;}

    // Buscar por código
    public Alumno buscarPorCodigo(String codigo) {
        for (Alumno a : alumnos) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) {
                return a;
            }
        }
        return null; //si es que no se encuentra
    }
}
