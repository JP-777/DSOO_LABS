package DSOO_LABS.laboratorio9.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldo;
    private LocalDate fechaApertura;
    private List<Transaccion> transacciones;

    // Constructor VACÍO para JDBC
    public Cuenta() {
        this.numeroCuenta = "";
        this.tipoCuenta = "";
        this.saldo = 0.0;
        this.fechaApertura = LocalDate.now();
        this.transacciones = new ArrayList<>();
    }
    
    // Constructor con parámetros
    public Cuenta(String numeroCuenta, String tipoCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.fechaApertura = LocalDate.now();
        this.transacciones = new ArrayList<>();
    }

    // Los demás métodos se mantienen igual...
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) {
        saldo -= monto;
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.add(t);
    }

    @Override
    public String toString() {
        return "Cuenta [Número: " + numeroCuenta + ", Tipo: " + tipoCuenta + ", Saldo: S/ " + saldo + "]";
    }
}