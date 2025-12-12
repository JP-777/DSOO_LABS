package DSOO_LABS.laboratorio7.dao;

import DSOO_LABS.laboratorio7.database.DatabaseConnection;
import DSOO_LABS.laboratorio7.model.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TitularidadDAO {
    
    // Asignar una cuenta a un cliente
    public boolean asignarCuentaACliente(String codigoCliente, String numeroCuenta) {
        // Primero obtener IDs
        int idCliente = obtenerIdCliente(codigoCliente);
        int idCuenta = obtenerIdCuenta(numeroCuenta);
        
        if (idCliente == -1 || idCuenta == -1) {
            return false;
        }
        
        // Verificar si ya está asignada
        if (cuentaYaAsignada(idCliente, idCuenta)) {
            System.out.println(" Esta cuenta ya está asignada al cliente");
            return false;
        }
        
        // Asignar cuenta
        String sql = """
            INSERT INTO titularidad (id_cliente, id_cuenta, tipo_titular) 
            VALUES (?, ?, 'PRINCIPAL')
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, idCuenta);
            
            int filas = pstmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println(" Cuenta asignada exitosamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al asignar cuenta: " + e.getMessage());
        }
        
        return false;
    }
    
    // Obtener cuentas de un cliente
    public List<Cuenta> obtenerCuentasPorCliente(String codigoCliente) {
        List<Cuenta> cuentas = new ArrayList<>();
        
        String sql = """
            SELECT c.numero_cuenta, c.tipo_cuenta, c.saldo
            FROM cuentas c
            JOIN titularidad t ON c.id_cuenta = t.id_cuenta
            JOIN clientes cl ON t.id_cliente = cl.id_cliente
            WHERE cl.codigo_cliente = ?
            ORDER BY c.numero_cuenta
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigoCliente);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cuenta cuenta = new Cuenta(
                        rs.getString("numero_cuenta"),
                        rs.getString("tipo_cuenta"),
                        rs.getDouble("saldo")
                    );
                    cuentas.add(cuenta);
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener cuentas del cliente: " + e.getMessage());
        }
        
        return cuentas;
    }
    
    // Métodos auxiliares
    private int obtenerIdCliente(String codigoCliente) {
        String sql = "SELECT id_cliente FROM clientes WHERE codigo_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigoCliente);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ID de cliente: " + e.getMessage());
        }
        
        return -1;
    }
    
    private int obtenerIdCuenta(String numeroCuenta) {
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
    
    private boolean cuentaYaAsignada(int idCliente, int idCuenta) {
        String sql = "SELECT COUNT(*) as total FROM titularidad WHERE id_cliente = ? AND id_cuenta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, idCuenta);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al verificar titularidad: " + e.getMessage());
        }
        
        return false;
    }
}