package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepo {
    private List<Empleado> listaEmpleados;

    public EmpleadoRepo() {
        this.listaEmpleados = new ArrayList<>();
        // Miembros del grupo como empleados
        listaEmpleados.add(new Empleado("E001", "74123456", "Jordan", "Paredes Saico", 
            "Av. Independencia 234, Arequipa", "987123456", "Administrador General"));
        
        listaEmpleados.add(new Empleado("E002", "73234567", "Kevin", "Peralta Llasa", 
            "Calle Peral 567, Cayma", "965432178", "Gerente de Operaciones"));
        
        listaEmpleados.add(new Empleado("E003", "72345678", "Elkin Eder Brais", "Ramos Ochochoque", 
            "Jr. San Juan 890, Yanahuara", "954321876", "Supervisor de Cuentas"));
        
        listaEmpleados.add(new Empleado("E004", "71456789", "Fernando Rafael", "Solsol Choque", 
            "Av. Ejército 345, Cercado", "943218765", "Jefe de Atención al Cliente"));
        
        listaEmpleados.add(new Empleado("E005", "70567890", "Brayan Stiph", "Sanchez Jacobo", 
            "Calle Bolognesi 678, Miraflores", "932187654", "Administrador de Sistemas"));
        
        System.out.println("✓ Empleados inicializados: " + listaEmpleados.size() + " registros");
    }

    public void agregarEmpleado(Empleado e) {
        listaEmpleados.add(e);
    }

    public void listarEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("ℹ No hay empleados registrados.");
        } else {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  LISTA DE EMPLEADOS                    ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            for (Empleado e : listaEmpleados) {
                System.out.println(e);
                System.out.println("───────────────────────────────────────────────────────");
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
            System.out.println(" ✓ Empleado eliminado correctamente: " + e.getNombre() + " " + e.getApellido());
        } else {
            System.out.println(" X Empleado no encontrado con ID: " + id);
        }
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }
}