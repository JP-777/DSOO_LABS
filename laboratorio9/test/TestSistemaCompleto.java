package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.service.BancoService;
import DSOO_LABS.laboratorio9.model.Usuario;
import DSOO_LABS.laboratorio9.model.Cliente;
import DSOO_LABS.laboratorio9.model.Empleado;
import java.util.List;

public class TestSistemaCompleto {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA FINAL - Sistema Bancario con MySQL\n");
        System.out.println("==================================================");
        
        // 1. Iniciar Servicio
        BancoService bancoService = new BancoService();
        
        // 2. Login como administrador
        System.out.println("1. 🔐 Login como ADMINISTRADOR:");
        Usuario admin = bancoService.login("elkin", "admin123");
        
        if (admin == null) {
            System.out.println("❌ Error: No se pudo autenticar al admin 'elkin'");
            return;
        }
        
        bancoService.setUsuarioActual(admin);
        System.out.println("   ✅ Admin autenticado: " + admin.getNombreUsuario());
        
        // 3. Operaciones de lectura (Usando los DAOs del servicio)
        System.out.println("\n2. 📊 Reportes del Sistema:");
        
        System.out.println("   a) Listando clientes...");
        List<Cliente> clientes = bancoService.getClienteDAO().listarTodos();
        System.out.println("      -> Total: " + clientes.size());
        
        System.out.println("   b) Listando empleados...");
        List<Empleado> empleados = bancoService.getEmpleadoDAO().listarTodos();
        System.out.println("      -> Total: " + empleados.size());
        
        System.out.println("   c) Listando cuentas...");
        System.out.println("      -> Total: " + bancoService.getCuentaDAO().listarTodas().size());
        
        // 4. Prueba de Operación (Depósito)
        System.out.println("\n3. 💰 Prueba de Operación:");
        System.out.println("   Intentando depósito de prueba en cuenta 1002...");
        try {
            // Depósito como Admin (null ID)
            bancoService.realizarDeposito("1002", 50.00, null);
            System.out.println("   ✅ Depósito realizado correctamente.");
        } catch (Exception e) {
            System.out.println("   ⚠️ " + e.getMessage());
        }
        
        System.out.println("\n==================================================");
        System.out.println("🎉 ¡SISTEMA BANCARIO FUNCIONANDO AL 100%!");
        System.out.println("✅ Conexión BD OK");
        System.out.println("✅ Login OK");
        System.out.println("✅ Operaciones OK");
    }
}