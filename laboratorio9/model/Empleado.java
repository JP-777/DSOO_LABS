package DSOO_LABS.laboratorio7.model;

public class Empleado extends Persona {
    private String idEmpleado;
    private String cargo;

    // Constructor VACÍO para JDBC
    public Empleado() {
        super();
        this.idEmpleado = "";
        this.cargo = "";
    }
    
    // Constructor con parámetros (el que ya tienes)
    public Empleado(String idEmpleado, String dni, String nombre, String apellido, 
                    String direccion, String telefono, String cargo) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idEmpleado = idEmpleado;
        this.cargo = cargo;
    }

    // Los getters y demás métodos se mantienen igual...
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Empleado [ID: " + idEmpleado + ", Nombre: " + nombre + " " + apellido + ", Cargo: " + cargo + "]";
    }
}