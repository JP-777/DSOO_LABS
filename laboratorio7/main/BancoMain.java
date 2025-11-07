package DSOO_LABS.laboratorio7.main;

import DSOO_LABS.laboratorio7.service.BancoService;
import java.util.Scanner;

/**
 * Clase principal del sistema bancario.
 * Muestra un menú interactivo por consola y usa los métodos del BancoService.
 */
public class BancoMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BancoService bancoService = new BancoService();

        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    bancoService.listarClientes();
                    break;

                case 2:
                    System.out.print("Ingrese el ID del cliente: ");
                    String idCliente = sc.nextLine();
                    bancoService.consultarCuentasDeCliente(idCliente);
                    break;

                case 3:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroCuentaSaldo = sc.nextLine();
                    bancoService.consultarSaldo(nroCuentaSaldo);
                    break;

                case 4:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroDep = sc.nextLine();
                    System.out.print("Ingrese el monto a depositar: ");
                    double montoDep = sc.nextDouble();
                    sc.nextLine();
                    bancoService.realizarDeposito(nroDep, montoDep, "E001");
                    break;

                case 5:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroRet = sc.nextLine();
                    System.out.print("Ingrese el monto a retirar: ");
                    double montoRet = sc.nextDouble();
                    sc.nextLine();
                    bancoService.realizarRetiro(nroRet, montoRet, "E001");
                    break;

                case 6:
                    System.out.print("Ingrese el número de cuenta: ");
                    String nroMov = sc.nextLine();
                    bancoService.verMovimientos(nroMov);
                    break;

                case 7:
                    bancoService.listarTransacciones();
                    break;

                case 8:
                    System.out.println("Gracias por usar el sistema bancario. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 8);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=======================================");
        System.out.println("   SISTEMA BANCARIO ORIENTADO A OBJETOS");
        System.out.println("=======================================");
        System.out.println("1. Listar clientes");
        System.out.println("2. Consultar cuentas de un cliente");
        System.out.println("3. Consultar saldo de una cuenta");
        System.out.println("4. Realizar depósito");
        System.out.println("5. Realizar retiro");
        System.out.println("6. Ver movimientos de una cuenta");
        System.out.println("7. Listar transacciones");
        System.out.println("8. Salir");
        System.out.println("=======================================");
    }
}
