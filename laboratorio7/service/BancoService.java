package DSOO_LABS.laboratorio7.service;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.repo.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BancoService {

    private ClienteRepo clienteRepo;
    private EmpleadoRepo empleadoRepo;
    private CuentaRepo cuentaRepo;
    private TitularidadRepo titularidadRepo;
    private TransaccionRepo transaccionRepo;

    public BancoService() {
        this.clienteRepo = new ClienteRepo();
        this.empleadoRepo = new EmpleadoRepo();
        this.cuentaRepo = new CuentaRepo();
        this.titularidadRepo = new TitularidadRepo(clienteRepo, cuentaRepo);
        this.transaccionRepo = new TransaccionRepo();
    }
    public void listarClientes() {
        clienteRepo.listarClientes();
    }

    public void listarEmpleados() {
        empleadoRepo.listarEmpleados();
    }

    public void listarCuentas() {
        cuentaRepo.listarCuentas();
    }

    public void listarTransacciones() {
        transaccionRepo.listarTransacciones();
    }

    public void consultarCuentasDeCliente(String idCliente) {
        Cliente cliente = clienteRepo.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("No se encontró un cliente con el ID ingresado.");
            return;
        }

        System.out.println("Cuentas del cliente " + cliente.getNombre() + " " + cliente.getApellido() + ":");
        for (Titularidad t : titularidadRepo.getListaTitularidades()) {
            if (t.getCliente().equals(cliente)) {
                t.getCuenta().mostrarDatos();
                System.out.println("----------------------------");
            }
        }
    }

    public void consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("No se encontró una cuenta con el número indicado.");
            return;
        }
        System.out.println("El saldo actual de la cuenta " + numeroCuenta + " es: S/ " +
                String.format("%.2f", cuenta.getSaldo()));
    }

    public void verMovimientos(String numeroCuenta) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("No se encontró la cuenta indicada.");
            return;
        }

        List<Transaccion> movimientos = cuenta.getTransacciones();
        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados para esta cuenta.");
        } else {
            System.out.println("Movimientos de la cuenta " + numeroCuenta + ":");
            for (Transaccion t : movimientos) {
                System.out.println(t);
            }
        }
    }

    public void realizarDeposito(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);

        if (cuenta == null) {
            System.out.println("No se encontró la cuenta indicada.");
            return;
        }
        if (empleado == null) {
            System.out.println("No se encontró el empleado que procesa la operación.");
            return;
        }

        Deposito deposito = new Deposito(
                "D" + (transaccionRepo.getListaTransacciones().size() + 1),
                LocalDateTime.now(),
                monto,
                empleado,
                cuenta
        );
        deposito.procesar();
        transaccionRepo.registrarTransaccion(deposito);
    }

    public void realizarRetiro(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);

        if (cuenta == null) {
            System.out.println("No se encontró la cuenta indicada.");
            return;
        }
        if (empleado == null) {
            System.out.println("No se encontró el empleado que procesa la operación.");
            return;
        }

        Retiro retiro = new Retiro(
                "R" + (transaccionRepo.getListaTransacciones().size() + 1),
                LocalDateTime.now(),
                monto,
                empleado,
                cuenta
        );
        retiro.procesar();
        transaccionRepo.registrarTransaccion(retiro);
    }

    public void menuInteractivo() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========= MENÚ DEL BANCO =========");
            System.out.println("1. Listar clientes");
            System.out.println("2. Consultar cuentas de un cliente");
            System.out.println("3. Consultar saldo de una cuenta");
            System.out.println("4. Realizar depósito");
            System.out.println("5. Realizar retiro");
            System.out.println("6. Ver movimientos de una cuenta");
            System.out.println("7. Listar transacciones");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    System.out.print("Ingrese el ID del cliente: ");
                    String idCliente = sc.nextLine();
                    consultarCuentasDeCliente(idCliente);
                    break;
                case 3:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroCuenta = sc.nextLine();
                    consultarSaldo(nroCuenta);
                    break;
                case 4:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroCtaDep = sc.nextLine();
                    System.out.print("Ingrese el monto a depositar: ");
                    double montoDep = sc.nextDouble();
                    sc.nextLine();
                    realizarDeposito(nroCtaDep, montoDep, "E001");
                    break;
                case 5:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroCtaRet = sc.nextLine();
                    System.out.print("Ingrese el monto a retirar: ");
                    double montoRet = sc.nextDouble();
                    sc.nextLine();
                    realizarRetiro(nroCtaRet, montoRet, "E001");
                    break;
                case 6:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroMov = sc.nextLine();
                    verMovimientos(nroMov);
                    break;
                case 7:
                    listarTransacciones();
                    break;
                case 8:
                    System.out.println("Gracias por usar el sistema bancario. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 8);
    }
}
