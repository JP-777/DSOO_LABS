package DSOO_LABS.laboratorio7.dao;

import DSOO_LABS.laboratorio7.database.DatabaseConnection;
import DSOO_LABS.laboratorio7.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    // Listar TODOS los clientes
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        
        // SQL para obtener clientes con sus datos de persona
        String sql = "SELECT " +
                     "  c.codigo_cliente, " +
                     "  p.dni, " +
                     "  p.nombre, " +
                     "  p.apellido, " +
                     "  p.direccion, " +
                     "  p.telefono, " +
                     "  c.correo, " +
                     "  p.estado " +
                     "FROM clientes c " +
                     "JOIN personas p ON c.id_cliente = p.id_persona " +
                     "ORDER BY p.apellido, p.nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                // Crear cliente con datos de la BD
                Cliente cliente = new Cliente(
                    rs.getString("codigo_cliente"),  // idCliente (C001, C002)
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("estado")
                );
                clientes.add(cliente);
            }
            
            System.out.println("✅ Clientes cargados desde BD: " + clientes.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    // Buscar cliente por código (C001, C002, etc.)
    public Cliente buscarPorCodigo(String codigoCliente) {
        String sql = "SELECT " +
                     "  c.codigo_cliente, " +
                     "  p.dni, " +
                     "  p.nombre, " +
                     "  p.apellido, " +
                     "  p.direccion, " +
                     "  p.telefono, " +
                     "  c.correo, " +
                     "  p.estado " +
                     "FROM clientes c " +
                     "JOIN personas p ON c.id_cliente = p.id_persona " +
                     "WHERE c.codigo_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigoCliente);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Cliente(
                    rs.getString("codigo_cliente"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("estado")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cliente: " + e.getMessage());
        }
        
        return null;
    }
    public boolean agregarCliente(Cliente cliente) {
    // SQL para insertar en dos tablas (personas y clientes)
    String sqlPersona = "INSERT INTO personas (dni, nombre, apellido, direccion, telefono, tipo) VALUES (?, ?, ?, ?, ?, 'CLIENTE')";
    String sqlCliente = "INSERT INTO clientes (id_cliente, codigo_cliente, correo) VALUES (?, ?, ?)";
    
    Connection conn = null;
    PreparedStatement pstmtPersona = null;
    PreparedStatement pstmtCliente = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción
        
        // 1. Insertar en tabla personas
        pstmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
        pstmtPersona.setString(1, cliente.getDni());
        pstmtPersona.setString(2, cliente.getNombre());
        pstmtPersona.setString(3, cliente.getApellido());
        pstmtPersona.setString(4, cliente.getDireccion());
        pstmtPersona.setString(5, cliente.getTelefono());
        
        int filasPersona = pstmtPersona.executeUpdate();
        if (filasPersona == 0) {
            conn.rollback();
            return false;
        }
        
        // Obtener el ID generado automáticamente para la persona
        ResultSet generatedKeys = pstmtPersona.getGeneratedKeys();
        int idPersonaGenerado = 0;
        if (generatedKeys.next()) {
            idPersonaGenerado = generatedKeys.getInt(1);
        }
        
        // 2. Insertar en tabla clientes
        pstmtCliente = conn.prepareStatement(sqlCliente);
        pstmtCliente.setInt(1, idPersonaGenerado);
        pstmtCliente.setString(2, cliente.getIdCliente()); // C001, C002, etc.
        pstmtCliente.setString(3, cliente.getCorreo());
        
        int filasCliente = pstmtCliente.executeUpdate();
        if (filasCliente == 0) {
            conn.rollback();
            return false;
        }
        
        conn.commit();
        System.out.println("✅ Cliente agregado a BD: " + cliente.getNombre() + " " + cliente.getApellido());
        return true;
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("❌ Error al agregar cliente: " + e.getMessage());
        return false;
    } finally {
        try {
            if (pstmtPersona != null) pstmtPersona.close();
            if (pstmtCliente != null) pstmtCliente.close();
            if (conn != null) conn.setAutoCommit(true); // Restaurar auto-commit
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public boolean eliminarCliente(String codigoCliente) {
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción
        
        // 1. Primero obtener el ID del cliente
        int idCliente = -1;
        String sql1 = "SELECT id_cliente FROM clientes WHERE codigo_cliente = ?";
        try (PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setString(1, codigoCliente);
            try (ResultSet rs = pstmt1.executeQuery()) {
                if (rs.next()) {
                    idCliente = rs.getInt("id_cliente");
                }
            }
        }
        
        if (idCliente == -1) {
            conn.rollback();
            return false; // Cliente no encontrado
        }
        
        // 2. Eliminar relaciones en titularidad (si existen)
        String sql2 = "DELETE FROM titularidad WHERE id_cliente = ?";
        try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt2.setInt(1, idCliente);
            pstmt2.executeUpdate();
        }
        
        // 3. Eliminar usuario asociado (si existe)
        String sql3 = "DELETE FROM usuarios WHERE id_persona = ?";
        try (PreparedStatement pstmt3 = conn.prepareStatement(sql3)) {
            pstmt3.setInt(1, idCliente);
            pstmt3.executeUpdate();
        }
        
        // 4. Eliminar de la tabla clientes
        String sql4 = "DELETE FROM clientes WHERE codigo_cliente = ?";
        try (PreparedStatement pstmt4 = conn.prepareStatement(sql4)) {
            pstmt4.setString(1, codigoCliente);
            int filasCliente = pstmt4.executeUpdate();
            
            if (filasCliente == 0) {
                conn.rollback();
                return false;
            }
        }
        
        // 5. Finalmente eliminar de personas
        String sql5 = "DELETE FROM personas WHERE id_persona = ? AND tipo = 'CLIENTE'";
        try (PreparedStatement pstmt5 = conn.prepareStatement(sql5)) {
            pstmt5.setInt(1, idCliente);
            int filasPersona = pstmt5.executeUpdate();
            
            if (filasPersona > 0) {
                conn.commit();
                System.out.println("✅ Cliente eliminado de BD: " + codigoCliente);
                return true;
            } else {
                conn.rollback();
                return false;
            }
        }
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
        return false;
    } finally {
        try {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar auto-commit
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public Cliente buscarPorPersonaId(int idPersona) {
    String sql = """
        SELECT c.codigo_cliente, p.dni, p.nombre, p.apellido, 
               p.direccion, p.telefono, c.correo, p.estado
        FROM clientes c
        JOIN personas p ON c.id_cliente = p.id_persona
        WHERE c.id_cliente = ?
        """;
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, idPersona);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new Cliente(
                    rs.getString("codigo_cliente"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("estado")
                );
            }
        }
        
    } catch (SQLException e) {
        System.err.println("❌ Error al buscar cliente por ID persona: " + e.getMessage());
    }
    
    return null;
}
}