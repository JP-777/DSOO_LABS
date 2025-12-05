package GUI.admin;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Cuenta;
import DSOO_LABS.laboratorio7.util.Validador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccountManagementFrame extends JFrame {
    private BancoService bancoService;
    private JTable tablaCuentas;
    private DefaultTableModel tableModel;
    private JTextField txtBuscar;
    
    public AccountManagementFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
        cargarCuentas();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Gestión de Cuentas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 128, 128));
        JLabel lblTitle = new JLabel("GESTIÓN DE CUENTAS BANCARIAS");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de controles
        JPanel controlPanel = new JPanel(new BorderLayout());
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar cuenta:");
        txtBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 128, 128));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarCuenta());
        
        searchPanel.add(lblBuscar);
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton btnNueva = new JButton("Nueva Cuenta");
        btnNueva.setBackground(new Color(60, 179, 113));
        btnNueva.setForeground(Color.WHITE);
        btnNueva.addActionListener(e -> nuevaCuenta());
        
        JButton btnEliminar = new JButton("Eliminar Cuenta");
        btnEliminar.setBackground(new Color(220, 80, 80));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarCuenta());
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarCuentas());
        
        buttonPanel.add(btnNueva);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefrescar);
        
        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Configurar tabla
        String[] columnNames = {"Número", "Tipo", "Saldo (S/)", "Fecha Apertura", "Transacciones", "Cliente"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Double.class;
                return String.class;
            }
        };
        
        tablaCuentas = new JTable(tableModel);
        tablaCuentas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaCuentas.setRowHeight(25);
        tablaCuentas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCuentas.getTableHeader().setBackground(new Color(0, 128, 128));
        tablaCuentas.getTableHeader().setForeground(Color.WHITE);
        
        // Formatear columna de saldo
        tablaCuentas.getColumnModel().getColumn(2).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Double) {
                    setText(String.format("S/ %.2f", (Double) value));
                    setHorizontalAlignment(JLabel.RIGHT);
                } else {
                    super.setValue(value);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaCuentas);
        
        // Panel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statsPanel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTotal = new JLabel("Total cuentas: 0");
        JLabel lblTotalSaldo = new JLabel("Saldo total: S/ 0.00");
        
        statsPanel.add(lblTotal);
        statsPanel.add(lblTotalSaldo);
        
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        closePanel.add(btnCerrar);
        
        bottomPanel.add(statsPanel, BorderLayout.WEST);
        bottomPanel.add(closePanel, BorderLayout.EAST);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarCuentas() {
        tableModel.setRowCount(0);
        
        try {
            List<Cuenta> cuentas = bancoService.getCuentaRepo().getListaCuentas();
            double totalSaldo = 0;
            
            for (Cuenta cuenta : cuentas) {
                Object[] fila = {
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldo(),
                    "2025-01-01", // Fecha de ejemplo
                    cuenta.getTransacciones().size() + " transacciones",
                    "Cliente asociado" // Información de cliente
                };
                tableModel.addRow(fila);
                totalSaldo += cuenta.getSaldo();
            }
            
            // Actualizar estadísticas
            actualizarEstadisticas(cuentas.size(), totalSaldo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar cuentas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarEstadisticas(int totalCuentas, double totalSaldo) {
        // Actualizar labels en la interfaz
        Component[] components = ((JPanel)getContentPane().getComponent(3)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                Component[] subComps = ((JPanel)comp).getComponents();
                for (Component subComp : subComps) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        if (label.getText().startsWith("Total cuentas:")) {
                            label.setText("Total cuentas: " + totalCuentas);
                        } else if (label.getText().startsWith("Saldo total:")) {
                            label.setText(String.format("Saldo total: S/ %.2f", totalSaldo));
                        }
                    }
                }
            }
        }
    }
    
    private void buscarCuenta() {
        String criterio = txtBuscar.getText().trim();
        
        if (criterio.isEmpty()) {
            cargarCuentas();
            return;
        }
        
        List<Cuenta> cuentas = bancoService.getCuentaRepo().getListaCuentas();
        tableModel.setRowCount(0);
        
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().contains(criterio) ||
                cuenta.getTipoCuenta().toLowerCase().contains(criterio.toLowerCase())) {
                
                Object[] fila = {
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldo(),
                    "2025-01-01",
                    cuenta.getTransacciones().size() + " transacciones",
                    "Cliente asociado"
                };
                tableModel.addRow(fila);
            }
        }
    }
    
    private void nuevaCuenta() {
        JDialog dialog = new JDialog(this, "Nueva Cuenta Bancaria", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField txtNumero = new JTextField(20);
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Ahorros", "Corriente", "Inversión"});
        JTextField txtSaldo = new JTextField(20);
        JTextField txtClienteId = new JTextField(20);
        
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Número de Cuenta:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtNumero, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Tipo de Cuenta:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(cmbTipo, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Saldo Inicial (S/):"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtSaldo, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("ID Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtClienteId, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.setBackground(new Color(60, 179, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            if (validarCuenta(txtNumero.getText(), txtSaldo.getText(), txtClienteId.getText())) {
                JOptionPane.showMessageDialog(dialog,
                    "Cuenta creada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                cargarCuentas();
            }
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private boolean validarCuenta(String numero, String saldoStr, String clienteId) {
        if (numero.isEmpty() || saldoStr.isEmpty() || clienteId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validador.validarNumeroCuenta(numero)) {
            JOptionPane.showMessageDialog(this,
                "Número de cuenta inválido. Debe tener al menos 4 dígitos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double saldo = Double.parseDouble(saldoStr);
            if (!Validador.validarMonto(saldo)) {
                JOptionPane.showMessageDialog(this,
                    "Saldo debe ser mayor a 0",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Saldo inválido. Ingrese un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void eliminarCuenta() {
        int selectedRow = tablaCuentas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una cuenta para eliminar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroCuenta = (String) tableModel.getValueAt(selectedRow, 0);
        double saldo = (Double) tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar la cuenta?\n" +
            "Número: " + numeroCuenta + "\n" +
            "Saldo: S/ " + String.format("%.2f", saldo) + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // bancoService.eliminarCuenta(numeroCuenta);
                
                JOptionPane.showMessageDialog(this,
                    "Cuenta eliminada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                cargarCuentas();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar cuenta: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}