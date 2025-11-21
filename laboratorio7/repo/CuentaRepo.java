package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cuenta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepo {
    private List<Cuenta> listaCuentas;

    public CuentaRepo() {
        this.listaCuentas = new ArrayList<>();
        listaCuentas.add(new Cuenta("1001", "Ahorros", 1500));
        listaCuentas.add(new Cuenta("1002", "Corriente", 2300));
    }

    public void agregarCuenta(Cuenta c) {
        listaCuentas.add(c);
    }

    public void listarCuentas() {
        if (listaCuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
        } else {
            System.out.println("Lista de cuentas:");
            for (Cuenta c : listaCuentas) {
                System.out.println("Número: " + c.getNumeroCuenta() + " | Tipo: " + c.getTipoCuenta() + " | Saldo: S/ " + c.getSaldo());
            }
        }
    }

    public Cuenta buscarPorNumero(String numero) {
        for (Cuenta c : listaCuentas) {
            if (c.getNumeroCuenta().equalsIgnoreCase(numero)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCuenta(String numero) {
        Cuenta c = buscarPorNumero(numero);
        if (c != null) {
            listaCuentas.remove(c);
            System.out.println("Cuenta eliminada correctamente.");
        } else {
            System.out.println("No se encontró la cuenta.");
        }
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }
}
