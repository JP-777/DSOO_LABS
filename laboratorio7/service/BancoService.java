package DSOO_LABS.laboratorio7.service;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.repo.*;

package service;

import model.*;
import repo.*;
import java.util.List;

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

    // CLIENTES
    public void listarClientes() {
        clienteRepo.listarClientes();
    }

    public void agregarCliente(String id, String dni, String nombre, String apellido, String direccion, String telefono, String correo) {
        Cliente nuevo = new Cliente(id, dni, nombre, apellido, direccion, telefono, correo, "Activo");
        clienteRepo.agregarCliente(nuevo);
        System.out.println("Cliente agregado correctamente: " + nombre + " " + apellido);
    }

    public void buscarClientePorId(String id) {
        Cliente cliente = clienteRepo.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
        } else {
            System.out.println(cliente);
        }
    }

    public void eliminarCliente(String id) {
        clienteRepo.eliminarCliente(id);
    }

    // EMPLEADOS
    public void listarEmpleados() {
        empleadoRepo.listarEmpleados();
    }

    public void agregarEmpleado(String id, String dni, String nombre, String apellido, String direccion, String telefono, String cargo) {
        Empleado e = new Empleado(id, dni, nombre, apellido, direccion, telefono, cargo);
        empleadoRepo.agregarEmpleado(e);
        System.out.println("Empleado agregado correctamente.");
    }

    // CUENTAS
    public void listarCuentas() {
        cuentaRepo.listarCuentas();
    }

    public void agregarCuenta(String numero, String tipo, double saldo) {
        Cuenta c = new Cuenta(numero, tipo, saldo);
        cuentaRepo.agregarCuenta(c);
        System.out.println("Cuenta agregada correctamente.");
    }

    public void eliminarCuenta(String numero) {
        cuentaRepo.eliminarCuenta(numero);
    }

    public void consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("No existe una cuenta con ese número.");
            return;
        }
        System.out.println("Saldo actual de la cuenta " + numeroCuenta + ": S/ " + cuenta.getSaldo());
    }

    // TRANSACCIONES
    public void realizarDeposito(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("No se encontró la cuenta.");
            return;
        }

        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);
        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        Transaccion deposito = new Deposito("T" + System.currentTimeMillis(), monto, empleado, cuenta);
        deposito.procesar();
        cuenta.agregarTransaccion(deposito);
        transaccionRepo.agregarTransaccion(deposito);
        System.out.println("Depósito realizado correctamente.");
    }

    public void realizarRetiro(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("No se encontró la cuenta.");
            return;
        }

        if (cuenta.getSaldo() < monto) {
            System.out.println("Saldo insuficiente para realizar el retiro.");
            return;
        }

        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);
        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        Transaccion retiro = new Retiro("T" + System.currentTimeMillis(), monto, empleado, cuenta);
        retiro.procesar();
        cuenta.agregarTransaccion(retiro);
        transaccionRepo.agregarTransaccion(retiro);
        System.out.println("Retiro realizado correctamente.");
    }

    public void verMovimientos(String numeroCuenta) {
        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        List<Transaccion> transacciones = cuenta.getTransacciones();
        if (transacciones.isEmpty()) {
            System.out.println("No hay movimientos registrados en esta cuenta.");
        } else {
            System.out.println("Movimientos de la cuenta " + numeroCuenta + ":");
            for (Transaccion t : transacciones) {
                System.out.println(" - " + t.getClass().getSimpleName() + " | Monto: S/ " + t.getMonto() + " | Fecha: " + t.getFecha());
            }
        }
    }

    public void listarTransacciones() {
        transaccionRepo.listarTransacciones();
    }
}
