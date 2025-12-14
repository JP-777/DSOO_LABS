package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.service.BancoService;
import DSOO_LABS.laboratorio9.model.Usuario;

public class TestLoginBD {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de LOGIN con Base de Datos\n");
        
        // Instanciamos el servicio principal
        BancoService bancoService = new BancoService();
        
        // Prueba 1: Login de administrador (Elkin)
        System.out.println("1. 🔐 Login ADMINISTRADOR (elkin):");
        Usuario admin = bancoService.login("elkin", "admin123");
        if (admin != null) {
            System.out.println("   ✅ Login exitoso - Usuario: " + admin.getNombreUsuario() + " | Rol: " + admin.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 2: Login de empleado (Ana)
        System.out.println("\n2. 🔐 Login EMPLEADO (ana):");
        Usuario empleado = bancoService.login("ana", "ana123");
        if (empleado != null) {
            System.out.println("   ✅ Login exitoso - Usuario: " + empleado.getNombreUsuario() + " | Rol: " + empleado.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 3: Login de cliente (Maria)
        System.out.println("\n3. 🔐 Login CLIENTE (maria):");
        Usuario cliente = bancoService.login("maria", "maria123");
        if (cliente != null) {
            System.out.println("   ✅ Login exitoso - Usuario: " + cliente.getNombreUsuario() + " | Rol: " + cliente.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 4: Login con credenciales incorrectas
        System.out.println("\n4. 🔐 Login con CREDENCIALES INCORRECTAS:");
        Usuario incorrecto = bancoService.login("usuario.hacker", "pass.hacker");
        if (incorrecto == null) {
            System.out.println("   ✅ Correcto: El sistema rechazó el acceso.");
        } else {
            System.out.println("   ❌ Error: Debería haber fallado.");
        }
        
        System.out.println("\n🎉 Prueba de LOGIN completada.");
    }
}