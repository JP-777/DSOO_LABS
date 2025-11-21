package DSOO_LABS.laboratorio7.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private String idCliente;
    private String correo;
    private String estado;
    private List<Cuenta> cuentas;

    public Cliente(String idCliente, String dni, String nombre, String apellido, String direccion, String telefono, String correo, String estado) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idCliente = idCliente;
        this.correo = correo;
        this.estado = estado;
        this.cuentas = new ArrayList<>();
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public String getEstado() {
        return estado;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(Cuenta c) {
        cuentas.add(c);
    }

    @Override
    public String toString() {
        return "Cliente [ID: " + idCliente + ", Nombre: " + nombre + " " + apellido + ", DNI: " + dni + ", Correo: " + correo + ", Estado: " + estado + "]";
    }
}
