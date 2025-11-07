package DSOO_LABS.laboratorio7.model;

import java.time.LocalDate;

public class Titularidad {
    private Cliente cliente;
    private Cuenta cuenta;
    private LocalDate fechaInicio;
    private String tipoTitular; 

    public Titularidad(Cliente cliente, Cuenta cuenta, LocalDate fechaInicio, String tipoTitular) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.fechaInicio = fechaInicio;
        this.tipoTitular = tipoTitular;
    }

    public Cliente getCliente() { return cliente; }
    public Cuenta getCuenta() { return cuenta; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public String getTipoTitular() { return tipoTitular; }
}
