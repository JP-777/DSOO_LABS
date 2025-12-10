package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cuenta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepo {
    private List<Cuenta> listaCuentas;

    public CuentaRepo() {
        this.listaCuentas = new ArrayList<>();
        // Cuentas con montos variados y realistas
        listaCuentas.add(new Cuenta("1001", "Ahorros", 2500.00));
        listaCuentas.add(new Cuenta("1002", "Corriente", 15000.00));
        listaCuentas.add(new Cuenta("1003", "Ahorros", 50000.00));
        listaCuentas.add(new Cuenta("1004", "Inversión", 250000.00));
        listaCuentas.add(new Cuenta("1005", "Corriente", 85000.00));
        listaCuentas.add(new Cuenta("1006", "Ahorros", 12000.00));
    }

    public void agregarCuenta(Cuenta c) {
        listaCuentas.add(c);
    }

    public void listarCuentas() {
        if (listaCuentas.isEmpty()) {
            System.out.println("ℹ No hay cuentas registradas.");
        } else {
            System.out.println("\n=== LISTA DE CUENTAS ===");
            for (Cuenta c : listaCuentas) {
                System.out.println("Número: " + c.getNumeroCuenta() + 
                                 " | Tipo: " + c.getTipoCuenta() + 
                                 " | Saldo: S/ " + String.format("%.2f", c.getSaldo()));
            }
            System.out.println("=========================\n");
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
            System.out.println("✓ Cuenta eliminada correctamente: " + numero);
        } else {
            System.out.println("❌ No se encontró la cuenta: " + numero);
        }
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }
}