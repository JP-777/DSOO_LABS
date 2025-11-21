package DSOO_LABS.laboratorio7.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldo;
    private LocalDate fechaApertura;
    private List<Transaccion> transacciones;

    public Cuenta(String numeroCuenta, String tipoCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.fechaApertura = LocalDate.now();
        this.transacciones = new ArrayList<>();
    }

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
