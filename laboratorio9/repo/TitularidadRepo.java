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

        // RELACIONES INICIALES - Asignar cuentas a clientes para pruebas
        inicializarRelaciones();
    }

    private void inicializarRelaciones() {
        System.out.println("⚙ Inicializando relaciones cliente-cuenta...");
        
        // Cliente C001 (Edwar) tiene cuenta 1001 (Ahorros)
        Cliente c1 = clienteRepo.buscarPorId("C001");
        Cuenta cu1 = cuentaRepo.buscarPorNumero("1001");
        if (c1 != null && cu1 != null) {
            listaTitularidades.add(new Titularidad(c1, cu1, "Principal"));
            c1.agregarCuenta(cu1);
            System.out.println("✓ Asignada cuenta 1001 a Edwar Saire");
        }
        
        // Cliente C002 (Elon) tiene cuenta 1002 (Corriente)
        Cliente c2 = clienteRepo.buscarPorId("C002");
        Cuenta cu2 = cuentaRepo.buscarPorNumero("1002");
        if (c2 != null && cu2 != null) {
            listaTitularidades.add(new Titularidad(c2, cu2, "Principal"));
            c2.agregarCuenta(cu2);
            System.out.println("✓ Asignada cuenta 1002 a Elon Musk");
        }
        
        // Cliente C003 (Satya) tiene cuenta 1003 (Ahorros)
        Cliente c3 = clienteRepo.buscarPorId("C003");
        Cuenta cu3 = cuentaRepo.buscarPorNumero("1003");
        if (c3 != null && cu3 != null) {
            listaTitularidades.add(new Titularidad(c3, cu3, "Principal"));
            c3.agregarCuenta(cu3);
            System.out.println("✓ Asignada cuenta 1003 a Satya Nadella");
        }
        
        // Cliente C004 (Tim) tiene cuenta 1005 (Corriente)
        Cliente c4 = clienteRepo.buscarPorId("C004");
        Cuenta cu5 = cuentaRepo.buscarPorNumero("1005");
        if (c4 != null && cu5 != null) {
            listaTitularidades.add(new Titularidad(c4, cu5, "Principal"));
            c4.agregarCuenta(cu5);
            System.out.println("✓ Asignada cuenta 1005 a Tim Cook");
        }
        
        // Cliente C005 (Sundar) tiene cuenta 1006 (Ahorros)
        Cliente c5 = clienteRepo.buscarPorId("C005");
        Cuenta cu6 = cuentaRepo.buscarPorNumero("1006");
        if (c5 != null && cu6 != null) {
            listaTitularidades.add(new Titularidad(c5, cu6, "Principal"));
            c5.agregarCuenta(cu6);
            System.out.println("✓ Asignada cuenta 1006 a Sundar Pichai");
        }
        
        // Cuenta 1004 (Inversión) está sin asignar (para pruebas)
        System.out.println("✓ Cuenta 1004 está disponible para asignar a un cliente");
        System.out.println("✅ Relaciones inicializadas: " + listaTitularidades.size() + " titularidades creadas");
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
    
    // Método para verificar si una cuenta ya está asignada a algún cliente
    public boolean cuentaYaAsignada(String numeroCuenta) {
        for (Titularidad t : listaTitularidades) {
            if (t.getCuenta().getNumeroCuenta().equals(numeroCuenta)) {
                return true;
            }
        }
        return false;
    }
}