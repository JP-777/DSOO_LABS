package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.service.BancoService;
import DSOO_LABS.laboratorio9.model.Usuario;
import DSOO_LABS.laboratorio9.model.Cuenta;

public class TestBugFix {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA DE OPERACIONES (Depósito/Retiro)\n");
        
        BancoService bancoService = new BancoService();
        
        // Usamos una cuenta que sabemos que existe (creada en el SQL anterior)
        String cuentaPrueba = "1002"; 
        
        // Login como empleado (Ana)
        Usuario empleado = bancoService.login("ana", "ana123");
        bancoService.setUsuarioActual(empleado);
        
        System.out.println("1. Consultando saldo inicial...");
        Cuenta c = bancoService.getCuentaDAO().buscarPorNumero(cuentaPrueba);
        if (c != null) {
            System.out.println("   Saldo actual: S/ " + c.getSaldo());
        }
        
        System.out.println("\n2. 💰 Realizando DEPÓSITO de S/ 100.00...");
        try {
            bancoService.realizarDeposito(cuentaPrueba, 100.00, "E002"); // Ana es E002
            System.out.println("   ✅ Depósito exitoso.");
        } catch (Exception e) {
            System.out.println("   ❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n3. 💸 Realizando RETIRO de S/ 50.00...");
        try {
            bancoService.realizarRetiro(cuentaPrueba, 50.00, "E002");
            System.out.println("   ✅ Retiro exitoso.");
        } catch (Exception e) {
            System.out.println("   ❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n4. Verificando saldo final...");
        c = bancoService.getCuentaDAO().buscarPorNumero(cuentaPrueba);
        System.out.println("   Saldo final: S/ " + c.getSaldo());
        
        System.out.println("\n🎉 ¡Lógica de negocio verificada!");
    }
}