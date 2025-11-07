package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cuenta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para cuentas bancarias.
 */
public class CuentaRepo {
    private List<Cuenta> listaCuentas;

    public CuentaRepo() {
        this.listaCuentas = new ArrayList<>();

        // Datos precargados
        listaCuentas.add(new Cuenta("1001", "Ahorros", 1500.00, LocalDate.of(2024, 1, 10)));
        listaCuentas.add(new Cuenta("1002", "Corriente", 2500.00, LocalDate.of(2024, 3, 5)));
    }

    public void agregarCuenta(Cuenta c) {
        listaCuentas.add(c);
    }

    public Cuenta buscarPorNumero(String numeroCuenta) {
        for (Cuenta c : listaCuentas) {
            if (c.getNumeroCuenta().equalsIgnoreCase(numeroCuenta)) return c;
        }
        return null;
    }

    public void listarCuentas() {
        System.out.println("=== LISTA DE CUENTAS ===");
        for (Cuenta c : listaCuentas) {
            c.mostrarDatos();
            System.out.println("----------------------------");
        }
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }
}
