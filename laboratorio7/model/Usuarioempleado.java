package DSOO_LABS.laboratorio7.model;

public class UsuarioEmpleado extends Usuario {
    private Empleado empleado;

    public UsuarioEmpleado(String nombreUsuario, String contrasena, Empleado empleado) {
        super(nombreUsuario, contrasena, "EMPLEADO");
        this.empleado = empleado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("\n=== PERMISOS DE USUARIO EMPLEADO ===");
        System.out.println("✓ Gestionar cuentas (crear, consultar)");
        System.out.println("✓ Atender transacciones (depósitos, retiros)");
        System.out.println("✓ Consultar información de clientes");
        System.out.println("✓ Ver movimientos de cualquier cuenta");
        System.out.println("✗ No puede agregar/eliminar clientes");
        System.out.println("✗ No puede agregar/eliminar empleados");
        System.out.println("=====================================");
    }

    @Override
    public String toString() {
        return "Usuario Empleado: " + nombreUsuario + " | Empleado: " + empleado.getNombre() + " " + empleado.getApellido() + " | Cargo: " + empleado.getCargo();
    }
}