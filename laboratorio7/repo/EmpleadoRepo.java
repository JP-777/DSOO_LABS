package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepo {
    private List<Empleado> listaEmpleados;

    public EmpleadoRepo() {
        this.listaEmpleados = new ArrayList<>();
        listaEmpleados.add(new Empleado("E001", "12345678", "Carlos", "Vega", "Av. San Martín 345", "999111222", "Cajero"));
        listaEmpleados.add(new Empleado("E002", "87654321", "Marta", "Soto", "Jr. Colón 789", "988777666", "Asistente"));
    }

    public void agregarEmpleado(Empleado e) {
        listaEmpleados.add(e);
    }

    public void listarEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            System.out.println("Lista de empleados:");
            for (Empleado e : listaEmpleados) {
                System.out.println(e);
            }
        }
    }

    public Empleado buscarPorId(String id) {
        for (Empleado e : listaEmpleados) {
            if (e.getIdEmpleado().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public void eliminarEmpleado(String id) {
        Empleado e = buscarPorId(id);
        if (e != null) {
            listaEmpleados.remove(e);
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }
}

