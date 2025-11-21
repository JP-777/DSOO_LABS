package DSOO_LABS.laboratorio7.model;

public class Empleado extends Persona {
    private String idEmpleado;
    private String cargo;

    public Empleado(String idEmpleado, String dni, String nombre, String apellido, String direccion, String telefono, String cargo) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idEmpleado = idEmpleado;
        this.cargo = cargo;
    }

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
