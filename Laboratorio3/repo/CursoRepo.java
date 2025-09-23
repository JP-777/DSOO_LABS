package repo;

import model.Curso;
import java.util.ArrayList;

public class CursoRepo {
    private final ArrayList<Curso> cursos = new ArrayList<>();

    public void agregarCurso(Curso curso) {
        cursos.add(curso);
    }

    public Curso buscarPorCodigo(String codigo) {
        for (Curso curso : cursos) {
            if (curso.getCodigo().equalsIgnoreCase(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public ArrayList<Curso> mostrarTodos() {
        return new ArrayList<>(cursos);
    }
}
