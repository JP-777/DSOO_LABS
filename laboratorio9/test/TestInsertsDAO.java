package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.dao.*;
import DSOO_LABS.laboratorio9.model.*;

public class TestInsertsDAO {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de INSERTS en MySQL\n");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();
        
        // 1. Agregar nuevo cliente
        System.out.println("1. ➕ Agregando NUEVO cliente:");
        Cliente nuevoCliente = new Cliente(
            "C006", 
            "87654321", 
            "Mark", 
            "Zuckerberg", 
            "Facebook HQ, Menlo Park", 
            "944567890", 
            "mark.zuckerberg@meta.com", 
            "ACTIVO"
        );
        
        boolean clienteAgregado = clienteDAO.agregarCliente(nuevoCliente);
        System.out.println("   Resultado: " + (clienteAgregado ? "✅ Éxito" : "❌ Falló"));
        
        // 2. Agregar nueva cuenta
        System.out.println("\n2. ➕ Agregando NUEVA cuenta:");
        Cuenta nuevaCuenta = new Cuenta("1007", "AHORROS", 30000.00);
        boolean cuentaAgregada = cuentaDAO.agregarCuenta(nuevaCuenta);
        System.out.println("   Resultado: " + (cuentaAgregada ? "✅ Éxito" : "❌ Falló"));
        
        // 3. Verificar que se agregaron
        System.out.println("\n3. 📋 Verificando datos actualizados:");
        System.out.println("   Clientes total: " + clienteDAO.listarTodos().size());
        System.out.println("   Cuentas total: " + cuentaDAO.listarTodas().size());
        
        // 4. Buscar el nuevo cliente
        System.out.println("\n4. 🔍 Buscando cliente C006:");
        Cliente clienteEncontrado = clienteDAO.buscarPorCodigo("C006");
        if (clienteEncontrado != null) {
            System.out.println("   ✅ Encontrado: " + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellido());
        } else {
            System.out.println("   ❌ No encontrado");
        }
        
        System.out.println("\n🎉 Prueba de INSERTS completada!");
    }
}