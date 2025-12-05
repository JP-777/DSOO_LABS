package GUI;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Usuario;
import GUI.admin.*;  // IMPORT AÑADIDO
import GUI.operations.*;  // IMPORT AÑADIDO
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {
    private BancoService bancoService;
    private Usuario usuarioActual;
    private JTabbedPane tabbedPane;
    
    // Paneles para cada tipo de usuario
    private JPanel adminPanel;
    private JPanel empleadoPanel;
    private JPanel clientePanel;
    
    public MainMenuFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        this.usuarioActual = bancoService.getUsuarioActual();
        initComponents();
        setupFrame();
        cargarPanelesSegunRol();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario UNSA - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel de cabecera
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Información del usuario
        JLabel lblUserInfo = new JLabel();
        lblUserInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblUserInfo.setForeground(Color.WHITE);
        lblUserInfo.setText("Usuario: " + usuarioActual.getNombreUsuario() + 
                           " | Rol: " + usuarioActual.getTipo());
        
        // Botón de cerrar sesión
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setBackground(new Color(220, 80, 80));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> cerrarSesion());
        
        headerPanel.add(lblUserInfo, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);
        
        // TabbedPane para los diferentes módulos
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Crear los paneles
        adminPanel = crearPanelAdministrador();
        empleadoPanel = crearPanelEmpleado();
        clientePanel = crearPanelCliente();
        
        // Panel de pie
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel lblFooter = new JLabel("Sistema Bancario UNSA © 2025 - Desarrollado por el Grupo de Laboratorio");
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 11));
        footerPanel.add(lblFooter);
        
        // Ensamblar
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarPanelesSegunRol() {
        String tipoUsuario = usuarioActual.getTipo();
        
        switch (tipoUsuario) {
            case "ADMINISTRADOR":
                tabbedPane.addTab("Administración", adminPanel);
                tabbedPane.addTab("Gestión Empleados", crearPanelGestionEmpleados());
                tabbedPane.addTab("Gestión Clientes", crearPanelGestionClientes());
                tabbedPane.addTab("Gestión Cuentas", crearPanelGestionCuentas());
                tabbedPane.addTab("Operaciones", empleadoPanel);
                tabbedPane.addTab("Reportes", crearPanelReportes());
                break;
                
            case "EMPLEADO":
                tabbedPane.addTab("Clientes", crearPanelGestionClientes());
                tabbedPane.addTab("Cuentas", crearPanelGestionCuentas());
                tabbedPane.addTab("Operaciones", empleadoPanel);
                break;
                
            case "CLIENTE":
                tabbedPane.addTab("Mis Operaciones", clientePanel);
                tabbedPane.addTab("Mis Cuentas", crearPanelMisCuentas());
                break;
        }
    }
    
    private JPanel crearPanelAdministrador() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        // Botones para administrador
        JButton[] botones = {
            crearBoton("Dashboard", "Ver estadísticas del sistema", e -> mostrarDashboard()),
            crearBoton("Usuarios Sistema", "Gestionar usuarios", e -> gestionarUsuariosSistema()),
            crearBoton("Configuración", "Configurar sistema", e -> mostrarConfiguracion()),
            crearBoton("Auditoría", "Ver registros de auditoría", e -> mostrarAuditoria()),
            crearBoton("Backup", "Realizar copia de seguridad", e -> realizarBackup()),
            crearBoton("Reportes Avanzados", "Reportes detallados", e -> mostrarReportesAvanzados())
        };
        
        for (JButton btn : botones) {
            panel.add(btn);
        }
        
        return panel;
    }
    
    private JPanel crearPanelEmpleado() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        // Botones para empleado - CORREGIDOS
        JButton btnDeposito = crearBoton("Depósito", "Realizar depósito a cuenta", 
            e -> abrirVentanaDeposito());
        
        JButton btnRetiro = crearBoton("Retiro", "Realizar retiro de cuenta", 
            e -> abrirVentanaRetiro());
        
        JButton btnConsultaSaldo = crearBoton("Consultar Saldo", "Consultar saldo de cuenta", 
            e -> abrirVentanaConsultaSaldo());
        
        JButton btnMovimientos = crearBoton("Movimientos", "Ver movimientos de cuenta", 
            e -> abrirVentanaMovimientos());
        
        panel.add(btnDeposito);
        panel.add(btnRetiro);
        panel.add(btnConsultaSaldo);
        panel.add(btnMovimientos);
        
        return panel;
    }
    
    private JPanel crearPanelCliente() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        // Botones para cliente - CORREGIDOS
        JButton btnMiSaldo = crearBoton("Mi Saldo", "Consultar mi saldo", 
            e -> abrirVentanaConsultaSaldo());  // Usa la misma que empleado
        
        JButton btnDepositar = crearBoton("Depositar", "Realizar depósito", 
            e -> abrirVentanaDeposito());  // Usa la misma que empleado
        
        JButton btnRetirar = crearBoton("Retirar", "Realizar retiro", 
            e -> abrirVentanaRetiro());  // Usa la misma que empleado
        
        JButton btnMisMovimientos = crearBoton("Mis Movimientos", "Ver mis movimientos", 
            e -> abrirVentanaMovimientos());  // Usa la misma que empleado
        
        panel.add(btnMiSaldo);
        panel.add(btnDepositar);
        panel.add(btnRetirar);
        panel.add(btnMisMovimientos);
        
        return panel;
    }
    
    private JPanel crearPanelGestionClientes() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnListarClientes = crearBoton("Listar Clientes", "Ver todos los clientes", 
            e -> listarClientes());
        
        JButton btnRegistrarCliente = crearBoton("Registrar Cliente", "Agregar nuevo cliente", 
            e -> registrarCliente());
        
        JButton btnBuscarCliente = crearBoton("Buscar Cliente", "Buscar cliente por ID", 
            e -> buscarCliente());
        
        JButton btnEliminarCliente = crearBoton("Eliminar Cliente", "Eliminar cliente", 
            e -> eliminarCliente());
        
        panel.add(btnListarClientes);
        panel.add(btnRegistrarCliente);
        panel.add(btnBuscarCliente);
        
        // Solo administrador puede eliminar clientes
        if (usuarioActual.getTipo().equals("ADMINISTRADOR")) {
            panel.add(btnEliminarCliente);
        }
        
        return panel;
    }
    
    private JPanel crearPanelGestionEmpleados() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnListarEmpleados = crearBoton("Listar Empleados", "Ver todos los empleados", 
            e -> listarEmpleados());
        
        JButton btnRegistrarEmpleado = crearBoton("Registrar Empleado", "Agregar nuevo empleado", 
            e -> registrarEmpleado());
        
        JButton btnBuscarEmpleado = crearBoton("Buscar Empleado", "Buscar empleado por ID", 
            e -> buscarEmpleado());
        
        panel.add(btnListarEmpleados);
        panel.add(btnRegistrarEmpleado);
        panel.add(btnBuscarEmpleado);
        
        return panel;
    }
    
    private JPanel crearPanelGestionCuentas() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnListarCuentas = crearBoton("Listar Cuentas", "Ver todas las cuentas", 
            e -> listarCuentas());
        
        JButton btnRegistrarCuenta = crearBoton("Registrar Cuenta", "Agregar nueva cuenta", 
            e -> registrarCuenta());
        
        JButton btnBuscarCuenta = crearBoton("Buscar Cuenta", "Buscar cuenta por número", 
            e -> buscarCuenta());
        
        JButton btnEliminarCuenta = crearBoton("Eliminar Cuenta", "Eliminar cuenta", 
            e -> eliminarCuenta());
        
        panel.add(btnListarCuentas);
        panel.add(btnRegistrarCuenta);
        panel.add(btnBuscarCuenta);
        
        if (usuarioActual.getTipo().equals("ADMINISTRADOR")) {
            panel.add(btnEliminarCuenta);
        }
        
        return panel;
    }
    
    private JPanel crearPanelMisCuentas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Aquí iría una tabla con las cuentas del cliente
        String[] columnas = {"Número", "Tipo", "Saldo", "Fecha Apertura"};
        Object[][] datos = {}; // Se llenaría con datos reales
        
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        panel.add(new JLabel("Mis Cuentas:", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnTransacciones = crearBoton("Transacciones", "Ver todas las transacciones", 
            e -> verTodasTransacciones());
        
        JButton btnEstadisticas = crearBoton("Estadísticas", "Ver estadísticas", 
            e -> mostrarEstadisticas());
        
        JButton btnReporteClientes = crearBoton("Reporte Clientes", "Reporte de clientes", 
            e -> generarReporteClientes());
        
        JButton btnReporteCuentas = crearBoton("Reporte Cuentas", "Reporte de cuentas", 
            e -> generarReporteCuentas());
        
        panel.add(btnTransacciones);
        panel.add(btnEstadisticas);
        panel.add(btnReporteClientes);
        panel.add(btnReporteCuentas);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, String tooltip, ActionListener listener) {
        JButton button = new JButton(texto);
        button.setToolTipText(tooltip);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.addActionListener(listener);
        
        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 110, 160));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
    
    // ========== MÉTODOS CORREGIDOS PARA ABRIR VENTANAS REALES ==========
    
    private void abrirVentanaDeposito() {
       new DepositFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaRetiro() {
      new WithdrawalFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaConsultaSaldo() {
       new BalanceQueryFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaMovimientos() {
        new TransactionHistoryFrame(bancoService).setVisible(true);
    }
    
    private void listarClientes() {
        new ClientManagementFrame(bancoService).setVisible(true);
    }
    
    private void registrarCliente() {
        // Abre la ventana de gestión de clientes
        new ClientManagementFrame(bancoService).setVisible(true);
    }
    
    private void buscarCliente() {
        // Podría abrir un diálogo de búsqueda o la ventana de gestión
        new ClientManagementFrame(bancoService).setVisible(true);
    }
    
    private void eliminarCliente() {
        new ClientManagementFrame(bancoService).setVisible(true);
    }
    
    private void listarEmpleados() {
        new EmployeeManagementFrame(bancoService).setVisible(true);
    }
    
    private void registrarEmpleado() {
        new EmployeeManagementFrame(bancoService).setVisible(true);
    }
    
    private void buscarEmpleado() {
        new EmployeeManagementFrame(bancoService).setVisible(true);
    }
    
    private void listarCuentas() {
        new AccountManagementFrame(bancoService).setVisible(true);
    }
    
    private void registrarCuenta() {
        new AccountManagementFrame(bancoService).setVisible(true);
    }
    
    private void buscarCuenta() {
        new AccountManagementFrame(bancoService).setVisible(true);
    }
    
    private void eliminarCuenta() {
        new AccountManagementFrame(bancoService).setVisible(true);
    }
    
    private void verTodasTransacciones() {
        new AllTransactionsFrame(bancoService).setVisible(true);
    }
    
    private void gestionarUsuariosSistema() {
        new SystemUsersFrame(bancoService).setVisible(true);
    }
    
    // ========== MÉTODOS QUE SIGUEN EN DESARROLLO ==========
    
    private void abrirVentanaMiSaldo() {
        // Mismo que consulta saldo
        new BalanceQueryFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaDepositoCliente() {
        // Mismo que depósito normal
        new DepositFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaRetiroCliente() {
        // Mismo que retiro normal
        new WithdrawalFrame(bancoService).setVisible(true);
    }
    
    private void abrirVentanaMisMovimientos() {
        // Mismo que movimientos
        new TransactionHistoryFrame(bancoService).setVisible(true);
    }
    
    private void mostrarDashboard() {
        JOptionPane.showMessageDialog(this, "Mostrando dashboard...");
    }
    
    private void mostrarConfiguracion() {
        JOptionPane.showMessageDialog(this, "Mostrando configuración...");
    }
    
    private void mostrarAuditoria() {
        JOptionPane.showMessageDialog(this, "Mostrando auditoría...");
    }
    
    private void realizarBackup() {
        JOptionPane.showMessageDialog(this, "Realizando backup...");
    }
    
    private void mostrarReportesAvanzados() {
        JOptionPane.showMessageDialog(this, "Mostrando reportes avanzados...");
    }
    
    private void mostrarEstadisticas() {
        JOptionPane.showMessageDialog(this, "Mostrando estadísticas...");
    }
    
    private void generarReporteClientes() {
        JOptionPane.showMessageDialog(this, "Generando reporte clientes...");
    }
    
    private void generarReporteCuentas() {
        JOptionPane.showMessageDialog(this, "Generando reporte cuentas...");
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                loginFrame.setLocationRelativeTo(null);
            });
        }
    }
}