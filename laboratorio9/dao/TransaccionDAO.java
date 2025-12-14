package DSOO_LABS.laboratorio9.dao;

import DSOO_LABS.laboratorio9.database.DatabaseConnection;
import DSOO_LABS.laboratorio9.model.Transaccion;
import DSOO_LABS.laboratorio9.model.Cuenta;
import DSOO_LABS.laboratorio9.model.Deposito;
import DSOO_LABS.laboratorio9.model.Empleado;
import DSOO_LABS.laboratorio9.model.Retiro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO {
    
    public boolean registrarTransaccion(double monto, String tipoTransaccion, int idCuenta, int idEmpleado) {
        String sql = """
            INSERT INTO transacciones 
            (tipo_transaccion, monto, id_cuenta, id_empleado, descripcion, fecha_hora) 
            VALUES (?, ?, ?, ?, ?, NOW())
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipoTransaccion);
            pstmt.setDouble(2, monto);
            pstmt.setInt(3, idCuenta);
            
            // TRUCO: Si el ID es -1 (no encontrado o cajero), insertamos NULL en la BD
            if (idEmpleado == -1) {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(4, idEmpleado);
            }
            
            pstmt.setString(5, tipoTransaccion + " realizado vía Sistema");
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar transacción: " + e.getMessage());
            // Imprime esto en consola para que veas si hay otro error oculto
            e.printStackTrace(); 
            return false;
        }
    }
    // PEGAR ESTO EN TransaccionDAO.java

    public List<Transaccion> obtenerUltimasTransacciones() {
        List<Transaccion> lista = new ArrayList<>();
        
        // USO LEFT JOIN PARA QUE NO OCULTE LAS TRANSACCIONES SIN EMPLEADO
        String sql = """
            SELECT t.id_transaccion, t.tipo_transaccion, t.monto, t.fecha_hora,
                   c.numero_cuenta, c.tipo_cuenta, c.saldo,
                   e.codigo_empleado, p.nombre, p.apellido
            FROM transacciones t
            JOIN cuentas c ON t.id_cuenta = c.id_cuenta
            LEFT JOIN empleados e ON t.id_empleado = e.id_empleado
            LEFT JOIN personas p ON e.id_empleado = p.id_persona
            ORDER BY t.fecha_hora DESC
            LIMIT 50
            """;
            
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            System.out.println("🔍 Buscando transacciones en BD..."); // DEBUG
            int contador = 0;

            while (rs.next()) {
                contador++;
                // Reconstruir objetos
                Cuenta c = new Cuenta(rs.getString("numero_cuenta"), rs.getString("tipo_cuenta"), rs.getDouble("saldo"));
                
                Empleado emp = null;
                String codigoEmp = rs.getString("codigo_empleado");
                
                if (codigoEmp != null) {
                    emp = new Empleado();
                    
                    // 2. ¡AQUÍ ESTÁ EL ERROR! Faltaba esta línea:
                    emp.setIdEmpleado(codigoEmp); 
                    
                    // Opcional: También poner el nombre si quieres
                    emp.setNombre(rs.getString("nombre")); 
                }

                String tipo = rs.getString("tipo_transaccion");
                Transaccion t;
                
                // Convertir la fecha de SQL a Java
                java.sql.Timestamp fechaSql = rs.getTimestamp("fecha_hora");
                // Nota: Aquí podrías necesitar ajustar la fecha si Transaccion usa LocalDateTime

                if ("DEPOSITO".equalsIgnoreCase(tipo)) {
                    t = new Deposito("T"+rs.getInt("id_transaccion"), rs.getDouble("monto"), emp, c);
                } else {
                    t = new Retiro("T"+rs.getInt("id_transaccion"), rs.getDouble("monto"), emp, c);
                }
                
                lista.add(t);
            }
            System.out.println("✅ Se encontraron " + contador + " transacciones."); // DEBUG
            
        } catch (SQLException e) {
            System.err.println("❌ Error SQL al listar transacciones: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    // Obtener transacciones de una cuenta específica
    public List<Transaccion> obtenerTransaccionesPorCuenta(String numeroCuenta) {
        List<Transaccion> transacciones = new ArrayList<>();
        
        // Primero necesitamos el id_cuenta a partir del número
        String sql = """
            SELECT t.id_transaccion, t.tipo_transaccion, t.monto, t.fecha_hora,
                   t.descripcion, e.codigo_empleado, emp.nombre as nombre_empleado
            FROM transacciones t
            JOIN cuentas c ON t.id_cuenta = c.id_cuenta
            LEFT JOIN empleados e ON t.id_empleado = e.id_empleado
            LEFT JOIN personas emp ON e.id_empleado = emp.id_persona
            WHERE c.numero_cuenta = ?
            ORDER BY t.fecha_hora DESC
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCuenta);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Por simplicidad, creamos objetos Transaccion genéricos
                    // En un sistema real, crearíamos Deposito o Retiro según el tipo
                    String tipo = rs.getString("tipo_transaccion");
                    Transaccion t;
                    
                    // Crear objeto según el tipo (simplificado)
                    if (tipo.equals("DEPOSITO")) {
                        t = new Deposito(
                            "T" + rs.getInt("id_transaccion"),
                            rs.getDouble("monto"),
                            null, // Empleado sería null por ahora
                            null  // Cuenta sería null por ahora
                        );
                    } else {
                        t = new Retiro(
                            "T" + rs.getInt("id_transaccion"),
                            rs.getDouble("monto"),
                            null,
                            null
                        );
                    }
                    
                    transacciones.add(t);
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener transacciones: " + e.getMessage());
        }
        
        return transacciones;
    }
    
    // Listar todas las transacciones
    public void listarTodas() {
        String sql = """
            SELECT t.id_transaccion, t.tipo_transaccion, t.monto, t.fecha_hora,
                   c.numero_cuenta, e.codigo_empleado, 
                   CONCAT(emp.nombre, ' ', emp.apellido) as nombre_empleado
            FROM transacciones t
            JOIN cuentas c ON t.id_cuenta = c.id_cuenta
            LEFT JOIN empleados e ON t.id_empleado = e.id_empleado
            LEFT JOIN personas emp ON e.id_empleado = emp.id_persona
            ORDER BY t.fecha_hora DESC
            LIMIT 20
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n=== ÚLTIMAS 20 TRANSACCIONES ===");
            boolean hayTransacciones = false;
            
            while (rs.next()) {
                hayTransacciones = true;
                System.out.println("ID: " + rs.getInt("id_transaccion") +
                                 " | Tipo: " + rs.getString("tipo_transaccion") +
                                 " | Monto: S/ " + String.format("%.2f", rs.getDouble("monto")) +
                                 " | Cuenta: " + rs.getString("numero_cuenta") +
                                 " | Fecha: " + rs.getTimestamp("fecha_hora") +
                                 " | Empleado: " + rs.getString("nombre_empleado"));
            }
            
            if (!hayTransacciones) {
                System.out.println("ℹ No hay transacciones registradas.");
            }
            System.out.println("================================\n");
            
        } catch (SQLException e) {
            System.err.println(" Error al listar transacciones: " + e.getMessage());
        }
    }
    
    // Método auxiliar: obtener ID de cuenta por número
    public int obtenerIdCuenta(String numeroCuenta) {
        String sql = "SELECT id_cuenta FROM cuentas WHERE numero_cuenta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCuenta);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cuenta");
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ID de cuenta: " + e.getMessage());
        }
        
        return -1;
    }
    
    // Método auxiliar: obtener ID de empleado por código
    public int obtenerIdEmpleado(String codigoEmpleado) {
        String sql = "SELECT id_empleado FROM empleados WHERE codigo_empleado = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigoEmpleado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_empleado");
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ID de empleado: " + e.getMessage());
        }
        
        return -1;
    }
}