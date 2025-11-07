package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Transaccion;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio general para todas las transacciones (depósitos y retiros).
 */
public class TransaccionRepo {
    private List<Transaccion> listaTransacciones;

    public TransaccionRepo() {
        this.listaTransacciones = new ArrayList<>();
    }

    public void registrarTransaccion(Transaccion t) {
        listaTransacciones.add(t);
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void listarTransacciones() {
        if (listaTransacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
        } else {
            System.out.println("=== LISTA DE TRANSACCIONES ===");
            for (Transaccion t : listaTransacciones) {
                System.out.println(t);
            }
        }
    }
}
