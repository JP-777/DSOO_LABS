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

    public String getIdCliente() { return idCliente; }
    public String getCorreo() { return correo; }
    public String getEstado() { return estado; }
    public List<Cuenta> getCuentas() { return cuentas; }

    public void setCorreo(String correo) { this.correo = correo; }
    public void setEstado(String estado) { this.estado = estado; }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("ID Cliente: " + idCliente);
        System.out.println("Correo: " + correo);
        System.out.println("Estado: " + estado);
    }
}
