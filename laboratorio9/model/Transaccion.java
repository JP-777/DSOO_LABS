package DSOO_LABS.laboratorio9.model;

import java.time.LocalDateTime;

public abstract class Transaccion {
    protected String idTransaccion;
    protected LocalDateTime fecha;
    protected double monto;
    protected Empleado empleado;
    protected Cuenta cuenta;

    public Transaccion(String idTransaccion, double monto, Empleado empleado, Cuenta cuenta) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.empleado = empleado;
        this.cuenta = cuenta;
        this.fecha = LocalDateTime.now();
    }
    public Empleado getEmpleado() {
        return empleado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public String getIdTransaccion() {
        return idTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public abstract void procesar();

    @Override
    public String toString() {
        return "Transacción [ID: " + idTransaccion + ", Monto: S/ " + monto + ", Fecha: " + fecha + ", Empleado: " + empleado.getNombre() + "]";
    }
}
