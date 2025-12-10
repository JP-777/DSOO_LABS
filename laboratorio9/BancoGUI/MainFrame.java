package DSOO_LABS.laboratorio7.BancoGUI;

import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.util.Validador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
    private BancoService bancoService;
    private Usuario usuarioActual;
    private String rol;
    
    // Componentes
    private JTable tablaClientes, tablaEmpleados, tablaCuentas, tablaTransacciones;
    private DefaultTableModel modeloClientes, modeloEmpleados, modeloCuentas, modeloTransacciones;
    
    public MainFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        this.usuarioActual = bancoService.getUsuarioActual();
        this.rol = usuarioActual.getTipo();
        
        setTitle("Sistema Bancario - " + usuarioActual.getNombreUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        
        crearInterfaz();
        cargarDatosIniciales();
    }
    
    // ========== MÉTODOS PARA MOSTRAR EN VENTANAS ==========
    
    private void mostrarMensajeEnVentana(String titulo, String contenido) {
        JDialog dialogo = new JDialog(this, titulo, true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);
        
        JTextArea areaTexto = new JTextArea(contenido);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaTexto.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnCopiar = new JButton("📋 Copiar");
        btnCopiar.addActionListener(e -> {
            areaTexto.selectAll();
            areaTexto.copy();
            JOptionPane.showMessageDialog(dialogo, "Texto copiado al portapapeles");
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCopiar);
        panelBotones.add(btnCerrar);
        
        dialogo.setLayout(new BorderLayout());
        dialogo.add(scrollPane, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void mostrarSaldoEnVentana(String numeroCuenta, Cuenta cuenta) {
        JDialog dialogo = new JDialog(this, "Consulta de Saldo", true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(0, 1, 10, 10));
        
        JLabel lblTitulo = new JLabel("💵 SALDO DE CUENTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 100, 0));
        
        JLabel lblNumero = new JLabel("Número: " + cuenta.getNumeroCuenta());
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblTipo = new JLabel("Tipo: " + cuenta.getTipoCuenta());
        
        JLabel lblSaldo = new JLabel("💰 SALDO ACTUAL");
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        lblSaldo.setForeground(new Color(0, 0, 150));
        lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblMonto = new JLabel("S/ " + String.format("%.2f", cuenta.getSaldo()));
        lblMonto.setFont(new Font("Arial", Font.BOLD, 24));
        lblMonto.setForeground(new Color(0, 100, 0));
        lblMonto.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblFecha = new JLabel("Consulta: " + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lblFecha.setFont(new Font("Arial", Font.ITALIC, 10));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelInfo.add(lblTitulo);
        panelInfo.add(lblNumero);
        panelInfo.add(lblTipo);
        panelInfo.add(new JSeparator());
        panelInfo.add(lblSaldo);
        panelInfo.add(lblMonto);
        panelInfo.add(new JSeparator());
        panelInfo.add(lblFecha);
        
        // Botones
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnMovimientos = new JButton("📊 Ver Movimientos");
        btnMovimientos.addActionListener(e -> {
            dialogo.dispose();
            verMovimientosCuenta(cuenta.getNumeroCuenta());
        });
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.add(btnMovimientos);
        panelBotones.add(btnCerrar);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void verMovimientosCuenta(String numeroCuenta) {
        // Primero verificar que la cuenta existe
        Cuenta cuenta = bancoService.getCuentaDAO().buscarPorNumero(numeroCuenta);
        
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this, "Cuenta no encontrada: " + numeroCuenta);
            return;
        }
        
        // Usar el método del servicio que ya funciona en consola
        bancoService.verMovimientos(numeroCuenta);
        
        // Y además mostrar en una ventana simple
        mostrarMovimientosEnVentanaSimple(numeroCuenta, cuenta);
    }
    
    private void mostrarMovimientosEnVentanaSimple(String numeroCuenta, Cuenta cuenta) {
        JDialog dialogo = new JDialog(this, "Movimientos - Cuenta " + numeroCuenta, true);
        dialogo.setSize(600, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Información básica
        JLabel lblInfo = new JLabel(
            "<html><center><b>Cuenta:</b> " + numeroCuenta + 
            "<br><b>Tipo:</b> " + cuenta.getTipoCuenta() + 
            "<br><b>Saldo actual:</b> S/ " + String.format("%.2f", cuenta.getSaldo()) + 
            "</center></html>", SwingConstants.CENTER);
        
        // Área de texto para mostrar movimientos
        JTextArea areaMovimientos = new JTextArea();
        areaMovimientos.setEditable(false);
        areaMovimientos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Obtener transacciones de la BD (si existe el método)
        try {
            // Intenta obtener transacciones reales
            List<Transaccion> transacciones = bancoService.getTransaccionDAO().obtenerTransaccionesPorCuenta(numeroCuenta);
            
            if (transacciones != null && !transacciones.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("=== MOVIMIENTOS DE LA CUENTA ").append(numeroCuenta).append(" ===\n\n");
                
                for (Transaccion t : transacciones) {
                    String tipo = t.getClass().getSimpleName();
                    sb.append("• ").append(tipo)
                      .append(" | Monto: S/ ").append(String.format("%.2f", t.getMonto()))
                      .append(" | Fecha: ").append(t.getFecha())
                      .append("\n");
                }
                
                areaMovimientos.setText(sb.toString());
            } else {
                areaMovimientos.setText("No hay movimientos registrados en esta cuenta.\n\n" +
                    "Puedes realizar depósitos o retiros para generar movimientos.");
            }
        } catch (Exception e) {
            // Si hay error, mostrar mensaje de consola
            areaMovimientos.setText("Información de movimientos:\n" +
                "Consulta realizada: " + LocalDateTime.now() + "\n\n" +
                "La cuenta tiene " + cuenta.getSaldo() + " en saldo.\n" +
                "Para ver el historial completo, revisa la consola del sistema.");
        }
        
        JScrollPane scrollPane = new JScrollPane(areaMovimientos);
        
        // Botones
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnConsola = new JButton("📟 Ver en Consola");
        btnConsola.addActionListener(e -> {
            // Mostrar también en consola
            System.out.println("\n=== MOVIMIENTOS DE LA CUENTA " + numeroCuenta + " ===");
            System.out.println("Saldo actual: S/ " + String.format("%.2f", cuenta.getSaldo()));
            System.out.println("Consulta: " + LocalDateTime.now());
            System.out.println("=============================================\n");
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConsola);
        panelBotones.add(btnCerrar);
        
        panel.add(lblInfo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void mostrarClienteEnVentana(Cliente cliente) {
        JDialog dialogo = new JDialog(this, "Información del Cliente", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(0, 2, 10, 10));
        
        JLabel lblTitulo = new JLabel("👤 INFORMACIÓN DEL CLIENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 51, 102));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        panelInfo.add(new JLabel("ID Cliente:"));
        panelInfo.add(new JLabel(cliente.getIdCliente()));
        
        panelInfo.add(new JLabel("Nombre:"));
        panelInfo.add(new JLabel(cliente.getNombre()));
        
        panelInfo.add(new JLabel("Apellido:"));
        panelInfo.add(new JLabel(cliente.getApellido()));
        
        panelInfo.add(new JLabel("DNI:"));
        panelInfo.add(new JLabel(cliente.getDni()));
        
        panelInfo.add(new JLabel("Correo:"));
        panelInfo.add(new JLabel(cliente.getCorreo()));
        
        panelInfo.add(new JLabel("Teléfono:"));
        panelInfo.add(new JLabel(cliente.getTelefono()));
        
        panelInfo.add(new JLabel("Dirección:"));
        panelInfo.add(new JLabel(cliente.getDireccion()));
        
        panelInfo.add(new JLabel("Estado:"));
        JLabel lblEstado = new JLabel(cliente.getEstado());
        if (cliente.getEstado().equals("Activo")) {
            lblEstado.setForeground(new Color(0, 150, 0));
        } else {
            lblEstado.setForeground(Color.RED);
        }
        panelInfo.add(lblEstado);
        
        // Botones
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnVerCuentas = new JButton("💰 Ver Cuentas");
        btnVerCuentas.addActionListener(e -> {
            dialogo.dispose();
            mostrarCuentasCliente(cliente);
        });
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.add(btnVerCuentas);
        panelBotones.add(btnCerrar);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void mostrarCuentasCliente(Cliente cliente) {
        JDialog dialogo = new JDialog(this, "Cuentas de " + cliente.getNombre(), true);
        dialogo.setSize(600, 400);
        dialogo.setLocationRelativeTo(this);
        
        // Obtener cuentas del cliente
        List<Cuenta> cuentas = bancoService.getTitularidadDAO().obtenerCuentasPorCliente(cliente.getIdCliente());
        
        if (cuentas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El cliente " + cliente.getNombre() + " no tiene cuentas asociadas.");
            return;
        }
        
        String[] columnas = {"Número", "Tipo", "Saldo", "Estado", "Fecha Apertura"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        for (Cuenta c : cuentas) {
            modelo.addRow(new Object[]{
                c.getNumeroCuenta(),
                c.getTipoCuenta(),
                "S/ " + String.format("%.2f", c.getSaldo()),
                "ACTIVA",
                "2024-01-01" // Ejemplo
            });
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Resumen
        double totalSaldo = cuentas.stream().mapToDouble(Cuenta::getSaldo).sum();
        JLabel lblResumen = new JLabel(
            "<html><b>Resumen:</b> " + cuentas.size() + " cuenta(s) | " +
            "Saldo total: <font color='green'>S/ " + String.format("%.2f", totalSaldo) + "</font></html>");
        
        // Botones
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnDetalle = new JButton("📊 Ver Detalle");
        btnDetalle.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String numeroCuenta = tabla.getValueAt(fila, 0).toString();
                verMovimientosCuenta(numeroCuenta);
            } else {
                JOptionPane.showMessageDialog(dialogo, "Seleccione una cuenta");
            }
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnDetalle);
        panelBotones.add(btnCerrar);
        
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.add(lblResumen, BorderLayout.NORTH);
        dialogo.add(scrollPane, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void mostrarTransaccionesEnVentana() {
        JDialog dialogo = new JDialog(this, "Historial de Transacciones", true);
        dialogo.setSize(900, 500);
        dialogo.setLocationRelativeTo(this);
        
        String[] columnas = {"ID", "Fecha", "Hora", "Tipo", "Cuenta", "Monto", "Empleado", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        // Datos de ejemplo
        Object[][] datos = {
            {"T001", "15/01/2024", "10:30:45", "DEPÓSITO", "1001", "S/ 500.00", "E001", "Depósito inicial"},
            {"T002", "16/01/2024", "14:15:20", "RETIRO", "1002", "S/ 200.00", "E002", "Retiro en cajero"},
            {"T003", "18/01/2024", "09:45:10", "DEPÓSITO", "1003", "S/ 1000.00", "E001", "Depósito de nómina"},
            {"T004", "20/01/2024", "16:30:55", "TRANSFERENCIA", "1004", "S/ 300.00", "E003", "Transferencia a terceros"},
            {"T005", "22/01/2024", "11:20:30", "DEPÓSITO", "1001", "S/ 150.00", "E002", "Depósito ventanilla"},
            {"T006", "23/01/2024", "13:45:15", "RETIRO", "1002", "S/ 100.00", "E001", "Retiro efectivo"},
            {"T007", "24/01/2024", "15:10:40", "DEPÓSITO", "1005", "S/ 750.00", "E003", "Depósito cheque"}
        };
        
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        
        // Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por:"));
        
        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"Todas", "Depósitos", "Retiros", "Transferencias"});
        JTextField txtBuscar = new JTextField(15);
        JButton btnFiltrar = new JButton("🔍 Filtrar");
        
        panelFiltros.add(comboFiltro);
        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(txtBuscar);
        panelFiltros.add(btnFiltrar);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Estadísticas
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        double totalDepositos = 2400.00;
        double totalRetiros = 300.00;
        int totalTransacciones = 7;
        double promedio = (totalDepositos - totalRetiros) / totalTransacciones;
        
        JLabel lblTotal = new JLabel("<html><b>Total Transacciones:</b><br>" + 
            totalTransacciones + "</html>");
        JLabel lblDepositos = new JLabel("<html><b>Total Depósitos:</b><br>S/ " + 
            String.format("%.2f", totalDepositos) + "</html>");
        JLabel lblRetiros = new JLabel("<html><b>Total Retiros:</b><br>S/ " + 
            String.format("%.2f", totalRetiros) + "</html>");
        JLabel lblPromedio = new JLabel("<html><b>Promedio:</b><br>S/ " + 
            String.format("%.2f", promedio) + "</html>");
        
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblDepositos.setHorizontalAlignment(SwingConstants.CENTER);
        lblRetiros.setHorizontalAlignment(SwingConstants.CENTER);
        lblPromedio.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelStats.add(lblTotal);
        panelStats.add(lblDepositos);
        panelStats.add(lblRetiros);
        panelStats.add(lblPromedio);
        
        // Botones
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        
        JButton btnImprimir = new JButton("🖨️ Imprimir Reporte");
        JButton btnExportar = new JButton("📊 Exportar a Excel");
        JButton btnRefresh = new JButton("🔄 Actualizar");
        
        btnImprimir.addActionListener(e -> 
            JOptionPane.showMessageDialog(dialogo, "Función de impresión en desarrollo"));
        btnExportar.addActionListener(e -> 
            JOptionPane.showMessageDialog(dialogo, "Función de exportación en desarrollo"));
        btnRefresh.addActionListener(e -> JOptionPane.showMessageDialog(dialogo, "Datos actualizados"));
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnImprimir);
        panelBotones.add(btnExportar);
        panelBotones.add(btnRefresh);
        panelBotones.add(btnCerrar);
        
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.add(panelFiltros, BorderLayout.NORTH);
        dialogo.add(scrollPane, BorderLayout.CENTER);
        dialogo.add(panelStats, BorderLayout.SOUTH);
        dialogo.add(panelBotones, BorderLayout.PAGE_END);
        dialogo.setVisible(true);
    }
    
    // ========== INTERFAZ PRINCIPAL ==========
    
    private void crearInterfaz() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña 1: Perfil (para todos)
        tabbedPane.addTab("👤 Perfil", crearPanelPerfil());
        
        // Pestaña 2: Clientes (solo admin y empleado)
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            tabbedPane.addTab("👥 Clientes", crearPanelClientes());
        }
        
        // Pestaña 3: Empleados (solo admin y empleado)
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            tabbedPane.addTab("👔 Empleados", crearPanelEmpleados());
        }
        
        // Pestaña 4: Cuentas (solo admin y empleado)
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            tabbedPane.addTab("💰 Cuentas", crearPanelCuentas());
        }
        
        // Pestaña 5: Operaciones Bancarias (para todos)
        tabbedPane.addTab("💳 Operaciones", crearPanelOperaciones());
        
        // Pestaña 6: Asignaciones (solo admin y empleado)
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            tabbedPane.addTab("🔗 Asignaciones", crearPanelAsignaciones());
        }
        
        add(tabbedPane);
    }
    
    // ========== PANEL PERFIL ==========
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelInfo = new JPanel(new GridLayout(0, 2, 10, 10));
        
        panelInfo.add(new JLabel("Usuario:"));
        panelInfo.add(new JLabel(usuarioActual.getNombreUsuario()));
        
        panelInfo.add(new JLabel("Rol:"));
        panelInfo.add(new JLabel(usuarioActual.getTipo()));
        
        if (rol.equals("CLIENTE")) {
            UsuarioCliente uc = (UsuarioCliente) usuarioActual;
            Cliente cliente = uc.getCliente();
            
            panelInfo.add(new JLabel("ID Cliente:"));
            panelInfo.add(new JLabel(cliente.getIdCliente()));
            
            panelInfo.add(new JLabel("Nombre:"));
            panelInfo.add(new JLabel(cliente.getNombre() + " " + cliente.getApellido()));
            
            panelInfo.add(new JLabel("DNI:"));
            panelInfo.add(new JLabel(cliente.getDni()));
            
            panelInfo.add(new JLabel("Correo:"));
            panelInfo.add(new JLabel(cliente.getCorreo()));
            
            panelInfo.add(new JLabel("Teléfono:"));
            panelInfo.add(new JLabel(cliente.getTelefono()));
            
            panelInfo.add(new JLabel("Dirección:"));
            panelInfo.add(new JLabel(cliente.getDireccion()));
            
        } else if (rol.equals("EMPLEADO")) {
            UsuarioEmpleado ue = (UsuarioEmpleado) usuarioActual;
            Empleado empleado = ue.getEmpleado();
            
            panelInfo.add(new JLabel("ID Empleado:"));
            panelInfo.add(new JLabel(empleado.getIdEmpleado()));
            
            panelInfo.add(new JLabel("Nombre:"));
            panelInfo.add(new JLabel(empleado.getNombre() + " " + empleado.getApellido()));
            
            panelInfo.add(new JLabel("DNI:"));
            panelInfo.add(new JLabel(empleado.getDni()));
            
            panelInfo.add(new JLabel("Cargo:"));
            panelInfo.add(new JLabel(empleado.getCargo()));
            
            panelInfo.add(new JLabel("Teléfono:"));
            panelInfo.add(new JLabel(empleado.getTelefono()));
            
        } else if (rol.equals("ADMINISTRADOR")) {
            panelInfo.add(new JLabel("Departamento:"));
            panelInfo.add(new JLabel("Administración"));
            
            panelInfo.add(new JLabel("Permisos:"));
            panelInfo.add(new JLabel("Acceso completo al sistema"));
        }
        
        panel.add(panelInfo, BorderLayout.CENTER);
        
        JButton btnPermisos = new JButton("📋 Ver Mis Permisos");
        btnPermisos.addActionListener(e -> usuarioActual.mostrarPermisos());
        panel.add(btnPermisos, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ========== PANEL CLIENTES ==========
    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton btnAgregar = new JButton("➕ Agregar Cliente");
        JButton btnBuscar = new JButton("🔍 Buscar Cliente");
        JButton btnRefrescar = new JButton("🔄 Refrescar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnRefrescar);
        
        if (rol.equals("ADMINISTRADOR")) {
            JButton btnEliminar = new JButton("🗑️ Eliminar Cliente");
            panelBotones.add(btnEliminar);
            btnEliminar.addActionListener(e -> eliminarCliente());
        }
        
        btnAgregar.addActionListener(e -> agregarCliente());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnRefrescar.addActionListener(e -> cargarClientes());
        
        String[] columnas = {"ID", "DNI", "Nombre", "Apellido", "Correo", "Teléfono", "Estado"};
        modeloClientes = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes = new JTable(modeloClientes);
        tablaClientes.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ========== PANEL EMPLEADOS ==========
    private JPanel crearPanelEmpleados() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton btnRefrescar = new JButton("🔄 Refrescar");
        panelBotones.add(btnRefrescar);
        
        if (rol.equals("ADMINISTRADOR")) {
            JButton btnAgregar = new JButton("➕ Agregar Empleado");
            panelBotones.add(btnAgregar);
            btnAgregar.addActionListener(e -> agregarEmpleado());
        }
        
        btnRefrescar.addActionListener(e -> cargarEmpleados());
        
        String[] columnas = {"ID", "DNI", "Nombre", "Apellido", "Cargo", "Teléfono"};
        modeloEmpleados = new DefaultTableModel(columnas, 0);
        
        tablaEmpleados = new JTable(modeloEmpleados);
        tablaEmpleados.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ========== PANEL CUENTAS ==========
    private JPanel crearPanelCuentas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton btnAgregar = new JButton("➕ Agregar Cuenta");
        JButton btnRefrescar = new JButton("🔄 Refrescar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnRefrescar);
        
        if (rol.equals("ADMINISTRADOR")) {
            JButton btnEliminar = new JButton("🗑️ Eliminar Cuenta");
            panelBotones.add(btnEliminar);
            btnEliminar.addActionListener(e -> eliminarCuenta());
        }
        
        btnAgregar.addActionListener(e -> agregarCuenta());
        btnRefrescar.addActionListener(e -> cargarCuentas());
        
        String[] columnas = {"Número", "Tipo", "Saldo", "Estado"};
        modeloCuentas = new DefaultTableModel(columnas, 0);
        
        tablaCuentas = new JTable(modeloCuentas);
        tablaCuentas.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaCuentas);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ========== PANEL OPERACIONES ==========
    private JPanel crearPanelOperaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton btnConsultarSaldo = new JButton("💰 Consultar Saldo");
        JButton btnDeposito = new JButton("💵 Realizar Depósito");
        JButton btnRetiro = new JButton("💸 Realizar Retiro");
        JButton btnMovimientos = new JButton("📊 Ver Movimientos");
        
        panelBotones.add(btnConsultarSaldo);
        panelBotones.add(btnDeposito);
        panelBotones.add(btnRetiro);
        panelBotones.add(btnMovimientos);
        
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            JButton btnRefrescar = new JButton("🔄 Refrescar");
            JButton btnListarTransacciones = new JButton("📋 Listar Transacciones");
            
            panelBotones.add(btnRefrescar);
            panelBotones.add(btnListarTransacciones);
            
            btnRefrescar.addActionListener(e -> {
                cargarCuentas();
                if (rol.equals("ADMINISTRADOR")) {
                    cargarTransacciones();
                }
            });
            
            btnListarTransacciones.addActionListener(e -> mostrarTransaccionesEnVentana());
        }
        
        btnConsultarSaldo.addActionListener(e -> consultarSaldo());
        btnDeposito.addActionListener(e -> realizarDeposito());
        btnRetiro.addActionListener(e -> realizarRetiro());
        btnMovimientos.addActionListener(e -> verMovimientos());  // <-- ESTE ES EL MÉTODO CORREGIDO
        
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            String[] columnas = {"ID", "Tipo", "Monto", "Cuenta", "Fecha", "Empleado"};
            modeloTransacciones = new DefaultTableModel(columnas, 0);
            
            tablaTransacciones = new JTable(modeloTransacciones);
            tablaTransacciones.setRowHeight(25);
            JScrollPane scrollPane = new JScrollPane(tablaTransacciones);
            
            panel.add(panelBotones, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
        } else {
            panel.add(panelBotones, BorderLayout.NORTH);
            
            JLabel lblInfo = new JLabel(
                "<html><center><h3>Operaciones Bancarias</h3>" +
                "<p>Puedes realizar consultas y transacciones sobre tus cuentas</p>" +
                "<p>Selecciona una operación del menú superior</p></center></html>",
                SwingConstants.CENTER
            );
            panel.add(lblInfo, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    // ========== PANEL ASIGNACIONES ==========
    private JPanel crearPanelAsignaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        
        JButton btnAsignar = new JButton("🔗 Asignar Cuenta a Cliente");
        JButton btnVerCuentas = new JButton("👥 Ver Cuentas de un Cliente");
        JButton btnRefrescar = new JButton("🔄 Refrescar Datos");
        
        panelBotones.add(btnAsignar);
        panelBotones.add(btnVerCuentas);
        panelBotones.add(btnRefrescar);
        
        JButton btnAyuda = new JButton("❓ Ayuda");
        btnAyuda.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Para asignar una cuenta:\n" +
            "1. Asegúrate de que el cliente exista\n" +
            "2. La cuenta debe estar creada\n" +
            "3. Una cuenta puede tener varios titulares\n" +
            "4. Un cliente puede tener varias cuentas"));
        panelBotones.add(btnAyuda);
        
        btnAsignar.addActionListener(e -> asignarCuentaACliente());
        btnVerCuentas.addActionListener(e -> verCuentasCliente());
        btnRefrescar.addActionListener(e -> {
            cargarClientes();
            cargarCuentas();
        });
        
        panel.add(panelBotones, BorderLayout.NORTH);
        
        JTextArea areaInfo = new JTextArea(
            "Instrucciones:\n" +
            "• Use 'Asignar Cuenta' para vincular una cuenta a un cliente\n" +
            "• Use 'Ver Cuentas' para listar las cuentas de un cliente\n" +
            "• Los clientes pueden tener múltiples cuentas\n" +
            "• Las cuentas pueden tener múltiples titulares"
        );
        areaInfo.setEditable(false);
        areaInfo.setBackground(panel.getBackground());
        panel.add(new JScrollPane(areaInfo), BorderLayout.CENTER);
        
        return panel;
    }
    
    // ========== CARGAR DATOS ==========
    
    private void cargarDatosIniciales() {
        if (rol.equals("ADMINISTRADOR") || rol.equals("EMPLEADO")) {
            cargarClientes();
            if (rol.equals("ADMINISTRADOR")) {
                cargarEmpleados();
            }
            cargarCuentas();
            cargarTransacciones();
        }
    }
    
    private void cargarClientes() {
        if (modeloClientes == null) return;
        
        SwingWorker<List<Cliente>, Void> worker = new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() throws Exception {
                return bancoService.getClienteDAO().listarTodos();
            }
            
            @Override
            protected void done() {
                try {
                    List<Cliente> clientes = get();
                    modeloClientes.setRowCount(0);
                    for (Cliente c : clientes) {
                        modeloClientes.addRow(new Object[]{
                            c.getIdCliente(), c.getDni(), c.getNombre(), c.getApellido(),
                            c.getCorreo(), c.getTelefono(), c.getEstado()
                        });
                    }
                } catch (Exception e) {
                    mostrarError("Error al cargar clientes", e);
                }
            }
        };
        worker.execute();
    }
    
    private void cargarEmpleados() {
        if (modeloEmpleados == null) return;
        
        SwingWorker<List<Empleado>, Void> worker = new SwingWorker<List<Empleado>, Void>() {
            @Override
            protected List<Empleado> doInBackground() throws Exception {
                return bancoService.getEmpleadoDAO().listarTodos();
            }
            
            @Override
            protected void done() {
                try {
                    List<Empleado> empleados = get();
                    modeloEmpleados.setRowCount(0);
                    for (Empleado e : empleados) {
                        modeloEmpleados.addRow(new Object[]{
                            e.getIdEmpleado(), e.getDni(), e.getNombre(), e.getApellido(),
                            e.getCargo(), e.getTelefono()
                        });
                    }
                } catch (Exception ex) {
                    mostrarError("Error al cargar empleados", ex);
                }
            }
        };
        worker.execute();
    }
    
    private void cargarCuentas() {
        if (modeloCuentas == null) return;
        
        SwingWorker<List<Cuenta>, Void> worker = new SwingWorker<List<Cuenta>, Void>() {
            @Override
            protected List<Cuenta> doInBackground() throws Exception {
                return bancoService.getCuentaDAO().listarTodas();
            }
            
            @Override
            protected void done() {
                try {
                    List<Cuenta> cuentas = get();
                    modeloCuentas.setRowCount(0);
                    for (Cuenta c : cuentas) {
                        modeloCuentas.addRow(new Object[]{
                            c.getNumeroCuenta(), c.getTipoCuenta(),
                            String.format("S/ %.2f", c.getSaldo()), "ACTIVA"
                        });
                    }
                } catch (Exception e) {
                    mostrarError("Error al cargar cuentas", e);
                }
            }
        };
        worker.execute();
    }
    
    private void cargarTransacciones() {
        if (modeloTransacciones == null) return;
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                return null;
            }
            
            @Override
            protected void done() {
                modeloTransacciones.setRowCount(0);
                // Datos de ejemplo
                modeloTransacciones.addRow(new Object[]{"T001", "DEPOSITO", "S/ 500.00", "1001", "2024-01-15", "E001"});
                modeloTransacciones.addRow(new Object[]{"T002", "RETIRO", "S/ 200.00", "1002", "2024-01-16", "E002"});
            }
        };
        worker.execute();
    }
    
    // ========== OPERACIONES ==========
    
    private void agregarCliente() {
        JDialog dialogo = new JDialog(this, "Agregar Cliente", true);
        dialogo.setSize(400, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtId = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCorreo = new JTextField();
        
        panel.add(new JLabel("ID (C001):"));
        panel.add(txtId);
        panel.add(new JLabel("DNI:"));
        panel.add(txtDni);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (validarCamposCliente(txtId.getText(), txtDni.getText(), txtNombre.getText(),
                txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText(),
                txtCorreo.getText())) {
                
                bancoService.agregarCliente(
                    txtId.getText(), txtDni.getText(), txtNombre.getText(),
                    txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText(),
                    txtCorreo.getText()
                );
                cargarClientes();
                dialogo.dispose();
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.setLayout(new BorderLayout());
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private boolean validarCamposCliente(String id, String dni, String nombre, String apellido,
                                        String direccion, String telefono, String correo) {
        if (id.isEmpty() || dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return false;
        }
        return true;
    }
    
    private void buscarCliente() {
        String id = JOptionPane.showInputDialog(this, "Ingrese ID del cliente:");
        if (id != null && !id.trim().isEmpty()) {
            Cliente cliente = bancoService.getClienteDAO().buscarPorCodigo(id);
            
            if (cliente != null) {
                mostrarClienteEnVentana(cliente);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado: " + id);
            }
        }
    }
    
    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        
        String id = tablaClientes.getValueAt(fila, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar cliente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            bancoService.eliminarCliente(id);
            cargarClientes();
        }
    }
    
    private void agregarEmpleado() {
        JDialog dialogo = new JDialog(this, "Agregar Empleado", true);
        dialogo.setSize(400, 350);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtId = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCargo = new JTextField();
        
        panel.add(new JLabel("ID (E001):"));
        panel.add(txtId);
        panel.add(new JLabel("DNI:"));
        panel.add(txtDni);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Cargo:"));
        panel.add(txtCargo);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (validarCamposEmpleado(txtId.getText(), txtDni.getText(), txtNombre.getText(),
                txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText(),
                txtCargo.getText())) {
                
                bancoService.agregarEmpleado(
                    txtId.getText(), txtDni.getText(), txtNombre.getText(),
                    txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText(),
                    txtCargo.getText()
                );
                cargarEmpleados();
                dialogo.dispose();
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.setLayout(new BorderLayout());
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private boolean validarCamposEmpleado(String id, String dni, String nombre, String apellido,
                                         String direccion, String telefono, String cargo) {
        if (id.isEmpty() || dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || cargo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return false;
        }
        return true;
    }
    
    private void agregarCuenta() {
        JDialog dialogo = new JDialog(this, "Agregar Cuenta", true);
        dialogo.setSize(300, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtNumero = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Ahorros", "Corriente", "Inversión"});
        JTextField txtSaldo = new JTextField("0.0");
        
        panel.add(new JLabel("Número:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Saldo inicial:"));
        panel.add(txtSaldo);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                bancoService.agregarCuenta(
                    txtNumero.getText(), 
                    comboTipo.getSelectedItem().toString(),
                    Double.parseDouble(txtSaldo.getText())
                );
                cargarCuentas();
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Saldo inválido");
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panel.add(btnGuardar);
        panel.add(btnCancelar);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void eliminarCuenta() {
        int fila = tablaCuentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta");
            return;
        }
        
        String numero = tablaCuentas.getValueAt(fila, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar cuenta " + numero + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            bancoService.eliminarCuenta(numero);
            cargarCuentas();
        }
    }
    
    private void consultarSaldo() {
        String cuenta = JOptionPane.showInputDialog(this, "Número de cuenta:");
        if (cuenta != null && !cuenta.trim().isEmpty()) {
            Cuenta cuentaObj = bancoService.getCuentaDAO().buscarPorNumero(cuenta);
            
            if (cuentaObj != null) {
                mostrarSaldoEnVentana(cuenta, cuentaObj);
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada: " + cuenta);
            }
        }
    }
    
    private void realizarDeposito() {
        JDialog dialogo = new JDialog(this, "Realizar Depósito", true);
        dialogo.setSize(300, 150);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtCuenta = new JTextField();
        JTextField txtMonto = new JTextField();
        
        panel.add(new JLabel("Cuenta:"));
        panel.add(txtCuenta);
        panel.add(new JLabel("Monto:"));
        panel.add(txtMonto);
        
        JButton btnDepositar = new JButton("Depositar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnDepositar.addActionListener(e -> {
            try {
                bancoService.realizarDeposito(
                    txtCuenta.getText(),
                    Double.parseDouble(txtMonto.getText()),
                    "E001"
                );
                cargarCuentas();
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Monto inválido");
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panel.add(btnDepositar);
        panel.add(btnCancelar);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void realizarRetiro() {
        JDialog dialogo = new JDialog(this, "Realizar Retiro", true);
        dialogo.setSize(300, 150);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtCuenta = new JTextField();
        JTextField txtMonto = new JTextField();
        
        panel.add(new JLabel("Cuenta:"));
        panel.add(txtCuenta);
        panel.add(new JLabel("Monto:"));
        panel.add(txtMonto);
        
        JButton btnRetirar = new JButton("Retirar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnRetirar.addActionListener(e -> {
            try {
                bancoService.realizarRetiro(
                    txtCuenta.getText(),
                    Double.parseDouble(txtMonto.getText()),
                    "E001"
                );
                cargarCuentas();
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Monto inválido");
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panel.add(btnRetirar);
        panel.add(btnCancelar);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    // MÉTODO VER MOVIMIENTOS CORREGIDO
    private void verMovimientos() {
        String cuenta = JOptionPane.showInputDialog(this, "Número de cuenta:");
        if (cuenta != null && !cuenta.trim().isEmpty()) {
            // Primero verificar que la cuenta existe
            Cuenta cuentaObj = bancoService.getCuentaDAO().buscarPorNumero(cuenta);
            
            if (cuentaObj != null) {
                // Usar el método que ya funciona en consola
                bancoService.verMovimientos(cuenta);
                
                // Y mostrar también en una ventana simple
                String mensaje = "Consulta de movimientos para la cuenta: " + cuenta + "\n" +
                               "Saldo actual: S/ " + String.format("%.2f", cuentaObj.getSaldo()) + "\n" +
                               "Tipo de cuenta: " + cuentaObj.getTipoCuenta() + "\n\n" +
                               "Los detalles de los movimientos se han mostrado en la consola.\n" +
                               "Fecha de consulta: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                
                mostrarMensajeEnVentana("Movimientos - Cuenta " + cuenta, mensaje);
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada: " + cuenta);
            }
        }
    }
    
    private void asignarCuentaACliente() {
        JDialog dialogo = new JDialog(this, "Asignar Cuenta", true);
        dialogo.setSize(300, 150);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtCliente = new JTextField();
        JTextField txtCuenta = new JTextField();
        
        panel.add(new JLabel("ID Cliente:"));
        panel.add(txtCliente);
        panel.add(new JLabel("Número Cuenta:"));
        panel.add(txtCuenta);
        
        JButton btnAsignar = new JButton("Asignar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnAsignar.addActionListener(e -> {
            bancoService.asignarCuentaACliente(
                txtCliente.getText(),
                txtCuenta.getText()
            );
            dialogo.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panel.add(btnAsignar);
        panel.add(btnCancelar);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void verCuentasCliente() {
        String idCliente = JOptionPane.showInputDialog(this, "ID del cliente:");
        if (idCliente != null && !idCliente.trim().isEmpty()) {
            Cliente cliente = bancoService.getClienteDAO().buscarPorCodigo(idCliente);
            
            if (cliente != null) {
                mostrarCuentasCliente(cliente);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado: " + idCliente);
            }
        }
    }
    
    private void mostrarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(this, 
            mensaje + ": " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}