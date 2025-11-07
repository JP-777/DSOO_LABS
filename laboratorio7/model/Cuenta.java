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

    public Cuenta(String numeroCuenta, String tipoCuenta, double saldoInicial, LocalDate fechaApertura) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldoInicial;
        this.fechaApertura = fechaApertura;
        this.transacciones = new ArrayList<>();
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipoCuenta() { return tipoCuenta; }
    public double getSaldo() { return saldo; }
    public LocalDate getFechaApertura() { return fechaApertura; }
    public List<Transaccion> getTransacciones() { return transacciones; }

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) {
        saldo -= monto;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.add(t);
    }

    public void mostrarDatos() {
        System.out.println("Número de cuenta: " + numeroCuenta);
        System.out.println("Tipo de cuenta: " + tipoCuenta);
        System.out.println("Saldo actual: S/ " + String.format("%.2f", saldo));
        System.out.println("Fecha de apertura: " + fechaApertura);
    }
}
