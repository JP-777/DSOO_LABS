package DSOO_LABS.laboratorio7.model;

import java.time.LocalDateTime;

public abstract class Transaccion {
    protected String idTransaccion;
    protected LocalDateTime fecha;
    protected double monto;
    protected Empleado empleado; 
    protected Cuenta cuenta;     

    public Transaccion(String idTransaccion, LocalDateTime fecha, double monto, Empleado empleado, Cuenta cuenta) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.monto = monto;
        this.empleado = empleado;
        this.cuenta = cuenta;
    }

    public String getIdTransaccion() { return idTransaccion; }
    public LocalDateTime getFecha() { return fecha; }
    public double getMonto() { return monto; }
    public Empleado getEmpleado() { return empleado; }
    public Cuenta getCuenta() { return cuenta; }

    public abstract void procesar();

    @Override
    public String toString() {
        return String.format("[%s] Monto: S/ %.2f | Fecha: %s | Procesado por: %s",
                idTransaccion, monto, fecha, empleado.getNombre());
    }
}
