package DSOO_LABS.laboratorio7.model;

public class UsuarioAdministrador extends Usuario {
    private String departamento;

    // Constructor vacío para JDBC
    public UsuarioAdministrador() {
        super();
        this.departamento = "";
        this.tipo = "ADMINISTRADOR";
    }
    
    // Constructor con parámetros
    public UsuarioAdministrador(String nombreUsuario, String contrasena, String departamento) {
        super(nombreUsuario, contrasena, "ADMINISTRADOR");
        this.departamento = departamento;
    }

    // Los demás métodos se mantienen igual...
    public String getDepartamento() {
        return departamento;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("\n=== PERMISOS DE ADMINISTRADOR ===");
        System.out.println("✓ Acceso total al sistema");
        System.out.println("✓ Agregar/eliminar clientes");
        System.out.println("✓ Agregar/eliminar empleados");
        System.out.println("✓ Crear/eliminar cuentas");
        System.out.println("✓ Realizar cualquier transacción");
        System.out.println("✓ Ver todas las transacciones");
        System.out.println("✓ Gestionar usuarios del sistema");
        System.out.println("=====================================");
    }

    @Override
    public String toString() {
        return "Usuario Administrador: " + nombreUsuario + " | Departamento: " + departamento;
    }
}