package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Retiro;

public class RetiroRepo extends TransaccionRepo {
    public void registrarRetiro(Retiro r) {
        super.agregarTransaccion(r);
    }
}
