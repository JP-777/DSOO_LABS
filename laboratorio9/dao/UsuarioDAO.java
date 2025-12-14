package DSOO_LABS.laboratorio9.dao;

import DSOO_LABS.laboratorio9.database.DatabaseConnection;
import DSOO_LABS.laboratorio9.model.*;
import java.sql.*;

public class UsuarioDAO {
    
    // Método para autenticar usuario
    public Usuario login(String username, String password) {
        String sql = """
            SELECT u.username, u.password_hash, u.tipo_usuario, u.id_persona,
                   p.tipo as tipo_persona, p.nombre, p.apellido
            FROM usuarios u
            JOIN personas p ON u.id_persona = p.id_persona
            WHERE u.username = ? AND u.password_hash = ? AND u.estado = 'ACTIVO'
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String tipoUsuario = rs.getString("tipo_usuario");
                    int idPersona = rs.getInt("id_persona");
                    String tipoPersona = rs.getString("tipo_persona");
                    
                    // Crear el usuario según su tipo
                    if (tipoUsuario.equals("ADMINISTRADOR")) {
                        return new UsuarioAdministrador(username, password, "Administración");
                        
                    } else if (tipoUsuario.equals("EMPLEADO")) {
                        // Obtener datos del empleado
                        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                        Empleado empleado = empleadoDAO.buscarPorPersonaId(idPersona);
                        
                        if (empleado != null) {
                            return new UsuarioEmpleado(username, password, empleado);
                        }
                        
                    } else if (tipoUsuario.equals("CLIENTE")) {
                        // Obtener datos del cliente
                        ClienteDAO clienteDAO = new ClienteDAO();
                        Cliente cliente = clienteDAO.buscarPorPersonaId(idPersona);
                        
                        if (cliente != null) {
                            return new UsuarioCliente(username, password, cliente);
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Método para registrar nuevo usuario (para admin)
    public boolean registrarUsuario(Usuario usuario, int idPersona) {
        String sql = "INSERT INTO usuarios (username, password_hash, tipo_usuario, id_persona) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getContrasena()); // Por ahora texto plano
            pstmt.setString(3, usuario.getTipo());
            pstmt.setInt(4, idPersona);
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    
    // Método para verificar si usuario existe
    public boolean existeUsuario(String username) {
        String sql = "SELECT COUNT(*) as total FROM usuarios WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar usuario: " + e.getMessage());
        }
        
        return false;
    }
}