package DSOO_LABS.laboratorio9.model;

public class Usuario {
    protected String nombreUsuario;
    protected String contrasena;
    protected String estado;
    protected String tipo; // "CLIENTE", "EMPLEADO", "ADMINISTRADOR"

    // Constructor vacío para JDBC
    public Usuario() {
        this.nombreUsuario = "";
        this.contrasena = "";
        this.estado = "Activo";
        this.tipo = "";
    }
    
    // Constructor con parámetros (el que ya tienes)
    public Usuario(String nombreUsuario, String contrasena, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.estado = "Activo";
        this.tipo = tipo;
    }

    // Los demás métodos se mantienen igual...
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean validarCredenciales(String usuario, String password) {
        return this.nombreUsuario.equals(usuario) && 
               this.contrasena.equals(password) && 
               this.estado.equals("Activo");
    }

    public void mostrarPermisos() {
        System.out.println("Permisos básicos de usuario");
    }

    @Override
    public String toString() {
        return "Usuario: " + nombreUsuario + " | Tipo: " + tipo + " | Estado: " + estado;
    }
}