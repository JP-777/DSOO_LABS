package repo;
import model.Docente;
import util.Validations;
import java.util.ArrayList;
public class DocenteRepo {
    private final ArrayList<Docente> docentes = new ArrayList<>();
    public void agregarDocente(Docente docente) {
        Validations.noVacio(docente.getDni(), "dni");
        if (buscarPorDni(docente.getDni()) != null) throw new IllegalArgumentException("DNI de docente repetido");
        docentes.add(docente);
    }
    public Docente buscarPorDni(String dni) {
        for (Docente docente : docentes) if (docente.getDni().equalsIgnoreCase(dni)) return docente;
        return null;
    }
    public ArrayList<Docente> mostrarTodos() { return new ArrayList<>(docentes); }
}
