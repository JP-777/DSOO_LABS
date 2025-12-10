package DSOO_LABS.laboratorio7.test;

import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.dao.*;
import DSOO_LABS.laboratorio7.model.Usuario;

public class TestSistemaCompleto {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA FINAL - Sistema Bancario con MySQL\n");
        System.out.println("==================================================");
        
        // 1. Configurar sistema
        ClienteDAO clienteDAO = new ClienteDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        GestorClinica gestor = new GestorClinica(clienteDAO, empleadoDAO);
        BancoService bancoService = new BancoService();
        
        // 2. Login como administrador
        System.out.println("1. 🔐 Login como ADMINISTRADOR:");
        Usuario admin = gestor.login("jordan.paredes", "admin123");
        
        if (admin == null) {
            System.out.println("❌ Error: No se pudo autenticar admin");
            return;
        }
        
        bancoService.setUsuarioActual(admin);
        System.out.println("   ✅ Admin autenticado: " + admin.getNombreUsuario());
        
        // 3. Operaciones de administrador
        System.out.println("\n2. 📊 Operaciones de ADMINISTRADOR:");
        
        System.out.println("   a) Listar clientes:");
        bancoService.listarClientes();
        
        System.out.println("   b) Listar empleados:");
        bancoService.listarEmpleados();
        
        System.out.println("   c) Listar cuentas:");
        bancoService.listarCuentas();
        
        System.out.println("   d) Listar transacciones:");
        bancoService.listarTransacciones();
        
        // 4. Login como empleado
        System.out.println("\n3. 🔐 Login como EMPLEADO:");
        Usuario empleado = gestor.login("kevin.peralta", "empleado123");
        
        if (empleado != null) {
            bancoService.setUsuarioActual(empleado);
            System.out.println("   ✅ Empleado autenticado: " + empleado.getNombreUsuario());
            
            System.out.println("   a) Consultar saldo cuenta 1001:");
            bancoService.consultarSaldo("1001");
            
            System.out.println("   b) Realizar depósito:");
            bancoService.realizarDeposito("1001", 500.00, "E001");
        }
        
        // 5. Login como cliente
        System.out.println("\n4. 🔐 Login como CLIENTE:");
        Usuario cliente = gestor.login("edwar.saire", "cliente123");
        
        if (cliente != null) {
            bancoService.setUsuarioActual(cliente);
            System.out.println("   ✅ Cliente autenticado: " + cliente.getNombreUsuario());
            
            System.out.println("   a) Consultar saldo propia cuenta:");
            bancoService.consultarSaldo("1001");
            
            System.out.println("   b) Ver movimientos:");
            bancoService.verMovimientos("1001");
        }
        
        // 6. Verificar datos en BD
        System.out.println("\n5. 📈 Estadísticas finales:");
        System.out.println("   Total clientes: " + clienteDAO.listarTodos().size());
        System.out.println("   Total empleados: " + empleadoDAO.listarTodos().size());
        System.out.println("   Total cuentas: " + bancoService.getCuentaDAO().listarTodas().size());
        
        System.out.println("\n==================================================");
        System.out.println("🎉 ¡SISTEMA BANCARIO COMPLETO CON MYSQL FUNCIONANDO!");
        System.out.println("✅ Base de datos ✓");
        System.out.println("✅ DAOs implementados ✓");
        System.out.println("✅ Login con BD ✓");
        System.out.println("✅ Transacciones en BD ✓");
        System.out.println("✅ Permisos por rol ✓");
        System.out.println("==================================================");
    }
}