package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Deposito;

/**
 * Repositorio específico para depósitos.
 * (Se usa principalmente para filtrar o registrar estadísticas)
 */
public class DepositoRepo extends TransaccionRepo {
    public void registrarDeposito(Deposito d) {
        super.registrarTransaccion(d);
    }
}
