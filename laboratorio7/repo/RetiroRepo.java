package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Retiro;

/**
 * Repositorio específico para retiros.
 */
public class RetiroRepo extends TransaccionRepo {
    public void registrarRetiro(Retiro r) {
        super.registrarTransaccion(r);
    }
}
