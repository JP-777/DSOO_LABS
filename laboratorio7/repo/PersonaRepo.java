package DSOO_LABS.laboratorio7.repo;


import DSOO_LABS.laboratorio7.model.Persona;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio base para manejar personas (padre de Cliente y Empleado).
 */
public class PersonaRepo {
    protected List<Persona> listaPersonas;

    public PersonaRepo() {
        this.listaPersonas = new ArrayList<>();
    }

    public void agregarPersona(Persona p) {
        listaPersonas.add(p);
    }

    public Persona buscarPorDni(String dni) {
        for (Persona p : listaPersonas) {
            if (p.getDni().equalsIgnoreCase(dni)) return p;
        }
        return null;
    }

    public void listarPersonas() {
        for (Persona p : listaPersonas) {
            p.mostrarDatos();
            System.out.println("----------------------------");
        }
    }
}
