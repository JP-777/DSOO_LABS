package repo;

import model.Matricula;
import model.Alumno;
import java.util.ArrayList;

public class MatriculaRepo {
    private ArrayList<Matricula> matriculas;
    private int contCodigo = 1;

    public MatriculaRepo(){
        matriculas = new ArrayList<>();
    }

    public void agregarMatricula(Matricula mat){
        if (mat.getNotas().size() < 3) {
            System.out.println("Alumno con falta de notas");
            return;
        }
            
        for(int i = 0; i < matriculas.size(); i++){
            if (matriculas.get(i).getAlumnoRef().getCodigo().equals(mat.getAlumnoRef().getCodigo()) && 
                matriculas.get(i).getCursoRef().getCodigo().equals(mat.getCursoRef().getCodigo())) {
                    System.out.println("Alumno ya matriculado en este curso, intente muevamente");
                    return;
                }
        }
        mat.setCodigo("M" + contCodigo);
        contCodigo++;
        matriculas.add(mat);
    }

    public ArrayList<Matricula> buscarPorAlumno(String codigoA){
        ArrayList<Matricula> matriculasAlumno = new ArrayList<>();
        for(int i=0; i<matriculas.size(); i++){
            if(matriculas.get(i).getAlumnoRef().getCodigo().equals(codigoA))
                matriculasAlumno.add(matriculas.get(i));
        }
        return matriculasAlumno;
    }

    public ArrayList<Matricula> buscarPorCurso(String codigoC){
        ArrayList<Matricula> matriculasCurso = new ArrayList<>();
        for(int i=0; i<matriculas.size(); i++){
            if(matriculas.get(i).getCursoRef().getCodigo().equals(codigoC))
                matriculasCurso.add(matriculas.get(i));
        }
        return matriculasCurso;
    }

    public double obtenerPromedioAlumno(String codigoA){
        double sumatoria = 0;
        ArrayList<Matricula> matriculasAlumno = buscarPorAlumno(codigoA);
        if (matriculasAlumno.size() == 0){
            System.out.println("El alumno no cuenta con cursos");
            return 0;
        }
        for(int i=0; i<matriculasAlumno.size(); i++){
            sumatoria += matriculasAlumno.get(i).calcularPromedio();
        }
        return Math.round((sumatoria/matriculasAlumno.size()) * 100.0)/ 100.0;
    }

    public Alumno obtenerAlumnoConMejorPromedio(AlumnoRepo listaAlumnos){
        if (listaAlumnos.listarAlumnos().isEmpty()) return null;
        Alumno alumnoPromedioMayor = listaAlumnos.listarAlumnos().get(0);
        double promedioMayor = obtenerPromedioAlumno(listaAlumnos.listarAlumnos().get(0).getCodigo());
        for (int i = 1 ; i < listaAlumnos.listarAlumnos().size(); i++){
            if (promedioMayor < obtenerPromedioAlumno(listaAlumnos.listarAlumnos().get(i).getCodigo())){
                promedioMayor = obtenerPromedioAlumno(listaAlumnos.listarAlumnos().get(i).getCodigo());
                alumnoPromedioMayor = listaAlumnos.listarAlumnos().get(i);
            }
        }
        return alumnoPromedioMayor;
    }

    public ArrayList<Alumno> ListarDesaprobados(String codigoC){
        ArrayList<Alumno> desaprobados = new ArrayList<>();
        ArrayList<Matricula> matriculados = buscarPorCurso(codigoC);
        for (int i=0;i<matriculados.size();i++){
            if (!matriculados.get(i).isAprobado())
                desaprobados.add(matriculados.get(i).getAlumnoRef());
        }
        return desaprobados;
    }
}