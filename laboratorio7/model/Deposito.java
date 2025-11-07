package DSOO_LABS.laboratorio7.model;

import java.time.LocalDateTime;

public class Deposito extends Transaccion {

    public Deposito(String idTransaccion, LocalDateTime fecha, double monto, Empleado empleado, Cuenta cuenta) {
        super(idTransaccion, fecha, monto, empleado, cuenta);
    }

    @Override
    public void procesar() {
        cuenta.depositar(monto);
        cuenta.agregarTransaccion(this);
        System.out.println("Depósito procesado correctamente. Monto: S/ " + String.format("%.2f", monto));
    }
}
