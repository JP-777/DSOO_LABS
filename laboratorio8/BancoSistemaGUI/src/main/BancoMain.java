package DSOO_LABS.laboratorio7.main;

import service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.service.InterfazUsuario;
import java.util.Scanner;

public class BancoMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BancoService bancoService = new BancoService();
        GestorClinica gestorClinica = new GestorClinica(
            bancoService.getClienteRepo(), 
            bancoService.getEmpleadoRepo()
        );
        InterfazUsuario interfaz = new InterfazUsuario(sc, bancoService, gestorClinica);
        
        interfaz.mostrarBienvenida();
        
        if (interfaz.realizarLogin()) {
            int opcion;
            do {
                interfaz.mostrarMenu();
                System.out.print("Seleccione una opción: ");
                
                try {
                    opcion = sc.nextInt();
                    sc.nextLine();
                    
                    switch (opcion) {
                        case 1:
                            bancoService.listarClientes();
                            interfaz.pausar();
                            break;
                        case 2:
                            interfaz.agregarCliente();
                            interfaz.pausar();
                            break;
                        case 3:
                            interfaz.buscarCliente();
                            interfaz.pausar();
                            break;
                        case 4:
                            interfaz.eliminarCliente();
                            interfaz.pausar();
                            break;
                        case 5:
                            bancoService.listarEmpleados();
                            interfaz.pausar();
                            break;
                        case 6:
                            interfaz.agregarEmpleado();
                            interfaz.pausar();
                            break;
                        case 7:
                            bancoService.listarCuentas();
                            interfaz.pausar();
                            break;
                        case 8:
                            interfaz.agregarCuenta();
                            interfaz.pausar();
                            break;
                        case 9:
                            interfaz.eliminarCuenta();
                            interfaz.pausar();
                            break;
                        case 10:
                            interfaz.consultarSaldo();
                            interfaz.pausar();
                            break;
                        case 11:
                            interfaz.realizarDeposito();
                            interfaz.pausar();
                            break;
                        case 12:
                            interfaz.realizarRetiro();
                            interfaz.pausar();
                            break;
                        case 13:
                            interfaz.verMovimientos();
                            interfaz.pausar();
                            break;
                        case 14:
                            bancoService.listarTransacciones();
                            interfaz.pausar();
                            break;
                        case 0:
                            System.out.println("\n ⟳ Cerrando sesión...");
                            break;
                        default:
                            System.out.println(" X Opción no válida. Intente nuevamente.");
                    }
                    
                } catch (Exception e) {
                    System.out.println(" X Error: Ingrese un número válido");
                    sc.nextLine();
                    opcion = -1;
                }

            } while (opcion != 0);
            
            System.out.println("\n ✓ Sesión cerrada. ¡Hasta pronto!");
        } else {
            System.out.println("\n X No se pudo iniciar sesión. Cerrando sistema...");
        }

        sc.close();
    }
}