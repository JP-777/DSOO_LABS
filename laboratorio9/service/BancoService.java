package DSOO_LABS.laboratorio9.service;

import DSOO_LABS.laboratorio9.model.*;
import DSOO_LABS.laboratorio9.dao.*;  // ¡NUEVO: Importar DAOs en lugar de repos!
import DSOO_LABS.laboratorio9.util.Validador;

import java.util.List;

public class BancoService {
    // NUEVO: DAOs para acceso a base de datos
    private ClienteDAO clienteDAO;
    private EmpleadoDAO empleadoDAO;
    private CuentaDAO cuentaDAO;
    private TransaccionDAO transaccionDAO;    // NUEVO
    private TitularidadDAO titularidadDAO;    // NUEVO
    private UsuarioDAO usuarioDAO;
    
    private Usuario usuarioActual;

    public BancoService() {
        // NUEVO: Inicializar todos los DAOs
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.cuentaDAO = new CuentaDAO();
        this.transaccionDAO = new TransaccionDAO();    // NUEVO
        this.titularidadDAO = new TitularidadDAO();    // NUEVO
        this.usuarioDAO = new UsuarioDAO();
    }
    public Usuario login(String username, String password) {
        // Usamos el DAO real para verificar en la base de datos
        return usuarioDAO.login(username, password);
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    private boolean tienePermiso(String accion) {
        if (usuarioActual == null) {
            System.out.println("No hay un usuario autenticado.");
            return false;
        }

        String tipo = usuarioActual.getTipo();

        switch (accion) {
            case "AGREGAR_CLIENTE":
                return tipo.equals("ADMINISTRADOR") || tipo.equals("EMPLEADO");
                
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
            case "ASIGNAR_CUENTA":
            case "VER_CUENTAS_CLIENTE":
                return tipo.equals("ADMINISTRADOR") || tipo.equals("EMPLEADO");
                
            case "CONSULTAR_SALDO":
            case "DEPOSITO":
            case "RETIRO":
            case "VER_MOVIMIENTOS":
                return true; // Todos los usuarios autenticados
                
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

    // ========== CLIENTES (ACTUALIZADO para BD) ==========
    public void listarClientes() {
        if (!tienePermiso("LISTAR_CLIENTES")) {
            mostrarErrorPermiso("Listar clientes");
            return;
        }
        
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("ℹ No hay clientes registrados.");
        } else {
            System.out.println("\n=== LISTA DE CLIENTES ===");
            for (Cliente c : clientes) {
                System.out.println(c);
            }
            System.out.println("=========================\n");
        }
    }

   public void agregarCliente(String id, String dni, String nombre, String apellido, 
                               String direccion, String telefono, String correo) {
        
        if (!Validador.validarId(id)) throw new RuntimeException("El ID es obligatorio.");
        if (!Validador.validarDNI(dni)) throw new RuntimeException("El DNI debe tener 8 dígitos exactos.");
        if (!Validador.validarNombre(nombre)) throw new RuntimeException("El nombre solo debe tener letras.");
        if (!Validador.validarCorreo(correo)) throw new RuntimeException("El correo no es válido.");

        // 3. Verificar duplicados
        if (clienteDAO.buscarPorCodigo(id) != null) {
            throw new RuntimeException("Error: Ya existe un cliente con el ID " + id);
        }

        // 4. Guardar
        Cliente nuevo = new Cliente(id, dni, nombre, apellido, direccion, telefono, correo, "Activo");
        if (!clienteDAO.agregarCliente(nuevo)) {
            throw new RuntimeException("Error crítico de BD: No se pudo guardar el cliente.");
        }
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

        // Buscar en BD usando DAO
        Cliente cliente = clienteDAO.buscarPorCodigo(id);
        if (cliente == null) {
            System.out.println(" Cliente no encontrado con ID: " + id);
        } else {
            System.out.println("✓ Cliente encontrado:");
            System.out.println(cliente);
            // Mostrar cuentas del cliente
            mostrarCuentasDeCliente(id);
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

        boolean resultado = clienteDAO.eliminarCliente(id);
        if (resultado) {
            System.out.println("✓ Cliente eliminado correctamente de BD");
        } else {
            System.out.println(" No se pudo eliminar el cliente");
        }
    }

    // ========== EMPLEADOS (ACTUALIZADO para BD) ==========
    public void listarEmpleados() {
        if (!tienePermiso("LISTAR_EMPLEADOS")) {
            mostrarErrorPermiso("Listar empleados");
            return;
        }
        
        List<Empleado> empleados = empleadoDAO.listarTodos();
        if (empleados.isEmpty()) {
            System.out.println("ℹ No hay empleados registrados.");
        } else {
            System.out.println("\n=== LISTA DE EMPLEADOS ===");
            for (Empleado e : empleados) {
                System.out.println(e);
            }
            System.out.println("==========================\n");
        }
    }

    public void agregarEmpleado(String id, String dni, String nombre, String apellido, 
                                String direccion, String telefono, String cargo) {
        
        if (!Validador.validarId(id)) throw new RuntimeException("El ID es obligatorio.");
        if (!Validador.validarDNI(dni)) throw new RuntimeException("El DNI debe tener 8 dígitos exactos.");
        if (!Validador.validarNombre(nombre)) throw new RuntimeException("El nombre solo debe tener letras.");

        // 3. Verificar duplicados
        if (empleadoDAO.buscarPorCodigo(id) != null) {
            throw new RuntimeException("Error: Ya existe un cliente con el ID " + id);
        }

        // 4. Guardar
        Empleado nuevo = new Empleado(id, dni, nombre, apellido, direccion, telefono, cargo);
        if (!empleadoDAO.agregarEmpleado(nuevo)) {
            throw new RuntimeException("Error crítico de BD: No se pudo guardar el empleado.");
        }
    }

    // ========== CUENTAS (ACTUALIZADO para BD) ==========
    public void listarCuentas() {
        if (!tienePermiso("LISTAR_CUENTAS")) {
            mostrarErrorPermiso("Listar cuentas");
            return;
        }
        
        List<Cuenta> cuentas = cuentaDAO.listarTodas();
        if (cuentas.isEmpty()) {
            System.out.println("ℹ No hay cuentas registradas.");
        } else {
            System.out.println("\n=== LISTA DE CUENTAS ===");
            for (Cuenta c : cuentas) {
                System.out.println(c);
            }
            System.out.println("========================\n");
        }
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

        // Verificar si la cuenta ya existe en BD
        Cuenta existente = cuentaDAO.buscarPorNumero(numero);
        if (existente != null) {
            System.out.println(" Ya existe una cuenta con ese número: " + numero);
            return;
        }

        Cuenta nuevaCuenta = new Cuenta(numero, tipo, saldo);
        boolean resultado = cuentaDAO.agregarCuenta(nuevaCuenta);
        if (resultado) {
            System.out.println("✓ Cuenta agregada correctamente a BD.");
            System.out.println(" NOTA: Esta cuenta no está asignada a ningún cliente.");
            System.out.println("   Use 'Asignar cuenta a cliente' para vincularla.");
        } else {
            System.out.println(" Error al agregar cuenta a BD");
        }
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

        boolean resultado = cuentaDAO.eliminarCuenta(numero);
        if (resultado) {
            System.out.println(" Cuenta eliminada correctamente de BD");
        } else {
            System.out.println(" No se pudo eliminar la cuenta");
        }
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

        // Buscar cuenta en BD
        Cuenta cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" No existe una cuenta con ese número: " + numeroCuenta);
            return;
        }

        // Si es cliente, verificar que la cuenta le pertenece
        if (usuarioActual.getTipo().equals("CLIENTE")) {
            List<Cuenta> cuentasCliente = titularidadDAO.obtenerCuentasPorCliente(
                ((UsuarioCliente) usuarioActual).getCliente().getIdCliente()
            );
            
            boolean tieneAcceso = false;
            for (Cuenta c : cuentasCliente) {
                if (c.getNumeroCuenta().equals(numeroCuenta)) {
                    tieneAcceso = true;
                    break;
                }
            }
            
            if (!tieneAcceso) {
                System.out.println(" No tienes permiso para consultar esta cuenta.");
                return;
            }
        }

        System.out.println("✓ Saldo actual de la cuenta " + numeroCuenta + ": S/ " + 
                           String.format("%.2f", cuenta.getSaldo()));
    }

    // ========== TRANSACCIONES (COMPLETAMENTE ACTUALIZADO) ==========
    // REEMPLAZA SOLO ESTOS DOS MÉTODOS EN TU BancoService ACTUAL

    public void realizarDeposito(String numeroCuenta, double monto, String codigoEmpleado) {
        // Validaciones que lanzan "Bombas" (Excepciones) para la GUI
        if (!Validador.validarNumeroCuenta(numeroCuenta)) {
            throw new RuntimeException("El número de cuenta es inválido.");
        }
        if (monto <= 0) {
            throw new RuntimeException("El monto debe ser positivo.");
        }

        Cuenta cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            throw new RuntimeException("La cuenta " + numeroCuenta + " no existe.");
        }

        // Lógica de BD
        int idCuenta = transaccionDAO.obtenerIdCuenta(numeroCuenta);
        int idEmpleadoBD = -1; 
        
        // Manejo de empleado nulo (Cajero Automático / Admin)
        if (codigoEmpleado != null) {
            idEmpleadoBD = transaccionDAO.obtenerIdEmpleado(codigoEmpleado);
        }

        // Actualizar Saldo
        double nuevoSaldo = cuenta.getSaldo() + monto;
        if (!cuentaDAO.actualizarSaldo(numeroCuenta, nuevoSaldo)) {
            throw new RuntimeException("Error crítico: No se pudo actualizar el saldo en BD.");
        }
        
        // Registrar Historial
        transaccionDAO.registrarTransaccion(monto, "DEPOSITO", idCuenta, idEmpleadoBD);
    }

    public void realizarRetiro(String numeroCuenta, double monto, String codigoEmpleado) {
        if (!Validador.validarNumeroCuenta(numeroCuenta)) throw new RuntimeException("Cuenta inválida.");
        if (monto <= 0) throw new RuntimeException("Monto inválido.");

        Cuenta cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) throw new RuntimeException("La cuenta no existe.");

        if (cuenta.getSaldo() < monto) {
            throw new RuntimeException("Saldo insuficiente. Tienes: S/ " + cuenta.getSaldo());
        }

        int idCuenta = transaccionDAO.obtenerIdCuenta(numeroCuenta);
        int idEmpleadoBD = (codigoEmpleado != null) ? transaccionDAO.obtenerIdEmpleado(codigoEmpleado) : -1;

        double nuevoSaldo = cuenta.getSaldo() - monto;
        if (!cuentaDAO.actualizarSaldo(numeroCuenta, nuevoSaldo)) {
            throw new RuntimeException("Error al actualizar saldo.");
        }
        
        transaccionDAO.registrarTransaccion(monto, "RETIRO", idCuenta, idEmpleadoBD);
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

        // Verificar que la cuenta existe
        Cuenta cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println(" Cuenta no encontrada: " + numeroCuenta);
            return;
        }

        // Si es cliente, verificar que la cuenta le pertenece
        if (usuarioActual.getTipo().equals("CLIENTE")) {
            List<Cuenta> cuentasCliente = titularidadDAO.obtenerCuentasPorCliente(
                ((UsuarioCliente) usuarioActual).getCliente().getIdCliente()
            );
            
            boolean tieneAcceso = false;
            for (Cuenta c : cuentasCliente) {
                if (c.getNumeroCuenta().equals(numeroCuenta)) {
                    tieneAcceso = true;
                    break;
                }
            }
            
            if (!tieneAcceso) {
                System.out.println(" No tienes permiso para ver los movimientos de esta cuenta.");
                return;
            }
        }

        // Obtener transacciones desde BD
        List<Transaccion> transacciones = transaccionDAO.obtenerTransaccionesPorCuenta(numeroCuenta);
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
        
        // Listar transacciones desde BD
        transaccionDAO.listarTodas();
    }

    // ========== ASIGNACIONES (COMPLETAMENTE ACTUALIZADO) ==========
    public void asignarCuentaACliente(String idCliente, String numeroCuenta) {
        if (!tienePermiso("ASIGNAR_CUENTA")) {
            mostrarErrorPermiso("Asignar cuentas a clientes");
            return;
        }

        // Asignar cuenta usando TitularidadDAO
        boolean resultado = titularidadDAO.asignarCuentaACliente(idCliente, numeroCuenta);
        if (resultado) {
            // Buscar cliente y cuenta para mostrar mensaje detallado
            Cliente cliente = clienteDAO.buscarPorCodigo(idCliente);
            Cuenta cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
            if (cliente != null && cuenta != null) {
                System.out.println("✓ Cuenta " + numeroCuenta + " asignada exitosamente a " + 
                                   cliente.getNombre() + " " + cliente.getApellido());
            }
        } else {
            System.out.println(" No se pudo asignar la cuenta al cliente");
        }
    }

    public void mostrarCuentasDeCliente(String idCliente) {
        if (!tienePermiso("VER_CUENTAS_CLIENTE")) {
            mostrarErrorPermiso("Ver cuentas de cliente");
            return;
        }

        // Buscar cliente en BD
        Cliente cliente = clienteDAO.buscarPorCodigo(idCliente);
        if (cliente == null) {
            System.out.println(" Cliente no encontrado: " + idCliente);
            return;
        }

        // Obtener cuentas desde BD
        List<Cuenta> cuentas = titularidadDAO.obtenerCuentasPorCliente(idCliente);
        
        if (cuentas.isEmpty()) {
            System.out.println("ℹ El cliente " + cliente.getNombre() + " " + cliente.getApellido() + 
                             " no tiene cuentas asociadas");
        } else {
            System.out.println("\n=== CUENTAS DE " + cliente.getNombre() + " " + cliente.getApellido() + " ===");
            for (Cuenta cuenta : cuentas) {
                System.out.println("• Número: " + cuenta.getNumeroCuenta() + 
                                 " | Tipo: " + cuenta.getTipoCuenta() + 
                                 " | Saldo: S/ " + String.format("%.2f", cuenta.getSaldo()));
            }
            System.out.println("=============================================\n");
        }
    }

    // ========== GETTERS para compatibilidad ==========
    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public EmpleadoDAO getEmpleadoDAO() {
        return empleadoDAO;
    }

    public CuentaDAO getCuentaDAO() {
        return cuentaDAO;
    }
    public TransaccionDAO getTransaccionDAO() {
        return transaccionDAO;
    }
    
    public TitularidadDAO getTitularidadDAO() {
        return titularidadDAO;
    }
    
    // Métodos antiguos para compatibilidad (se usarán temporalmente)
    public Object getClienteRepo() {
        System.out.println("⚠ Usando getClienteRepo() obsoleto - usar getClienteDAO()");
        return clienteDAO;
    }
    
    public Object getEmpleadoRepo() {
        System.out.println("⚠ Usando getEmpleadoRepo() obsoleto - usar getEmpleadoDAO()");
        return empleadoDAO;
    }
    
    public Object getCuentaRepo() {
        System.out.println("⚠ Usando getCuentaRepo() obsoleto - usar getCuentaDAO()");
        return cuentaDAO;
    }
}