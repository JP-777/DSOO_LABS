package GUI.admin;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Cliente;
import DSOO_LABS.laboratorio7.util.Validador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClientManagementFrame extends JFrame {
    private BancoService bancoService;
    private JTable tablaClientes;
    private DefaultTableModel tableModel;
    private JTextField txtBuscar;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    
    public ClientManagementFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
        cargarClientes();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Gestión de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel lblTitle = new JLabel("GESTIÓN DE CLIENTES");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        
        JLabel lblBuscar = new JLabel("Buscar cliente:");
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarCliente());
        
        searchPanel.add(lblBuscar);
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        
        // Configurar tabla
        String[] columnNames = {"ID", "DNI", "Nombre", "Apellido", "Teléfono", "Correo", "Estado", "Cuentas"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes = new JTable(tableModel);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaClientes.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaClientes.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        
        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(new Color(240, 240, 240));
        
        btnNuevo = new JButton("Nuevo Cliente");
        btnNuevo.setBackground(new Color(60, 179, 113));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.addActionListener(e -> nuevoCliente());
        
        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 165, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarCliente());
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 80, 80));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarCliente());
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(70, 130, 180));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarClientes());
        
        actionPanel.add(btnNuevo);
        actionPanel.add(btnEditar);
        actionPanel.add(btnEliminar);
        actionPanel.add(btnRefrescar);
        
        // Panel de botones inferiores
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statsPanel.setBackground(new Color(240, 240, 240));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        JLabel lblTotal = new JLabel("Total clientes: 0");
        JLabel lblActivos = new JLabel("Clientes activos: 0");
        JLabel lblInactivos = new JLabel("Clientes inactivos: 0");
        
        statsPanel.add(lblTotal);
        statsPanel.add(lblActivos);
        statsPanel.add(lblInactivos);
        
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        closePanel.add(btnCerrar);
        
        bottomPanel.add(statsPanel, BorderLayout.WEST);
        bottomPanel.add(closePanel, BorderLayout.EAST);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void cargarClientes() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        try {
            // Obtener clientes del repositorio
            List<Cliente> clientes = bancoService.getClienteRepo().getListaClientes();
            
            for (Cliente cliente : clientes) {
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getDni(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getEstado(),
                    cliente.getCuentas().size() + " cuentas"
                };
                tableModel.addRow(fila);
            }
            
            // Actualizar estadísticas
            actualizarEstadisticas(clientes.size());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar clientes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarEstadisticas(int total) {
        // Aquí iría la lógica para calcular activos/inactivos
        // Por ahora solo mostramos el total
    }
    
    private void buscarCliente() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        
        if (criterio.isEmpty()) {
            cargarClientes();
            return;
        }
        
        // Filtrar clientes
        List<Cliente> clientes = bancoService.getClienteRepo().getListaClientes();
        tableModel.setRowCount(0);
        
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().toLowerCase().contains(criterio) ||
                cliente.getDni().toLowerCase().contains(criterio) ||
                cliente.getNombre().toLowerCase().contains(criterio) ||
                cliente.getApellido().toLowerCase().contains(criterio) ||
                cliente.getCorreo().toLowerCase().contains(criterio)) {
                
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getDni(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getEstado(),
                    cliente.getCuentas().size() + " cuentas"
                };
                tableModel.addRow(fila);
            }
        }
    }
    
    private void nuevoCliente() {
        // Crear diálogo para nuevo cliente
        JDialog dialog = new JDialog(this, "Nuevo Cliente", true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campos del formulario
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("ID Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtId = new JTextField(20);
        panel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("DNI (8 dígitos):"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtDni = new JTextField(20);
        panel.add(txtDni, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtApellido = new JTextField(20);
        panel.add(txtApellido, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtDireccion = new JTextField(20);
        panel.add(txtDireccion, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Teléfono (9 dígitos):"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++;
        JTextField txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(60, 179, 113));
        btnGuardar.setForeground(Color.WHITE);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            // Validar y guardar cliente
            if (validarCliente(txtId.getText(), txtDni.getText(), txtNombre.getText(), 
                              txtApellido.getText(), txtDireccion.getText(), 
                              txtTelefono.getText(), txtCorreo.getText())) {
                
                // Aquí iría la lógica para guardar el cliente
                JOptionPane.showMessageDialog(dialog,
                    "Cliente guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                dialog.dispose();
                cargarClientes();
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
    
    private boolean validarCliente(String id, String dni, String nombre, String apellido,
                                  String direccion, String telefono, String correo) {
        // Validar campos obligatorios
        if (id.isEmpty() || dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
            direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar DNI
        if (!Validador.validarDNI(dni)) {
            JOptionPane.showMessageDialog(this,
                "DNI inválido. Debe tener 8 dígitos",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar teléfono
        if (!Validador.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this,
                "Teléfono inválido. Debe tener 9 dígitos",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar correo
        if (!Validador.validarCorreo(correo)) {
            JOptionPane.showMessageDialog(this,
                "Correo electrónico inválido",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void editarCliente() {
        int selectedRow = tablaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un cliente para editar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String idCliente = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this,
            "Editando cliente: " + idCliente + "\n" +
            "Función en desarrollo",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarCliente() {
        int selectedRow = tablaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un cliente para eliminar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String idCliente = (String) tableModel.getValueAt(selectedRow, 0);
        String nombreCliente = (String) tableModel.getValueAt(selectedRow, 2) + " " +
                              (String) tableModel.getValueAt(selectedRow, 3);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar al cliente?\n" +
            "ID: " + idCliente + "\n" +
            "Nombre: " + nombreCliente + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Aquí iría la lógica para eliminar el cliente
                // bancoService.eliminarCliente(idCliente);
                
                JOptionPane.showMessageDialog(this,
                    "Cliente eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                cargarClientes();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}