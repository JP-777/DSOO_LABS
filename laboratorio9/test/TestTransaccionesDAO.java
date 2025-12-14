package DSOO_LABS.laboratorio9.test;

import DSOO_LABS.laboratorio9.dao.*;

public class TestTransaccionesDAO {
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA de Transacciones y Titularidad\n");
        
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        TitularidadDAO titularidadDAO = new TitularidadDAO();
        
        // 1. Probar IDs
        System.out.println("1. 🔍 Obteniendo IDs:");
        int idCuenta1001 = transaccionDAO.obtenerIdCuenta("1001");
        int idEmpleadoE001 = transaccionDAO.obtenerIdEmpleado("E001");
        System.out.println("   ID Cuenta 1001: " + idCuenta1001);
        System.out.println("   ID Empleado E001: " + idEmpleadoE001);
        
        // 2. Listar transacciones
        System.out.println("\n2. 📋 Listando transacciones:");
        transaccionDAO.listarTodas();
        
        // 3. Asignar cuenta a cliente
        System.out.println("3. 🔗 Asignando cuenta 1007 a cliente C006:");
        boolean asignada = titularidadDAO.asignarCuentaACliente("C006", "1007");
        System.out.println("   Resultado: " + (asignada ? "✅ Éxito" : "❌ Falló"));
        
        // 4. Ver cuentas de cliente
        System.out.println("\n4. 💳 Cuentas del cliente C006:");
        var cuentas = titularidadDAO.obtenerCuentasPorCliente("C006");
        System.out.println("   Total cuentas: " + cuentas.size());
        for (var cuenta : cuentas) {
            System.out.println("   • " + cuenta.getNumeroCuenta() + 
                             " - " + cuenta.getTipoCuenta() + 
                             " - S/ " + cuenta.getSaldo());
        }
        
        System.out.println("\n🎉 Prueba completada!");
    }
}