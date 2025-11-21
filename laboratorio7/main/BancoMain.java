package DSOO_LABS.laboratorio7.main;

import DSOO_LABS.laboratorio7.service.BancoService;
import java.util.Scanner;


public class BancoMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BancoService bancoService = new BancoService();
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    bancoService.listarClientes();
                    break;
                case 2:
                    System.out.print("Ingrese ID: ");
                    String id = sc.nextLine();
                    System.out.print("Ingrese DNI: ");
                    String dni = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese apellido: ");
                    String apellido = sc.nextLine();
                    System.out.print("Ingrese dirección: ");
                    String direccion = sc.nextLine();
                    System.out.print("Ingrese teléfono: ");
                    String telefono = sc.nextLine();
                    System.out.print("Ingrese correo: ");
                    String correo = sc.nextLine();
                    bancoService.agregarCliente(id, dni, nombre, apellido, direccion, telefono, correo);
                    break;
                case 3:
                    System.out.print("Ingrese ID del cliente: ");
                    String idBuscado = sc.nextLine();
                    bancoService.buscarClientePorId(idBuscado);
                    break;
                case 4:
                    System.out.print("Ingrese ID del cliente a eliminar: ");
                    String idEliminar = sc.nextLine();
                    bancoService.eliminarCliente(idEliminar);
                    break;
                case 5:
                    bancoService.listarEmpleados();
                    break;
                case 6:
                    System.out.print("Ingrese ID: ");
                    String eId = sc.nextLine();
                    System.out.print("Ingrese DNI: ");
                    String eDni = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String eNombre = sc.nextLine();
                    System.out.print("Ingrese apellido: ");
                    String eApellido = sc.nextLine();
                    System.out.print("Ingrese dirección: ");
                    String eDireccion = sc.nextLine();
                    System.out.print("Ingrese teléfono: ");
                    String eTelefono = sc.nextLine();
                    System.out.print("Ingrese cargo: ");
                    String eCargo = sc.nextLine();
                    bancoService.agregarEmpleado(eId, eDni, eNombre, eApellido, eDireccion, eTelefono, eCargo);
                    break;
                case 7:
                    bancoService.listarCuentas();
                    break;
                case 8:
                    System.out.print("Ingrese número de cuenta: ");
                    String numero = sc.nextLine();
                    System.out.print("Ingrese tipo de cuenta: ");
                    String tipo = sc.nextLine();
                    System.out.print("Ingrese saldo inicial: ");
                    double saldo = sc.nextDouble();
                    sc.nextLine();
                    bancoService.agregarCuenta(numero, tipo, saldo);
                    break;
                case 9:
                    System.out.print("Ingrese número de cuenta a eliminar: ");
                    String numEliminar = sc.nextLine();
                    bancoService.eliminarCuenta(numEliminar);
                    break;
                case 10:
                    System.out.print("Ingrese número de cuenta: ");
                    String nroSaldo = sc.nextLine();
                    bancoService.consultarSaldo(nroSaldo);
                    break;
                case 11:
                    System.out.print("Ingrese número de cuenta: ");
                    String nroDep = sc.nextLine();
                    System.out.print("Ingrese monto: ");
                    double montoDep = sc.nextDouble();
                    sc.nextLine();
                    bancoService.realizarDeposito(nroDep, montoDep, "E001");
                    break;
                case 12:
                    System.out.print("Ingrese número de cuenta: ");
                    String nroRet = sc.nextLine();
                    System.out.print("Ingrese monto: ");
                    double montoRet = sc.nextDouble();
                    sc.nextLine();
                    bancoService.realizarRetiro(nroRet, montoRet, "E001");
                    break;
                case 13:
                    System.out.print("Ingrese número de cuenta: ");
                    String nroMov = sc.nextLine();
                    bancoService.verMovimientos(nroMov);
                    break;
                case 14:
                    bancoService.listarTransacciones();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n========= SISTEMA BANCARIO =========");
        System.out.println("1. Listar clientes");
        System.out.println("2. Agregar cliente");
        System.out.println("3. Buscar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Listar empleados");
        System.out.println("6. Agregar empleado");
        System.out.println("7. Listar cuentas");
        System.out.println("8. Agregar cuenta");
        System.out.println("9. Eliminar cuenta");
        System.out.println("10. Consultar saldo");
        System.out.println("11. Realizar depósito");
        System.out.println("12. Realizar retiro");
        System.out.println("13. Ver movimientos");
        System.out.println("14. Listar transacciones");
        System.out.println("0. Salir");
        System.out.println("===================================");
    }
}
