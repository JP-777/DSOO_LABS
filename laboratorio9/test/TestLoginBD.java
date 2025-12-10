package DSOO_LABS.laboratorio7.test;

import DSOO_LABS.laboratorio7.dao.*;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.model.Usuario;

public class TestLoginBD {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de LOGIN con Base de Datos\n");
        
        // Crear DAOs necesarios
        ClienteDAO clienteDAO = new ClienteDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        
        // Crear GestorClinica (ahora usa BD)
        GestorClinica gestor = new GestorClinica(clienteDAO, empleadoDAO);
        
        // Prueba 1: Login de administrador (debe funcionar)
        System.out.println("1. 🔐 Login ADMINISTRADOR (jordan.paredes):");
        Usuario admin = gestor.login("jordan.paredes", "admin123");
        if (admin != null) {
            System.out.println("   ✅ Login exitoso - Tipo: " + admin.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 2: Login de empleado (debe funcionar)
        System.out.println("\n2. 🔐 Login EMPLEADO (kevin.peralta):");
        Usuario empleado = gestor.login("kevin.peralta", "empleado123");
        if (empleado != null) {
            System.out.println("   ✅ Login exitoso - Tipo: " + empleado.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 3: Login de cliente (debe funcionar)
        System.out.println("\n3. 🔐 Login CLIENTE (edwar.saire):");
        Usuario cliente = gestor.login("edwar.saire", "cliente123");
        if (cliente != null) {
            System.out.println("   ✅ Login exitoso - Tipo: " + cliente.getTipo());
        } else {
            System.out.println("   ❌ Login falló");
        }
        
        // Prueba 4: Login con credenciales incorrectas (debe fallar)
        System.out.println("\n4. 🔐 Login con CREDENCIALES INCORRECTAS:");
        Usuario incorrecto = gestor.login("usuario.inexistente", "password123");
        if (incorrecto == null) {
            System.out.println("   ✅ Correcto: Login falló como se esperaba");
        } else {
            System.out.println("   ❌ Error: Login debería haber fallado");
        }
        
        System.out.println("\n🎉 Prueba de LOGIN con BD completada!");
    }
}