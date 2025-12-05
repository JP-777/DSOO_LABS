package service;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.repo.*;
import DSOO_LABS.laboratorio7.util.Validador;

import java.util.List;

public class BancoService {
    private ClienteRepo clienteRepo;
    private EmpleadoRepo empleadoRepo;
    private CuentaRepo cuentaRepo;
    private TitularidadRepo titularidadRepo;
    private TransaccionRepo transaccionRepo;
    private Usuario usuarioActual;

    public BancoService() {
        this.clienteRepo = new ClienteRepo();
        this.empleadoRepo = new EmpleadoRepo();
        this.cuentaRepo = new CuentaRepo();
        this.titularidadRepo = new TitularidadRepo(clienteRepo, cuentaRepo);
        this.transaccionRepo = new TransaccionRepo();
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Método para verificar permisos
    private boolean tienePermiso(String accion) {
        if (usuarioActual == null) {
            System.out.println("No hay un usuario autenticado.");
            return false;
        }

        String tipo = usuarioActual.getTipo();

        switch (accion) {
            case "AGREGAR_CLIENTE":
            case "ELIMINAR_CLIENTE":
            case "AGREGAR_EMPLEADO":
            case "ELIMINAR_EMPLEADO":
                return tipo.equals("ADMINISTRADOR");
                
            case "LISTAR_CLIENTES":
            case "BUSCAR_CLIENTE":
            case "LISTAR_EMPLEADOS":
            case "AGREGAR_CUENTA":
            case "LISTAR_CUENTAS":
            case "ELIMINAR_CUENTA":
                return tipo.equals("ADMINISTRADOR") || tipo.equals("EMPLEADO");
                
            case "CONSULTAR_SALDO":
            case "DEPOSITO":
            case "RETIRO":
            case "VER_MOVIMIENTOS":
                return true; // Todos los usuarios autenticados pueden hacer estas operaciones
                
            case "LISTAR_TRANSACCIONES":
                return tipo.equals("ADMINISTRADOR") || tipo.equals("EMPLEADO");
                
            default:
                return false;
        }
    }

    private void mostrarErrorPermiso(String accion) {
        System.out.println("\n ACCESO DENEGADO ");
        System.out.println("Tu rol '" + usuarioActual.getTipo() + "' no tiene permisos para: " + accion);
        System.out.println("Contacta al administrador si necesitas acceso.\n");
    }

    // CLIENTES
    public void listarClientes() {
        if (!tienePermiso("LISTAR_CLIENTES")) {
            mostrarErrorPermiso("Listar clientes");
            return;
        }
        clienteRepo.listarClientes();
    }

    public void agregarCliente(String id, String dni, String nombre, String apellido, 
                               String direccion, String telefono, String correo) {
        if (!tienePermiso("AGREGAR_CLIENTE")) {
            mostrarErrorPermiso("Agregar clientes");
            return;
        }

        // Validaciones
        if (!Validador.validarId(id)) {
            Validador.mostrarErrorValidacion("ID", "No puede estar vacío");
            return;
        }

        if (!Validador.validarDatosCliente(dni, nombre, apellido, direccion, telefono, correo)) {
            System.out.println(" No se pudo agregar el cliente debido a errores de validación.\n");
            return;
        }

        // Verificar si el ID o DNI ya existen
        if (clienteRepo.buscarPorId(id) != null) {
            System.out.println(" Ya existe un cliente con el ID: " + id);
            return;
        }

        Cliente nuevo = new Cliente(id, dni, nombre, apellido, direccion, telefono, correo, "Activo");
        clienteRepo.agregarCliente(nuevo);
        System.out.println("✓ Cliente agregado correctamente: " + nombre + " " + apellido);
    }

    public void buscarClientePorId(String id) {
        if (!tienePermiso("BUSCAR_CLIENTE")) {
            mostrarErrorPermiso("Buscar clientes");
            return;
        }

        if (!Validador.validarId(id)) {
            Validador.mostrarErrorValidacion("ID", "No puede estar vacío");
            return;
        }

        Cliente cliente = clienteRepo.buscarPorId(id);
        if (cliente == null) {
            System.out.println(" Cliente no encontrado con ID: " + id);
        } else {
            System.out.println("✓ Cliente encontrado:");
            System.out.println(cliente);
        }
    }

    public void eliminarCliente(String id) {
        if (!tienePermiso("ELIMINAR_CLIENTE")) {
            mostrarErrorPermiso("Eliminar clientes");
            return;
        }

        if (!Validador.validarId(id)) {
            Validador.mostrarErrorValidacion("ID", "No puede estar vacío");
            return;
        }

        clienteRepo.eliminarCliente(id);
    }

    // EMPLEADOS
    public void listarEmpleados() {
        if (!tienePermiso("LISTAR_EMPLEADOS")) {
            mostrarErrorPermiso("Listar empleados");
            return;
        }
        empleadoRepo.listarEmpleados();
    }

    public void agregarEmpleado(String id, String dni, String nombre, String apellido, 
                                String direccion, String telefono, String cargo) {
        if (!tienePermiso("AGREGAR_EMPLEADO")) {
            mostrarErrorPermiso("Agregar empleados");
            return;
        }

        if (!Validador.validarId(id)) {
            Validador.mostrarErrorValidacion("ID", "No puede estar vacío");
            return;
        }

        if (!Validador.validarDatosEmpleado(dni, nombre, apellido, direccion, telefono, cargo)) {
            System.out.println(" No se pudo agregar el empleado debido a errores de validación.\n");
            return;
        }

        if (empleadoRepo.buscarPorId(id) != null) {
            System.out.println(" Ya existe un empleado con el ID: " + id);
            return;
        }

        Empleado e = new Empleado(id, dni, nombre, apellido, direccion, telefono, cargo);
        empleadoRepo.agregarEmpleado(e);
        System.out.println("✓ Empleado agregado correctamente: " + nombre + " " + apellido);
    }

    // CUENTAS
    public void listarCuentas() {
        if (!tienePermiso("LISTAR_CUENTAS")) {
            mostrarErrorPermiso("Listar cuentas");
            return;
        }
        cuentaRepo.listarCuentas();
    }

    public void agregarCuenta(String numero, String tipo, double saldo) {
        if (!tienePermiso("AGREGAR_CUENTA")) {
            mostrarErrorPermiso("Agregar cuentas");
            return;
        }

        if (!Validador.validarNumeroCuenta(numero)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Debe tener al menos 4 dígitos");
            return;
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            Validador.mostrarErrorValidacion("Tipo de cuenta", "No puede estar vacío");
            return;
        }

        if (!Validador.validarMonto(saldo)) {
            Validador.mostrarErrorValidacion("Saldo inicial", "Debe ser mayor a 0");
            return;
        }

        if (cuentaRepo.buscarPorNumero(numero) != null) {
            System.out.println(" Ya existe una cuenta con ese número: " + numero);
            return;
        }

        Cuenta c = new Cuenta(numero, tipo, saldo);
        cuentaRepo.agregarCuenta(c);
        System.out.println("✓ Cuenta agregada correctamente.");
    }

    public void eliminarCuenta(String numero) {
        if (!tienePermiso("ELIMINAR_CUENTA")) {
            mostrarErrorPermiso("Eliminar cuentas");
            return;
        }

        if (!Validador.validarNumeroCuenta(numero)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Formato inválido");
            return;
        }

        cuentaRepo.eliminarCuenta(numero);
    }

    public void consultarSaldo(String numeroCuenta) {
        if (!tienePermiso("CONSULTAR_SALDO")) {
            mostrarErrorPermiso("Consultar saldo");
            return;
        }

        if (!Validador.validarNumeroCuenta(numeroCuenta)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Formato inválido");
            return;
        }

        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" No existe una cuenta con ese número: " + numeroCuenta);
            return;
        }

        // Si es cliente, solo puede ver sus propias cuentas
        if (usuarioActual.getTipo().equals("CLIENTE")) {
            UsuarioCliente uc = (UsuarioCliente) usuarioActual;
            List<Cuenta> cuentasCliente = titularidadRepo.obtenerCuentasPorCliente(
                uc.getCliente().getIdCliente()
            );
            
            if (!cuentasCliente.contains(cuenta)) {
                System.out.println(" No tienes permiso para consultar esta cuenta.");
                return;
            }
        }

        System.out.println("✓ Saldo actual de la cuenta " + numeroCuenta + ": S/ " + 
                           String.format("%.2f", cuenta.getSaldo()));
    }

    // TRANSACCIONES
    public void realizarDeposito(String numeroCuenta, double monto, String idEmpleado) {
        if (!tienePermiso("DEPOSITO")) {
            mostrarErrorPermiso("Realizar depósitos");
            return;
        }

        if (!Validador.validarNumeroCuenta(numeroCuenta)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Formato inválido");
            return;
        }

        if (!Validador.validarMonto(monto)) {
            Validador.mostrarErrorValidacion("Monto", "Debe ser mayor a 0");
            return;
        }

        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" No se encontró la cuenta: " + numeroCuenta);
            return;
        }

        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);
        if (empleado == null) {
            System.out.println(" Empleado no encontrado: " + idEmpleado);
            return;
        }

        Transaccion deposito = new Deposito("T" + System.currentTimeMillis(), monto, empleado, cuenta);
        deposito.procesar();
        cuenta.agregarTransaccion(deposito);
        transaccionRepo.agregarTransaccion(deposito);
        System.out.println("✓ Depósito de S/ " + String.format("%.2f", monto) + 
                           " realizado correctamente.");
        System.out.println("  Nuevo saldo: S/ " + String.format("%.2f", cuenta.getSaldo()));
    }

    public void realizarRetiro(String numeroCuenta, double monto, String idEmpleado) {
        if (!tienePermiso("RETIRO")) {
            mostrarErrorPermiso("Realizar retiros");
            return;
        }

        if (!Validador.validarNumeroCuenta(numeroCuenta)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Formato inválido");
            return;
        }

        if (!Validador.validarMonto(monto)) {
            Validador.mostrarErrorValidacion("Monto", "Debe ser mayor a 0");
            return;
        }

        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" No se encontró la cuenta: " + numeroCuenta);
            return;
        }

        if (!Validador.validarSaldoSuficiente(cuenta.getSaldo(), monto)) {
            System.out.println(" Saldo insuficiente para realizar el retiro.");
            System.out.println("  Saldo actual: S/ " + String.format("%.2f", cuenta.getSaldo()));
            System.out.println("  Monto solicitado: S/ " + String.format("%.2f", monto));
            return;
        }

        Empleado empleado = empleadoRepo.buscarPorId(idEmpleado);
        if (empleado == null) {
            System.out.println(" Empleado no encontrado: " + idEmpleado);
            return;
        }

        Transaccion retiro = new Retiro("T" + System.currentTimeMillis(), monto, empleado, cuenta);
        retiro.procesar();
        cuenta.agregarTransaccion(retiro);
        transaccionRepo.agregarTransaccion(retiro);
        System.out.println("✓ Retiro de S/ " + String.format("%.2f", monto) + 
                           " realizado correctamente.");
        System.out.println("  Nuevo saldo: S/ " + String.format("%.2f", cuenta.getSaldo()));
    }

    public void verMovimientos(String numeroCuenta) {
        if (!tienePermiso("VER_MOVIMIENTOS")) {
            mostrarErrorPermiso("Ver movimientos");
            return;
        }

        if (!Validador.validarNumeroCuenta(numeroCuenta)) {
            Validador.mostrarErrorValidacion("Número de cuenta", "Formato inválido");
            return;
        }

        Cuenta cuenta = cuentaRepo.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" Cuenta no encontrada: " + numeroCuenta);
            return;
        }

        // Si es cliente, solo puede ver sus propias cuentas
        if (usuarioActual.getTipo().equals("CLIENTE")) {
            UsuarioCliente uc = (UsuarioCliente) usuarioActual;
            List<Cuenta> cuentasCliente = titularidadRepo.obtenerCuentasPorCliente(
                uc.getCliente().getIdCliente()
            );
            
            if (!cuentasCliente.contains(cuenta)) {
                System.out.println(" No tienes permiso para ver los movimientos de esta cuenta.");
                return;
            }
        }

        List<Transaccion> transacciones = cuenta.getTransacciones();
        if (transacciones.isEmpty()) {
            System.out.println("ℹ No hay movimientos registrados en esta cuenta.");
        } else {
            System.out.println("\n=== MOVIMIENTOS DE LA CUENTA " + numeroCuenta + " ===");
            for (Transaccion t : transacciones) {
                String tipo = t.getClass().getSimpleName();
                System.out.println("• " + tipo + " | Monto: S/ " + 
                                   String.format("%.2f", t.getMonto()) + 
                                   " | Fecha: " + t.getFecha());
            }
            System.out.println("=============================================\n");
        }
    }

    public void listarTransacciones() {
        if (!tienePermiso("LISTAR_TRANSACCIONES")) {
            mostrarErrorPermiso("Listar todas las transacciones");
            return;
        }
        transaccionRepo.listarTransacciones();
    }

    // Getters para los repositorios
    public ClienteRepo getClienteRepo() {
        return clienteRepo;
    }

    public EmpleadoRepo getEmpleadoRepo() {
        return empleadoRepo;
    }

    public CuentaRepo getCuentaRepo() {
        return cuentaRepo;
    }
}