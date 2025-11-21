package DSOO_LABS.laboratorio7.model;

import java.time.LocalDate;


public class Titularidad {
    private Cliente cliente;
    private Cuenta cuenta;
    private LocalDate fechaInicio;
    private String tipoTitular;

    public Titularidad(Cliente cliente, Cuenta cuenta, String tipoTitular) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.tipoTitular = tipoTitular;
        this.fechaInicio = LocalDate.now();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public String getTipoTitular() {
        return tipoTitular;
    }

    @Override
    public String toString() {
        return "Titularidad [Cliente: " + cliente.getNombre() + ", Cuenta: " + cuenta.getNumeroCuenta() + ", Tipo: " + tipoTitular + "]";
    }
}
