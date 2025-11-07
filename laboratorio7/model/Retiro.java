package DSOO_LABS.laboratorio7.model;

import java.time.LocalDateTime;

/**
 * Representa un retiro de una cuenta bancaria.
 */
public class Retiro extends Transaccion {

    public Retiro(String idTransaccion, LocalDateTime fecha, double monto, Empleado empleado, Cuenta cuenta) {
        super(idTransaccion, fecha, monto, empleado, cuenta);
    }

    @Override
    public void procesar() {
        if (cuenta.getSaldo() >= monto) {
            cuenta.retirar(monto);
            cuenta.agregarTransaccion(this);
            System.out.println("Retiro procesado correctamente. Monto: S/ " + String.format("%.2f", monto));
        } else {
            System.out.println("Fondos insuficientes. No se pudo realizar el retiro.");
        }
    }
}
