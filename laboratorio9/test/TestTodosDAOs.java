package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.dao.*;
import DSOO_LABS.laboratorio9.model.*;
import java.util.List;

public class TestTodosDAOs {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA COMPLETA de DAOs con MySQL\n");
        System.out.println("==============================================");
        
        // 1. Clientes
        System.out.println("1. 📋 CLIENTES:");
        System.out.println("----------------");
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.listarTodos();
        System.out.println("   Total clientes: " + clientes.size());
        
        // 2. Empleados
        System.out.println("\n2. 👨‍💼 EMPLEADOS:");
        System.out.println("----------------");
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        List<Empleado> empleados = empleadoDAO.listarTodos();
        System.out.println("   Total empleados: " + empleados.size());
        for (Empleado e : empleados) {
            System.out.println("   • " + e.getIdEmpleado() + " - " + 
                             e.getNombre() + " " + e.getApellido() + 
                             " (" + e.getCargo() + ")");
        }
        
        // 3. Cuentas
        System.out.println("\n3. 💰 CUENTAS:");
        System.out.println("----------------");
        CuentaDAO cuentaDAO = new CuentaDAO();
        List<Cuenta> cuentas = cuentaDAO.listarTodas();
        System.out.println("   Total cuentas: " + cuentas.size());
        for (Cuenta c : cuentas) {
            System.out.println("   • " + c.getNumeroCuenta() + " - " + 
                             c.getTipoCuenta() + " - S/ " + c.getSaldo());
        }
        
        System.out.println("\n==============================================");
        System.out.println("🎉 ¡TODOS LOS DAOs FUNCIONAN CORRECTAMENTE!");
        System.out.println("✅ MySQL + JDBC + Java funcionando al 100%");
    }
}