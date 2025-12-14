package DSOO_LABS.laboratorio9.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private String idCliente;
    private String correo;
    private String estado;
    private List<Cuenta> cuentas;

    // ========== CONSTRUCTORES ==========
    
    // Constructor VACÍO (OBLIGATORIO para JDBC/Reflection)
    public Cliente() {
        super("", "", "", "", "");
        this.idCliente = "";
        this.correo = "";
        this.estado = "ACTIVO";
        this.cuentas = new ArrayList<>();
    }
    
    // Constructor con todos los parámetros (el que ya tenías)
    public Cliente(String idCliente, String dni, String nombre, String apellido, 
                   String direccion, String telefono, String correo, String estado) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idCliente = idCliente;
        this.correo = correo;
        this.estado = estado;
        this.cuentas = new ArrayList<>();
    }
    
    // Constructor SIN lista de cuentas (útil para DAO)
    public Cliente(String idCliente, String dni, String nombre, String apellido, 
                   String direccion, String telefono, String correo, String estado,
                   List<Cuenta> cuentas) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idCliente = idCliente;
        this.correo = correo;
        this.estado = estado;
        this.cuentas = (cuentas != null) ? cuentas : new ArrayList<>();
    }

    // ========== GETTERS Y SETTERS ==========
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    // ========== MÉTODOS PARA GESTIÓN DE CUENTAS ==========
    public void agregarCuenta(Cuenta cuenta) {
        if (!cuentas.contains(cuenta)) {
            cuentas.add(cuenta);
            System.out.println("Cuenta " + cuenta.getNumeroCuenta() + 
                             " agregada al cliente " + nombre);
        }
    }

    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumeroCuenta().equals(numeroCuenta));
    }

    public boolean tieneCuenta(String numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return true;
            }
        }
        return false;
    }

    // ========== MÉTODO toString() ==========
    @Override
    public String toString() {
        return "Cliente [ID: " + idCliente + ", Nombre: " + nombre + " " + apellido + 
               ", DNI: " + dni + ", Correo: " + correo + ", Estado: " + estado + 
               ", Cuentas: " + cuentas.size() + "]";
    }
    
    // ========== MÉTODO para mostrar info simple ==========
    public String infoBasica() {
        return idCliente + " - " + nombre + " " + apellido + " (" + dni + ")";
    }
}