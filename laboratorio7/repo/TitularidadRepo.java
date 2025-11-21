package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cliente;
import DSOO_LABS.laboratorio7.model.Cuenta;
import DSOO_LABS.laboratorio7.model.Titularidad;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TitularidadRepo {
    private List<Titularidad> listaTitularidades;
    private ClienteRepo clienteRepo;
    private CuentaRepo cuentaRepo;

    public TitularidadRepo(ClienteRepo clienteRepo, CuentaRepo cuentaRepo) {
        this.clienteRepo = clienteRepo;
        this.cuentaRepo = cuentaRepo;
        this.listaTitularidades = new ArrayList<>();

        // Relación inicial
        Cliente c1 = clienteRepo.buscarPorId("C001");
        Cuenta cu1 = cuentaRepo.buscarPorNumero("1001");
        if (c1 != null && cu1 != null) {
            listaTitularidades.add(new Titularidad(c1, cu1, "Principal"));
        }
    }

    public List<Cuenta> obtenerCuentasPorCliente(String idCliente) {
        List<Cuenta> cuentas = new ArrayList<>();
        for (Titularidad t : listaTitularidades) {
            if (t.getCliente().getIdCliente().equalsIgnoreCase(idCliente)) {
                cuentas.add(t.getCuenta());
            }
        }
        return cuentas;
    }

    public void agregarTitularidad(Titularidad t) {
        listaTitularidades.add(t);
    }
}
