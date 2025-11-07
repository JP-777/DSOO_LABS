package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cliente;
import DSOO_LABS.laboratorio7.model.Cuenta;
import DSOO_LABS.laboratorio7.model.Titularidad;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TitularidadRepo {
    private List<Titularidad> listaTitularidades;

    public TitularidadRepo(ClienteRepo clienteRepo, CuentaRepo cuentaRepo) {
        this.listaTitularidades = new ArrayList<>();

        Cliente ana = clienteRepo.buscarPorId("C001");
        Cliente luis = clienteRepo.buscarPorId("C002");
        Cuenta cta1 = cuentaRepo.buscarPorNumero("1001");
        Cuenta cta2 = cuentaRepo.buscarPorNumero("1002");

        if (ana != null && cta1 != null)
            listaTitularidades.add(new Titularidad(ana, cta1, LocalDate.of(2024, 1, 10), "Principal"));
        if (luis != null && cta2 != null)
            listaTitularidades.add(new Titularidad(luis, cta2, LocalDate.of(2024, 3, 5), "Principal"));
    }

    public List<Titularidad> obtenerTitularesPorCuenta(Cuenta cuenta) {
        List<Titularidad> resultado = new ArrayList<>();
        for (Titularidad t : listaTitularidades) {
            if (t.getCuenta().equals(cuenta)) resultado.add(t);
        }
        return resultado;
    }

    public List<Titularidad> getListaTitularidades() {
        return listaTitularidades;
    }
}
