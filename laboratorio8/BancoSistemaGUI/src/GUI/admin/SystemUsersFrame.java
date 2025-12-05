package GUI.admin;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SystemUsersFrame extends JFrame {
    private BancoService bancoService;
    private JTable tablaUsuarios;
    private DefaultTableModel tableModel;
    
    public SystemUsersFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
        cargarUsuarios();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Usuarios del Sistema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(128, 0, 128));
        JLabel lblTitle = new JLabel("USUARIOS DEL SISTEMA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Configurar tabla
        String[] columnNames = {"Usuario", "Tipo", "Estado", "Último Acceso", "Detalles"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaUsuarios = new JTable(tableModel);
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaUsuarios.getTableHeader().setBackground(new Color(128, 0, 128));
        tablaUsuarios.getTableHeader().setForeground(Color.WHITE);
        
        // Colorear filas según tipo de usuario
        tablaUsuarios.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String tipo = (String) table.getValueAt(row, 1);
                    if ("ADMINISTRADOR".equals(tipo)) {
                        c.setBackground(new Color(255, 228, 225)); // Rosa claro
                    } else if ("EMPLEADO".equals(tipo)) {
                        c.setBackground(new Color(225, 255, 225)); // Verde claro
                    } else if ("CLIENTE".equals(tipo)) {
                        c.setBackground(new Color(225, 225, 255)); // Azul claro
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnNuevo = new JButton("Nuevo Usuario");
        btnNuevo.setBackground(new Color(60, 179, 113));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.addActionListener(e -> nuevoUsuario());
        
        JButton btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.setBackground(new Color(255, 165, 0));
        btnCambiarEstado.setForeground(Color.WHITE);
        btnCambiarEstado.addActionListener(e -> cambiarEstadoUsuario());
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarUsuarios());
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnCambiarEstado);
        buttonPanel.add(btnRefrescar);
        buttonPanel.add(btnCerrar);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarUsuarios() {
        tableModel.setRowCount(0);
        
        // Datos de ejemplo
        Object[][] datos = {
            {"jordan.paredes", "ADMINISTRADOR", "Activo", "2025-01-20 14:30", "Administrador General"},
            {"brayan.sanchez", "ADMINISTRADOR", "Activo", "2025-01-20 13:45", "Administrador de Sistemas"},
            {"kevin.peralta", "EMPLEADO", "Activo", "2025-01-20 12:15", "Gerente de Operaciones"},
            {"elkin.ramos", "EMPLEADO", "Activo", "2025-01-20 11:30", "Supervisor de Cuentas"},
            {"fernando.solsol", "EMPLEADO", "Activo", "2025-01-20 10:45", "Jefe de Atención al Cliente"},
            {"edwar.saire", "CLIENTE", "Activo", "2025-01-20 09:20", "Cliente VIP"},
            {"elon.musk", "CLIENTE", "Activo", "2025-01-19 16:10", "Cliente Corporativo"},
            {"satya.nadella", "CLIENTE", "Activo", "2025-01-19 15:30", "Cliente Empresarial"},
            {"tim.cook", "CLIENTE", "Inactivo", "2025-01-18 14:15", "Cliente Regular"},
            {"sundar.pichai", "CLIENTE", "Activo", "2025-01-18 13:40", "Cliente Premium"}
        };
        
        for (Object[] fila : datos) {
            tableModel.addRow(fila);
        }
    }
    
    private void nuevoUsuario() {
        JOptionPane.showMessageDialog(this,
            "Función para crear nuevo usuario\n" +
            "Se implementará en la próxima versión",
            "En desarrollo",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cambiarEstadoUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un usuario",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String usuario = (String) tableModel.getValueAt(selectedRow, 0);
        String estadoActual = (String) tableModel.getValueAt(selectedRow, 2);
        String nuevoEstado = estadoActual.equals("Activo") ? "Inactivo" : "Activo";
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Cambiar estado del usuario?\n" +
            "Usuario: " + usuario + "\n" +
            "Estado actual: " + estadoActual + "\n" +
            "Nuevo estado: " + nuevoEstado,
            "Confirmar cambio",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt(nuevoEstado, selectedRow, 2);
            JOptionPane.showMessageDialog(this,
                "Estado actualizado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
