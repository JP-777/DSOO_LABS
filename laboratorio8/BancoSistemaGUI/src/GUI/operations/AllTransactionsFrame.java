package GUI.operations;

import service.BancoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AllTransactionsFrame extends JFrame {
    private BancoService bancoService;
    private JTable tablaTransacciones;
    private DefaultTableModel tableModel;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    private JButton btnExportar;
    
    public AllTransactionsFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
        cargarDatos();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Todas las Transacciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(139, 0, 139));
        JLabel lblTitle = new JLabel("TODAS LAS TRANSACCIONES DEL SISTEMA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Configurar tabla
        String[] columnNames = {"ID", "Tipo", "Monto (S/)", "Fecha", "Empleado", "Cuenta", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Double.class; // Monto
                return String.class;
            }
        };
        
        tablaTransacciones = new JTable(tableModel);
        tablaTransacciones.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaTransacciones.setRowHeight(22);
        tablaTransacciones.setAutoCreateRowSorter(true);
        
        // Configurar header
        tablaTransacciones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaTransacciones.getTableHeader().setBackground(new Color(139, 0, 139));
        tablaTransacciones.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablaTransacciones);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarDatos());
        
        btnExportar = new JButton("Exportar a CSV");
        btnExportar.setBackground(new Color(60, 179, 113));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.addActionListener(e -> exportarCSV());
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(220, 80, 80));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnRefrescar);
        buttonPanel.add(btnExportar);
        buttonPanel.add(btnCerrar);
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statsPanel.setBackground(new Color(240, 240, 240));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        JLabel lblTotal = new JLabel("Total transacciones: 0");
        JLabel lblTotalDepositos = new JLabel("Total depósitos: S/ 0.00");
        JLabel lblTotalRetiros = new JLabel("Total retiros: S/ 0.00");
        
        statsPanel.add(lblTotal);
        statsPanel.add(lblTotalDepositos);
        statsPanel.add(lblTotalRetiros);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        add(mainPanel);
    }
    
    private void cargarDatos() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Datos de ejemplo
        Object[][] datos = {
            {"T001", "Depósito", 1000.00, "2025-01-10 09:00", "E001", "1001", "Completada"},
            {"T002", "Retiro", 500.00, "2025-01-11 14:30", "E002", "1001", "Completada"},
            {"T003", "Depósito", 2500.00, "2025-01-12 11:15", "E001", "1002", "Completada"},
            {"T004", "Depósito", 1500.00, "2025-01-13 16:45", "E003", "1003", "Completada"},
            {"T005", "Retiro", 1000.00, "2025-01-14 10:20", "E002", "1002", "Completada"},
            {"T006", "Depósito", 3000.00, "2025-01-15 13:45", "E001", "1004", "Completada"},
            {"T007", "Retiro", 750.00, "2025-01-16 15:30", "E003", "1003", "Completada"},
            {"T008", "Depósito", 2000.00, "2025-01-17 11:00", "E002", "1005", "Completada"},
            {"T009", "Retiro", 1200.00, "2025-01-18 16:15", "E001", "1004", "Completada"},
            {"T010", "Depósito", 5000.00, "2025-01-19 09:45", "E003", "1006", "Completada"}
        };
        
        for (Object[] fila : datos) {
            tableModel.addRow(fila);
        }
    }
    
    private void exportarCSV() {
        JOptionPane.showMessageDialog(this,
            "Función de exportación a CSV no implementada aún.\n" +
            "Se implementará en la próxima versión.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
}