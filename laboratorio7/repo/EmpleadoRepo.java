package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepo {
    private List<Empleado> listaEmpleados;

    public EmpleadoRepo() {
        this.listaEmpleados = new ArrayList<>();

        // Datos precargados
        listaEmpleados.add(new Empleado("E001", "11112222", "Carlos", "Ramírez", "Av. Bolognesi 222", "912345678", "Cajero"));
    }

    public Empleado buscarPorId(String idEmpleado) {
        for (Empleado e : listaEmpleados) {
            if (e.getIdEmpleado().equalsIgnoreCase(idEmpleado)) return e;
        }
        return null;
    }

    public void listarEmpleados() {
        System.out.println("=== LISTA DE EMPLEADOS ===");
        for (Empleado e : listaEmpleados) {
            e.mostrarDatos();
            System.out.println("----------------------------");
        }
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }
}
