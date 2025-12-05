package DSOO_LABS.laboratorio7.model;

public class Persona {
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String direccion;
    protected String telefono;

    public Persona(String dni, String nombre, String apellido, String direccion, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void mostrarDatos() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " | DNI: " + dni + " | Teléfono: " + telefono + " | Dirección: " + direccion;
    }
}
