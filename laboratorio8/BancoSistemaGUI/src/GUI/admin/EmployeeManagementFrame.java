package GUI.admin;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Empleado;
import DSOO_LABS.laboratorio7.util.Validador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementFrame extends JFrame {
    private BancoService bancoService;
    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;
    private JTextField txtBuscar;
    
    public EmployeeManagementFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
        cargarEmpleados();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Gestión de Empleados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(139, 0, 139));
        JLabel lblTitle = new JLabel("GESTIÓN DE EMPLEADOS");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de búsqueda y botones
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar empleado:");
        txtBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(139, 0, 139));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarEmpleado());
        
        searchPanel.add(lblBuscar);
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton btnNuevo = new JButton("Nuevo Empleado");
        btnNuevo.setBackground(new Color(60, 179, 113));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.addActionListener(e -> nuevoEmpleado());
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarEmpleados());
        
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnRefrescar);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Configurar tabla
        String[] columnNames = {"ID", "DNI", "Nombre", "Apellido", "Cargo", "Teléfono", "Dirección"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaEmpleados.setRowHeight(25);
        tablaEmpleados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEmpleados.getTableHeader().setBackground(new Color(139, 0, 139));
        tablaEmpleados.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        
        // Panel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        bottomPanel.add(btnCerrar);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarEmpleados() {
        tableModel.setRowCount(0);
        
        try {
            List<Empleado> empleados = bancoService.getEmpleadoRepo().getListaEmpleados();
            
            for (Empleado emp : empleados) {
                Object[] fila = {
                    emp.getIdEmpleado(),
                    emp.getDni(),
                    emp.getNombre(),
                    emp.getApellido(),
                    emp.getCargo(),
                    emp.getTelefono(),
                    emp.getDireccion()
                };
                tableModel.addRow(fila);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar empleados: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarEmpleado() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        
        if (criterio.isEmpty()) {
            cargarEmpleados();
            return;
        }
        
        List<Empleado> empleados = bancoService.getEmpleadoRepo().getListaEmpleados();
        tableModel.setRowCount(0);
        
        for (Empleado emp : empleados) {
            if (emp.getIdEmpleado().toLowerCase().contains(criterio) ||
                emp.getDni().toLowerCase().contains(criterio) ||
                emp.getNombre().toLowerCase().contains(criterio) ||
                emp.getApellido().toLowerCase().contains(criterio) ||
                emp.getCargo().toLowerCase().contains(criterio)) {
                
                Object[] fila = {
                    emp.getIdEmpleado(),
                    emp.getDni(),
                    emp.getNombre(),
                    emp.getApellido(),
                    emp.getCargo(),
                    emp.getTelefono(),
                    emp.getDireccion()
                };
                tableModel.addRow(fila);
            }
        }
    }
    
    private void nuevoEmpleado() {
        JDialog dialog = new JDialog(this, "Nuevo Empleado", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos del formulario
        JTextField txtId = new JTextField(20);
        JTextField txtDni = new JTextField(20);
        JTextField txtNombre = new JTextField(20);
        JTextField txtApellido = new JTextField(20);
        JTextField txtCargo = new JTextField(20);
        JTextField txtDireccion = new JTextField(20);
        JTextField txtTelefono = new JTextField(20);
        
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("ID Empleado:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtDni, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtApellido, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtCargo, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtDireccion, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(txtTelefono, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.setBackground(new Color(60, 179, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            if (validarEmpleado(txtId.getText(), txtDni.getText(), txtNombre.getText(),
                               txtApellido.getText(), txtCargo.getText(),
                               txtDireccion.getText(), txtTelefono.getText())) {
                // Lógica para guardar empleado
                JOptionPane.showMessageDialog(dialog,
                    "Empleado guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                cargarEmpleados();
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
    
    private boolean validarEmpleado(String id, String dni, String nombre, String apellido,
                                   String cargo, String direccion, String telefono) {
        if (id.isEmpty() || dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
            cargo.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validador.validarDNI(dni)) {
            JOptionPane.showMessageDialog(this,
                "DNI inválido. Debe tener 8 dígitos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validador.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this,
                "Teléfono inválido. Debe tener 9 dígitos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
}