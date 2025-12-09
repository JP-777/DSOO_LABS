package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Deposito;

public class DepositoRepo extends TransaccionRepo {
    public void registrarDeposito(Deposito d) {
        super.agregarTransaccion(d);
    }
}
