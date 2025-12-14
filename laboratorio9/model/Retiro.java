package DSOO_LABS.laboratorio9.model;


public class Retiro extends Transaccion {

    public Retiro(String idTransaccion, double monto, Empleado empleado, Cuenta cuenta) {
        super(idTransaccion, monto, empleado, cuenta);
    }

    @Override
    public void procesar() {
        cuenta.retirar(monto);
    }
}
