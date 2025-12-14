package DSOO_LABS.laboratorio9.model;

import java.time.LocalDateTime;

public class Deposito extends Transaccion {

    public Deposito(String idTransaccion, double monto, Empleado empleado, Cuenta cuenta) {
        super(idTransaccion, monto, empleado, cuenta);
    }

    @Override
    public void procesar() {
        cuenta.depositar(monto);
    }
}
