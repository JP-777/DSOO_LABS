package GUI.operations;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Transaccion;
import DSOO_LABS.laboratorio7.model.Cuenta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TransactionHistoryFrame extends JFrame {
    private BancoService bancoService;
    private JTextField txtNumeroCuenta;
    private JButton btnBuscar;
    private JButton btnCerrar;
    private JTable tablaTransacciones;
    private DefaultTableModel tableModel;
    
    public TransactionHistoryFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Historial de Transacciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));
        
        JLabel lblTitle = new JLabel("HISTORIAL DE TRANSACCIONES", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar por cuenta"));
        
        JLabel lblCuenta = new JLabel("Número de Cuenta:");
        txtNumeroCuenta = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(this::buscarTransacciones);
        
        searchPanel.add(lblCuenta);
        searchPanel.add(txtNumeroCuenta);
        searchPanel.add(btnBuscar);
        
        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        
        // Tabla de transacciones
        String[] columnNames = {"ID", "Tipo", "Monto (S/)", "Fecha", "Empleado", "Cuenta"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaTransacciones = new JTable(tableModel);
        tablaTransacciones.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaTransacciones.setRowHeight(25);
        tablaTransacciones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaTransacciones.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaTransacciones.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablaTransacciones);
        
        // Panel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        bottomPanel.add(btnCerrar);
        
        // Ensamblar
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Cargar datos de ejemplo
        cargarDatosEjemplo();
    }
    
    private void buscarTransacciones(ActionEvent e) {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        
        if (numeroCuenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un número de cuenta", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Simulación de búsqueda
        // Aquí iría la lógica real de búsqueda
        Object[] fila1 = {"T001", "Depósito", "500.00", "2025-01-15 10:30", "E001", numeroCuenta};
        Object[] fila2 = {"T002", "Retiro", "200.00", "2025-01-16 14:45", "E002", numeroCuenta};
        Object[] fila3 = {"T003", "Depósito", "1000.00", "2025-01-18 09:15", "E001", numeroCuenta};
        
        tableModel.addRow(fila1);
        tableModel.addRow(fila2);
        tableModel.addRow(fila3);
    }
    
    private void cargarDatosEjemplo() {
        // Datos de ejemplo para mostrar
        Object[] fila1 = {"T1001", "Depósito", "1000.00", "2025-01-10 09:00", "E001", "1001"};
        Object[] fila2 = {"T1002", "Retiro", "500.00", "2025-01-11 14:30", "E002", "1001"};
        Object[] fila3 = {"T1003", "Depósito", "2500.00", "2025-01-12 11:15", "E001", "1002"};
        Object[] fila4 = {"T1004", "Depósito", "1500.00", "2025-01-13 16:45", "E003", "1003"};
        Object[] fila5 = {"T1005", "Retiro", "1000.00", "2025-01-14 10:20", "E002", "1002"};
        
        tableModel.addRow(fila1);
        tableModel.addRow(fila2);
        tableModel.addRow(fila3);
        tableModel.addRow(fila4);
        tableModel.addRow(fila5);
    }
}
