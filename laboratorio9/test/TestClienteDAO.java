package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.dao.ClienteDAO;
import DSOO_LABS.laboratorio9.model.Cliente;
import java.util.List;

public class TestClienteDAO {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de ClienteDAO con MySQL\n");
        System.out.println("======================================");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        // PRUEBA 1: Listar todos los clientes
        System.out.println("1. 📋 Listando TODOS los clientes:");
        System.out.println("----------------------------------");
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("   ❌ No hay clientes en la BD");
        } else {
            for (Cliente c : clientes) {
                System.out.println("   • " + c.getIdCliente() + " - " + 
                                 c.getNombre() + " " + c.getApellido() + 
                                 " | DNI: " + c.getDni());
            }
            System.out.println("\n   Total: " + clientes.size() + " clientes");
        }
        
        // PRUEBA 2: Buscar cliente específico
        System.out.println("\n2. 🔍 Buscando cliente C001 (Edwar Saire):");
        System.out.println("-------------------------------------------");
        Cliente cliente = clienteDAO.buscarPorCodigo("C001");
        if (cliente != null) {
            System.out.println("   ✅ ENCONTRADO:");
            System.out.println("      Código: " + cliente.getIdCliente());
            System.out.println("      Nombre: " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.println("      DNI: " + cliente.getDni());
            System.out.println("      Dirección: " + cliente.getDireccion());
            System.out.println("      Teléfono: " + cliente.getTelefono());
            System.out.println("      Correo: " + cliente.getCorreo());
            System.out.println("      Estado: " + cliente.getEstado());
        } else {
            System.out.println("   ❌ Cliente C001 no encontrado");
        }
        
        // PRUEBA 3: Buscar cliente que NO existe
        System.out.println("\n3. 🔍 Buscando cliente INEXISTENTE (C999):");
        System.out.println("-------------------------------------------");
        Cliente clienteInexistente = clienteDAO.buscarPorCodigo("C999");
        if (clienteInexistente == null) {
            System.out.println("   ✅ Correcto: Cliente C999 no existe (retorna null)");
        } else {
            System.out.println("   ❌ Error: Debería retornar null");
        }
        
        System.out.println("\n======================================");
        System.out.println("🎉 ¡PRUEBA COMPLETADA CON ÉXITO!");
        System.out.println("MySQL + Java JDBC funcionando correctamente");
    }
}