package DSOO_LABS.laboratorio9.model;

public class UsuarioCliente extends Usuario {
    private Cliente cliente;

    public UsuarioCliente(String nombreUsuario, String contrasena, Cliente cliente) {
        super(nombreUsuario, contrasena, "CLIENTE");
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("\n=== PERMISOS DE USUARIO CLIENTE ===");
        System.out.println("✓ Consultar saldo");
        System.out.println("✓ Realizar depósitos");
        System.out.println("✓ Realizar retiros");
        System.out.println("✓ Ver movimientos de sus cuentas");
        System.out.println("✗ No puede gestionar clientes");
        System.out.println("✗ No puede gestionar empleados");
        System.out.println("✗ No puede gestionar cuentas de otros");
        System.out.println("=====================================");
    }

    @Override
    public String toString() {
        return "Usuario Cliente: " + nombreUsuario + " | Cliente: " + cliente.getNombre() + " " + cliente.getApellido();
    }
}