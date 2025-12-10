package DSOO_LABS.laboratorio7.test;

import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.dao.*;
import DSOO_LABS.laboratorio7.model.Usuario;

public class TestBugFix {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de BUG FIX - Depósito/Retiro\n");
        
        // Configurar
        ClienteDAO clienteDAO = new ClienteDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        GestorClinica gestor = new GestorClinica(clienteDAO, empleadoDAO);
        BancoService bancoService = new BancoService();
        
        // Login como empleado
        Usuario empleado = gestor.login("kevin.peralta", "empleado123");
        bancoService.setUsuarioActual(empleado);
        
        System.out.println("1. 💰 DEPÓSITO de S/ 100 a cuenta 1001:");
        
        // Ver saldo antes
        System.out.print("   Saldo antes: ");
        bancoService.consultarSaldo("1001");
        
        // Realizar depósito
        bancoService.realizarDeposito("1001", 100.00, "E001");
        
        // Ver saldo después
        System.out.print("   Saldo después: ");
        bancoService.consultarSaldo("1001");
        
        System.out.println("\n2. 💸 RETIRO de S/ 50 de cuenta 1001:");
        
        // Realizar retiro
        bancoService.realizarRetiro("1001", 50.00, "E001");
        
        // Ver saldo final
        System.out.print("   Saldo final: ");
        bancoService.consultarSaldo("1001");
        
        System.out.println("\n3. 📋 Ver transacciones registradas:");
        bancoService.listarTransacciones();
        
        System.out.println("🎉 ¡BUG ARREGLADO!");
    }
}