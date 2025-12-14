package DSOO_LABS.laboratorio9.dao;

import DSOO_LABS.laboratorio9.database.DatabaseConnection;
import DSOO_LABS.laboratorio9.model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT e.codigo_empleado, p.dni, p.nombre, p.apellido, " +
                     "p.direccion, p.telefono, e.cargo " +
                     "FROM empleados e " +
                     "JOIN personas p ON e.id_empleado = p.id_persona " +
                     "ORDER BY p.apellido, p.nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Empleado emp = new Empleado(
                    rs.getString("codigo_empleado"),  // E001, E002
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("cargo")
                );
                empleados.add(emp);
            }
            
            System.out.println("✅ Empleados cargados desde BD: " + empleados.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar empleados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return empleados;
    }
    
    public Empleado buscarPorCodigo(String codigoEmpleado) {
        String sql = "SELECT e.codigo_empleado, p.dni, p.nombre, p.apellido, " +
                     "p.direccion, p.telefono, e.cargo " +
                     "FROM empleados e " +
                     "JOIN personas p ON e.id_empleado = p.id_persona " +
                     "WHERE e.codigo_empleado = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigoEmpleado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                        rs.getString("codigo_empleado"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("cargo")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar empleado: " + e.getMessage());
        }
        
        return null;
    }
    public boolean agregarEmpleado(Empleado empleado) {
    String sqlPersona = "INSERT INTO personas (dni, nombre, apellido, direccion, telefono, tipo) VALUES (?, ?, ?, ?, ?, 'EMPLEADO')";
    String sqlEmpleado = "INSERT INTO empleados (id_empleado, codigo_empleado, cargo) VALUES (?, ?, ?)";
    
    Connection conn = null;
    PreparedStatement pstmtPersona = null;
    PreparedStatement pstmtEmpleado = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        
        // 1. Insertar en personas
        pstmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
        pstmtPersona.setString(1, empleado.getDni());
        pstmtPersona.setString(2, empleado.getNombre());
        pstmtPersona.setString(3, empleado.getApellido());
        pstmtPersona.setString(4, empleado.getDireccion());
        pstmtPersona.setString(5, empleado.getTelefono());
        
        int filasPersona = pstmtPersona.executeUpdate();
        if (filasPersona == 0) {
            conn.rollback();
            return false;
        }
        
        // Obtener ID generado
        ResultSet generatedKeys = pstmtPersona.getGeneratedKeys();
        int idPersonaGenerado = 0;
        if (generatedKeys.next()) {
            idPersonaGenerado = generatedKeys.getInt(1);
        }
        
        // 2. Insertar en empleados
        pstmtEmpleado = conn.prepareStatement(sqlEmpleado);
        pstmtEmpleado.setInt(1, idPersonaGenerado);
        pstmtEmpleado.setString(2, empleado.getIdEmpleado()); // E001, E002, etc.
        pstmtEmpleado.setString(3, empleado.getCargo());
        
        int filasEmpleado = pstmtEmpleado.executeUpdate();
        if (filasEmpleado == 0) {
            conn.rollback();
            return false;
        }
        
        conn.commit();
        System.out.println("✅ Empleado agregado a BD: " + empleado.getNombre() + " " + empleado.getApellido());
        return true;
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("❌ Error al agregar empleado: " + e.getMessage());
        return false;
    } finally {
        try {
            if (pstmtPersona != null) pstmtPersona.close();
            if (pstmtEmpleado != null) pstmtEmpleado.close();
            if (conn != null) conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public Empleado buscarPorPersonaId(int idPersona) {
    String sql = """
        SELECT e.codigo_empleado, p.dni, p.nombre, p.apellido, 
               p.direccion, p.telefono, e.cargo
        FROM empleados e
        JOIN personas p ON e.id_empleado = p.id_persona
        WHERE e.id_empleado = ?
        """;
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, idPersona);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new Empleado(
                    rs.getString("codigo_empleado"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("cargo")
                );
            }
        }
        
    } catch (SQLException e) {
        System.err.println("❌ Error al buscar empleado por ID persona: " + e.getMessage());
    }
    
    return null;
}
}