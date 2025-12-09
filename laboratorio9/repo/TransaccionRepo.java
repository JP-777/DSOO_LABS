package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Transaccion;
import java.util.ArrayList;
import java.util.List;

public class TransaccionRepo {
    private List<Transaccion> listaTransacciones;

    public TransaccionRepo() {
        this.listaTransacciones = new ArrayList<>();
    }

    public void agregarTransaccion(Transaccion t) {
        listaTransacciones.add(t);
    }

    public void listarTransacciones() {
        if (listaTransacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
        } else {
            System.out.println("Lista de transacciones:");
            for (Transaccion t : listaTransacciones) {
                System.out.println(t);
            }
        }
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }
}
