package DSOO_LABS.laboratorio7.main;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import java.util.Scanner;

public class BancoMain {
    private static Scanner sc = new Scanner(System.in);
    private static BancoService bancoService = new BancoService();
    private static GestorClinica gestorClinica;
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        // Inicializar gestor de usuarios
        gestorClinica = new GestorClinica(
            bancoService.getClienteRepo(), 
            bancoService.getEmpleadoRepo()
        );

        mostrarBienvenida();
        
        // Sistema de login
        if (realizarLogin()) {
            bancoService.setUsuarioActual(usuarioActual);
            mostrarMenuPrincipal();
        } else {
            System.out.println("\n❌ No se pudo iniciar sesión. Cerrando sistema...");
        }

        sc.close();
    }

    private static void mostrarBienvenida() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                                                       ║");
        System.out.println("║          SISTEMA BANCARIO - BANCO UNSA               ║");
        System.out.println("║                                                       ║");
        System.out.println("║          Gestión Integral de Operaciones             ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
    }

    private static boolean realizarLogin() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("                    INICIO DE SESIÓN                   ");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        int intentos = 0;
        int maxIntentos = 3;

        while (intentos < maxIntentos) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine().trim();
            
            System.out.print("Contraseña: ");
            String contrasena = sc.nextLine().trim();

            usuarioActual = gestorClinica.login(usuario, contrasena);

            if (usuarioActual != null) {
                System.out.println("\n✓ Inicio de sesión exitoso");
                System.out.println("Bienvenido: " + usuarioActual);
                usuarioActual.mostrarPermisos();
                return true;
            } else {
                intentos++;
                System.out.println("\n❌ Credenciales incorrectas.");
                System.out.println("Intentos restantes: " + (maxIntentos - intentos));
                
                if (intentos < maxIntentos) {
                    System.out.print("\n¿Desea ver los usuarios predefinidos? (s/n): ");
                    String verUsuarios = sc.nextLine().trim().toLowerCase();
                    if (verUsuarios.equals("s")) {
                        gestorClinica.mostrarUsuariosPredefinidos();
                    }
                    System.out.println();
                }
            }
        }

        return false;
    }

    private static void mostrarMenuPrincipal() {
        int opcion;

        do {
            mostrarMenuSegunTipo();
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
                
                procesarOpcion(opcion);
                
            } catch (Exception e) {
                System.out.println("❌ Error: Ingrese un número válido");
                sc.nextLine(); // Limpiar buffer
                opcion = -1;
            }

        } while (opcion != 0);

        System.out.println("\n✓ Sesión cerrada. ¡Hasta pronto!");
    }

    private static void mostrarMenuSegunTipo() {
        String tipo = usuarioActual.getTipo();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║              SISTEMA BANCARIO - MENÚ PRINCIPAL        ║");
        System.out.println("║  Usuario: " + String.format("%-43s", usuarioActual.getNombreUsuario()) + "║");
        System.out.println("║  Rol: " + String.format("%-47s", tipo) + "║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");

        if (tipo.equals("ADMINISTRADOR")) {
            mostrarMenuAdministrador();
        } else if (tipo.equals("EMPLEADO")) {
            mostrarMenuEmpleado();
        } else if (tipo.equals("CLIENTE")) {
            mostrarMenuCliente();
        }
    }

    private static void mostrarMenuAdministrador() {
        System.out.println("\n┌───────────────────────────────────────────────────────┐");
        System.out.println("│              MENÚ ADMINISTRADOR                       │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  GESTIÓN DE CLIENTES                                  │");
        System.out.println("│   1. Listar clientes                                  │");
        System.out.println("│   2. Agregar cliente                                  │");
        System.out.println("│   3. Buscar cliente                                   │");
        System.out.println("│   4. Eliminar cliente                                 │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  GESTIÓN DE EMPLEADOS                                 │");
        System.out.println("│   5. Listar empleados                                 │");
        System.out.println("│   6. Agregar empleado                                 │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  GESTIÓN DE CUENTAS                                   │");
        System.out.println("│   7. Listar cuentas                                   │");
        System.out.println("│   8. Agregar cuenta                                   │");
        System.out.println("│   9. Eliminar cuenta                                  │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  OPERACIONES BANCARIAS                                │");
        System.out.println("│  10. Consultar saldo                                  │");
        System.out.println("│  11. Realizar depósito                                │");
        System.out.println("│  12. Realizar retiro                                  │");
        System.out.println("│  13. Ver movimientos                                  │");
        System.out.println("│  14. Listar transacciones                             │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│   0. Cerrar sesión                                    │");
        System.out.println("└───────────────────────────────────────────────────────┘");
    }

    private static void mostrarMenuEmpleado() {
        System.out.println("\n┌───────────────────────────────────────────────────────┐");
        System.out.println("│              MENÚ EMPLEADO                            │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  CONSULTAS                                            │");
        System.out.println("│   1. Listar clientes                                  │");
        System.out.println("│   3. Buscar cliente                                   │");
        System.out.println("│   5. Listar empleados                                 │");
        System.out.println("│   7. Listar cuentas                                   │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  GESTIÓN DE CUENTAS                                   │");
        System.out.println("│   8. Agregar cuenta                                   │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  OPERACIONES BANCARIAS                                │");
        System.out.println("│  10. Consultar saldo                                  │");
        System.out.println("│  11. Realizar depósito                                │");
        System.out.println("│  12. Realizar retiro                                  │");
        System.out.println("│  13. Ver movimientos                                  │");
        System.out.println("│  14. Listar transacciones                             │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│   0. Cerrar sesión                                    │");
        System.out.println("└───────────────────────────────────────────────────────┘");
    }

    private static void mostrarMenuCliente() {
        System.out.println("\n┌───────────────────────────────────────────────────────┐");
        System.out.println("│              MENÚ CLIENTE                             │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│  OPERACIONES BANCARIAS                                │");
        System.out.println("│  10. Consultar saldo                                  │");
        System.out.println("│  11. Realizar depósito                                │");
        System.out.println("│  12. Realizar retiro                                  │");
        System.out.println("│  13. Ver movimientos de cuenta                        │");
        System.out.println("├───────────────────────────────────────────────────────┤");
        System.out.println("│   0. Cerrar sesión                                    │");
        System.out.println("└───────────────────────────────────────────────────────┘");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                bancoService.listarClientes();
                pausar();
                break;
            case 2:
                agregarCliente();
                pausar();
                break;
            case 3:
                buscarCliente();
                pausar();
                break;
            case 4:
                eliminarCliente();
                pausar();
                break;
            case 5:
                bancoService.listarEmpleados();
                pausar();
                break;
            case 6:
                agregarEmpleado();
                pausar();
                break;
            case 7:
                bancoService.listarCuentas();
                pausar();
                break;
            case 8:
                agregarCuenta();
                pausar();
                break;
            case 9:
                eliminarCuenta();
                pausar();
                break;
            case 10:
                consultarSaldo();
                pausar();
                break;
            case 11:
                realizarDeposito();
                pausar();
                break;
            case 12:
                realizarRetiro();
                pausar();
                break;
            case 13:
                verMovimientos();
                pausar();
                break;
            case 14:
                bancoService.listarTransacciones();
                pausar();
                break;
            case 0:
                System.out.println("\n⟳ Cerrando sesión...");
                break;
            default:
                System.out.println("❌ Opción no válida. Intente nuevamente.");
        }
    }

    // MÉTODOS AUXILIARES PARA CADA OPERACIÓN

    private static void agregarCliente() {
        System.out.println("\n=== AGREGAR NUEVO CLIENTE ===");
        System.out.print("Ingrese ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Ingrese DNI (8 dígitos): ");
        String dni = sc.nextLine().trim();
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Ingrese apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.print("Ingrese dirección: ");
        String direccion = sc.nextLine().trim();
        System.out.print("Ingrese teléfono (9 dígitos): ");
        String telefono = sc.nextLine().trim();
        System.out.print("Ingrese correo electrónico: ");
        String correo = sc.nextLine().trim();
        
        bancoService.agregarCliente(id, dni, nombre, apellido, direccion, telefono, correo);
    }

    private static void buscarCliente() {
        System.out.println("\n=== BUSCAR CLIENTE ===");
        System.out.print("Ingrese ID del cliente: ");
        String id = sc.nextLine().trim();
        bancoService.buscarClientePorId(id);
    }

    private static void eliminarCliente() {
        System.out.println("\n=== ELIMINAR CLIENTE ===");
        System.out.print("Ingrese ID del cliente a eliminar: ");
        String id = sc.nextLine().trim();
        System.out.print("¿Está seguro? (s/n): ");
        String confirmacion = sc.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            bancoService.eliminarCliente(id);
        } else {
            System.out.println("ℹ Operación cancelada.");
        }
    }

    private static void agregarEmpleado() {
        System.out.println("\n=== AGREGAR NUEVO EMPLEADO ===");
        System.out.print("Ingrese ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Ingrese DNI (8 dígitos): ");
        String dni = sc.nextLine().trim();
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Ingrese apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.print("Ingrese dirección: ");
        String direccion = sc.nextLine().trim();
        System.out.print("Ingrese teléfono (9 dígitos): ");
        String telefono = sc.nextLine().trim();
        System.out.print("Ingrese cargo: ");
        String cargo = sc.nextLine().trim();
        
        bancoService.agregarEmpleado(id, dni, nombre, apellido, direccion, telefono, cargo);
    }

    private static void agregarCuenta() {
        System.out.println("\n=== AGREGAR NUEVA CUENTA ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Ingrese tipo de cuenta (Ahorros/Corriente): ");
        String tipo = sc.nextLine().trim();
        System.out.print("Ingrese saldo inicial: ");
        try {
            double saldo = sc.nextDouble();
            sc.nextLine();
            bancoService.agregarCuenta(numero, tipo, saldo);
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido");
            sc.nextLine();
        }
    }

    private static void eliminarCuenta() {
        System.out.println("\n=== ELIMINAR CUENTA ===");
        System.out.print("Ingrese número de cuenta a eliminar: ");
        String numero = sc.nextLine().trim();
        System.out.print("¿Está seguro? (s/n): ");
        String confirmacion = sc.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            bancoService.eliminarCuenta(numero);
        } else {
            System.out.println("ℹ Operación cancelada.");
        }
    }

    private static void consultarSaldo() {
        System.out.println("\n=== CONSULTAR SALDO ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        bancoService.consultarSaldo(numero);
    }

    private static void realizarDeposito() {
        System.out.println("\n=== REALIZAR DEPÓSITO ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Ingrese monto a depositar: ");
        try {
            double monto = sc.nextDouble();
            sc.nextLine();
            bancoService.realizarDeposito(numero, monto, "E001");
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido");
            sc.nextLine();
        }
    }

    private static void realizarRetiro() {
        System.out.println("\n=== REALIZAR RETIRO ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Ingrese monto a retirar: ");
        try {
            double monto = sc.nextDouble();
            sc.nextLine();
            bancoService.realizarRetiro(numero, monto, "E001");
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido");
            sc.nextLine();
        }
    }

    private static void verMovimientos() {
        System.out.println("\n=== VER MOVIMIENTOS ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        bancoService.verMovimientos(numero);
    }

    private static void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        sc.nextLine();
    }
}