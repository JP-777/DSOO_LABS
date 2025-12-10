package DSOO_LABS.laboratorio7.dao;

import DSOO_LABS.laboratorio7.database.DatabaseConnection;
import DSOO_LABS.laboratorio7.model.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {
    
    public List<Cuenta> listarTodas() {
        List<Cuenta> cuentas = new ArrayList<>();
        String sql = "SELECT numero_cuenta, tipo_cuenta, saldo " +
                     "FROM cuentas ORDER BY numero_cuenta";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Cuenta cuenta = new Cuenta(
                    rs.getString("numero_cuenta"),
                    rs.getString("tipo_cuenta"),
                    rs.getDouble("saldo")
                );
                cuentas.add(cuenta);
            }
            
            System.out.println("✅ Cuentas cargadas desde BD: " + cuentas.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar cuentas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return cuentas;
    }
    
    public Cuenta buscarPorNumero(String numeroCuenta) {
        String sql = "SELECT numero_cuenta, tipo_cuenta, saldo " +
                     "FROM cuentas WHERE numero_cuenta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCuenta);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cuenta(
                        rs.getString("numero_cuenta"),
                        rs.getString("tipo_cuenta"),
                        rs.getDouble("saldo")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cuenta: " + e.getMessage());
        }
        
        return null;
    }
    public boolean agregarCuenta(Cuenta cuenta) {
    String sql = "INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo) VALUES (?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, cuenta.getNumeroCuenta());
        pstmt.setString(2, cuenta.getTipoCuenta());
        pstmt.setDouble(3, cuenta.getSaldo());
        
        int filasInsertadas = pstmt.executeUpdate();
        
        if (filasInsertadas > 0) {
            System.out.println("✅ Cuenta agregada a BD: " + cuenta.getNumeroCuenta());
            return true;
        } else {
            return false;
        }
        
    } catch (SQLException e) {
        System.err.println("❌ Error al agregar cuenta: " + e.getMessage());
        return false;
    }
}

public boolean eliminarCuenta(String numeroCuenta) {
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción
        
        // 1. Primero obtener el ID de la cuenta
        int idCuenta = -1;
        String sql1 = "SELECT id_cuenta FROM cuentas WHERE numero_cuenta = ?";
        try (PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setString(1, numeroCuenta);
            try (ResultSet rs = pstmt1.executeQuery()) {
                if (rs.next()) {
                    idCuenta = rs.getInt("id_cuenta");
                }
            }
        }
        
        if (idCuenta == -1) {
            conn.rollback();
            System.out.println("⚠ Cuenta no encontrada: " + numeroCuenta);
            return false; // Cuenta no encontrada
        }
        
        // 2. Eliminar transacciones asociadas (PRIMERO)
        String sqlTransacciones = "DELETE FROM transacciones WHERE id_cuenta = ?";
        try (PreparedStatement pstmtTrans = conn.prepareStatement(sqlTransacciones)) {
            pstmtTrans.setInt(1, idCuenta);
            int transEliminadas = pstmtTrans.executeUpdate();
            System.out.println("✅ Transacciones eliminadas: " + transEliminadas);
        }
        
        // 3. Eliminar relaciones en titularidad
        String sqlTitularidad = "DELETE FROM titularidad WHERE id_cuenta = ?";
        try (PreparedStatement pstmtTit = conn.prepareStatement(sqlTitularidad)) {
            pstmtTit.setInt(1, idCuenta);
            pstmtTit.executeUpdate();
        }
        
        // 4. Finalmente eliminar la cuenta
        String sqlCuenta = "DELETE FROM cuentas WHERE numero_cuenta = ?";
        try (PreparedStatement pstmtCuenta = conn.prepareStatement(sqlCuenta)) {
            pstmtCuenta.setString(1, numeroCuenta);
            int filasEliminadas = pstmtCuenta.executeUpdate();
            
            if (filasEliminadas > 0) {
                conn.commit();
                System.out.println("✅ Cuenta eliminada de BD: " + numeroCuenta);
                return true;
            } else {
                conn.rollback();
                System.out.println("❌ No se pudo eliminar la cuenta");
                return false;
            }
        }
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("❌ Error al eliminar cuenta: " + e.getMessage());
        return false;
    } finally {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public boolean actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
    String sql = "UPDATE cuentas SET saldo = ? WHERE numero_cuenta = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setDouble(1, nuevoSaldo);
        pstmt.setString(2, numeroCuenta);
        
        int filasActualizadas = pstmt.executeUpdate();
        return filasActualizadas > 0;
        
    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar saldo: " + e.getMessage());
        return false;
    }
}
}