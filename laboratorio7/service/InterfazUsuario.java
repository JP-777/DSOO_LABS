package DSOO_LABS.laboratorio7.service;

import DSOO_LABS.laboratorio7.model.Usuario;
import java.util.Scanner;

public class InterfazUsuario {
    private Scanner sc;
    private BancoService bancoService;
    private GestorClinica gestorClinica;
    private Usuario usuarioActual;

    public InterfazUsuario(Scanner sc, BancoService bancoService, GestorClinica gestorClinica) {
        this.sc = sc;
        this.bancoService = bancoService;
        this.gestorClinica = gestorClinica;
    }

    public void mostrarBienvenida() {
        System.out.println("\n");
        System.out.println("████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
        System.out.println("█                                                                              █");
        System.out.println("█   ███████╗██╗███████╗████████╗███████╗███╗   ███╗ █████╗                    █");
        System.out.println("█   ██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗ ████║██╔══██╗                   █");
        System.out.println("█   ███████╗██║███████╗   ██║   █████╗  ██╔████╔██║███████║                   █");
        System.out.println("█   ╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║██╔══██║                   █");
        System.out.println("█   ███████║██║███████║   ██║   ███████╗██║ ╚═╝ ██║██║  ██║                   █");
        System.out.println("█   ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝                   █");
        System.out.println("█                                                                              █");
        System.out.println("█   ██████╗  █████╗ ███╗   ██╗ ██████╗ █████╗ ██████╗ ██╗ ██████╗            █");
        System.out.println("█   ██╔══██╗██╔══██╗████╗  ██║██╔════╝██╔══██╗██╔══██╗██║██╔═══██╗           █");
        System.out.println("█   ██████╔╝███████║██╔██╗ ██║██║     ███████║██████╔╝██║██║   ██║           █");
        System.out.println("█   ██╔══██╗██╔══██║██║╚██╗██║██║     ██╔══██║██╔══██╗██║██║   ██║           █");
        System.out.println("█   ██████╔╝██║  ██║██║ ╚████║╚██████╗██║  ██║██║  ██║██║╚██████╔╝           █");
        System.out.println("█   ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝ ╚═════╝            █");
        System.out.println("█                                                                              █");
        System.out.println("█                          ■ BANCO UNSA ■                                     █");
        System.out.println("█                  Gestión Integral de Operaciones                            █");
        System.out.println("█                                                                              █");
        System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        System.out.println("████████████████████████████████████████████████████████████████████████████████");
        System.out.println("\n");
    }

    public boolean realizarLogin() {
        System.out.println("████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
        System.out.println("█                         INICIO DE SESIÓN                                     █");
        System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        System.out.println("████████████████████████████████████████████████████████████████████████████████\n");
        
        int intentos = 0;
        int maxIntentos = 3;

        while (intentos < maxIntentos) {
            System.out.print("■ Usuario: ");
            String usuario = sc.nextLine().trim();
            
            System.out.print("■ Contraseña: ");
            String contrasena = sc.nextLine().trim();

            usuarioActual = gestorClinica.login(usuario, contrasena);

            if (usuarioActual != null) {
                bancoService.setUsuarioActual(usuarioActual);
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

    public void mostrarMenu() {
        String tipo = usuarioActual.getTipo();
        
        System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
        System.out.println("█              SISTEMA BANCARIO - MENÚ PRINCIPAL                               █");
        System.out.println("█  ■ Usuario: " + String.format("%-63s", usuarioActual.getNombreUsuario()) + "█");
        System.out.println("█  ■ Rol: " + String.format("%-67s", tipo) + "█");
        System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        System.out.println("████████████████████████████████████████████████████████████████████████████████");

        if (tipo.equals("ADMINISTRADOR")) {
            mostrarMenuAdministrador();
        } else if (tipo.equals("EMPLEADO")) {
            mostrarMenuEmpleado();
        } else if (tipo.equals("CLIENTE")) {
            mostrarMenuCliente();
        }
    }

    private void mostrarMenuAdministrador() {
        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("                        ■ MENÚ ADMINISTRADOR ■                                  ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ GESTIÓN DE CLIENTES                                                       ");
        System.out.println("   1. Listar clientes                                                           ");
        System.out.println("   2. Agregar cliente                                                           ");
        System.out.println("   3. Buscar cliente                                                            ");
        System.out.println("   4. Eliminar cliente                                                          ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ GESTIÓN DE EMPLEADOS                                                      ");
        System.out.println("   5. Listar empleados                                                          ");
        System.out.println("   6. Agregar empleado                                                          ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ GESTIÓN DE CUENTAS                                                        ");
        System.out.println("   7. Listar cuentas                                                            ");
        System.out.println("   8. Agregar cuenta                                                            ");
        System.out.println("   9. Eliminar cuenta                                                           ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ OPERACIONES BANCARIAS                                                     ");
        System.out.println("  10. Consultar saldo                                                           ");
        System.out.println("  11. Realizar depósito                                                         ");
        System.out.println("  12. Realizar retiro                                                           ");
        System.out.println("  13. Ver movimientos                                                           ");
        System.out.println("  14. Listar transacciones                                                      ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("   0. Cerrar sesión                                                             ");
        System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
    }

    private void mostrarMenuEmpleado() {
        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("                           ■ MENÚ EMPLEADO ■                                    ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ CONSULTAS                                                                 ");
        System.out.println("   1. Listar clientes                                                           ");
        System.out.println("   3. Buscar cliente                                                            ");
        System.out.println("   5. Listar empleados                                                          ");
        System.out.println("   7. Listar cuentas                                                            ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ GESTIÓN DE CUENTAS                                                        ");
        System.out.println("   8. Agregar cuenta                                                            ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ OPERACIONES BANCARIAS                                                     ");
        System.out.println("  10. Consultar saldo                                                           ");
        System.out.println("  11. Realizar depósito                                                         ");
        System.out.println("  12. Realizar retiro                                                           ");
        System.out.println("  13. Ver movimientos                                                           ");
        System.out.println("  14. Listar transacciones                                                      ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("   0. Cerrar sesión                                                             ");
        System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
    }

    private void mostrarMenuCliente() {
        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("                            ■ MENÚ CLIENTE ■                                    ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("  ███ OPERACIONES BANCARIAS                                                     ");
        System.out.println("  10. Consultar saldo                                                           ");
        System.out.println("  11. Realizar depósito                                                         ");
        System.out.println("  12. Realizar retiro                                                           ");
        System.out.println("  13. Ver movimientos de cuenta                                                 ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        System.out.println("   0. Cerrar sesión                                                             ");
        System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
    }

    public void agregarCliente() {
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

    public void buscarCliente() {
        System.out.println("\n=== BUSCAR CLIENTE ===");
        System.out.print("Ingrese ID del cliente: ");
        String id = sc.nextLine().trim();
        bancoService.buscarClientePorId(id);
    }

    public void eliminarCliente() {
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

    public void agregarEmpleado() {
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

    public void agregarCuenta() {
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

    public void eliminarCuenta() {
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

    public void consultarSaldo() {
        System.out.println("\n=== CONSULTAR SALDO ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        bancoService.consultarSaldo(numero);
    }

    public void realizarDeposito() {
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

    public void realizarRetiro() {
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

    public void verMovimientos() {
        System.out.println("\n=== VER MOVIMIENTOS ===");
        System.out.print("Ingrese número de cuenta: ");
        String numero = sc.nextLine().trim();
        bancoService.verMovimientos(numero);
    }

    public void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        sc.nextLine();
    }
}