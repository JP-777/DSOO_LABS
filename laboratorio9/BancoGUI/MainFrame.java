
package BancoGUI;

import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.model.Usuario;
import DSOO_LABS.laboratorio7.model.Cliente;
import DSOO_LABS.laboratorio7.model.Empleado;
import DSOO_LABS.laboratorio7.model.Cuenta;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private BancoService bancoService;
    private Usuario usuarioActual;
    private JTextArea txtOutput;
    
    public MainFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        this.usuarioActual = bancoService.getUsuarioActual();
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Usuario: " + usuarioActual.getNombreUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel de información
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));
        
        JLabel lblInfo = new JLabel("Usuario: " + usuarioActual.getNombreUsuario() + 
                                   " | Rol: " + usuarioActual.getTipo());
        infoPanel.add(lblInfo, BorderLayout.WEST);
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        infoPanel.add(btnLogout, BorderLayout.EAST);
        
        // Panel de botones (izquierda)
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        
        // Área de salida (derecha)
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        // Crear botones según tipo de usuario
        crearBotonesPorRol(buttonPanel);
        
        // Ensamblar
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void crearBotonesPorRol(JPanel panel) {
        String tipo = usuarioActual.getTipo();
        
        // === BOTONES PARA TODOS LOS USUARIOS ===
        panel.add(crearBoton("Consultar Saldo", e -> consultarSaldo()));
        panel.add(crearBoton("Realizar Depósito", e -> realizarDeposito()));
        panel.add(crearBoton("Realizar Retiro", e -> realizarRetiro()));
        panel.add(crearBoton("Ver Movimientos", e -> verMovimientos()));
        
        // === BOTONES PARA EMPLEADO Y ADMIN ===
        if (tipo.equals("EMPLEADO") || tipo.equals("ADMINISTRADOR")) {
            panel.add(crearBoton("Listar Clientes", e -> listarClientes()));
            panel.add(crearBoton("Listar Cuentas", e -> listarCuentas()));
            panel.add(crearBoton("Buscar Cliente", e -> buscarCliente()));
            panel.add(crearBoton("Buscar Cuenta", e -> buscarCuenta()));
        }
        
        // === BOTONES SOLO PARA ADMIN ===
        if (tipo.equals("ADMINISTRADOR")) {
            panel.add(crearBoton("Agregar Cliente", e -> agregarCliente()));
            panel.add(crearBoton("Eliminar Cliente", e -> eliminarCliente()));
            panel.add(crearBoton("Agregar Empleado", e -> agregarEmpleado()));
            panel.add(crearBoton("Listar Empleados", e -> listarEmpleados()));
            panel.add(crearBoton("Agregar Cuenta", e -> agregarCuenta()));
            panel.add(crearBoton("Eliminar Cuenta", e -> eliminarCuenta()));
            panel.add(crearBoton("Listar Transacciones", e -> listarTransacciones()));
            panel.add(crearBoton("Ver Usuarios Sistema", e -> verUsuariosSistema()));
        }
        
        // === BOTONES ESPECIALES CLIENTE ===
        if (tipo.equals("CLIENTE")) {
            panel.add(crearBoton("Mis Cuentas", e -> misCuentas()));
        }
    }
    
    private JButton crearBoton(String texto, java.awt.event.ActionListener action) {
        JButton btn = new JButton(texto);
        btn.addActionListener(action);
        return btn;
    }
    
    // ========== MÉTODOS QUE LLAMAN A TU LÓGICA REAL ==========
    
    private void consultarSaldo() {
        String numero = JOptionPane.showInputDialog(this, "Número de cuenta:");
        if (numero != null && !numero.trim().isEmpty()) {
            try {
                // Usa tu lógica real
                Cuenta cuenta = bancoService.getCuentaRepo().buscarPorNumero(numero);
                if (cuenta != null) {
                    txtOutput.setText("=== CONSULTA DE SALDO ===\n");
                    txtOutput.append("Cuenta: " + cuenta.getNumeroCuenta() + "\n");
                    txtOutput.append("Tipo: " + cuenta.getTipoCuenta() + "\n");
                    txtOutput.append("Saldo: S/ " + cuenta.getSaldo() + "\n");
                } else {
                    txtOutput.setText("Cuenta no encontrada");
                }
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void realizarDeposito() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtCuenta = new JTextField();
        JTextField txtMonto = new JTextField();
        JTextField txtEmpleado = new JTextField("E001");
        
        panel.add(new JLabel("Número Cuenta:"));
        panel.add(txtCuenta);
        panel.add(new JLabel("Monto:"));
        panel.add(txtMonto);
        panel.add(new JLabel("ID Empleado:"));
        panel.add(txtEmpleado);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Depósito", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                double monto = Double.parseDouble(txtMonto.getText());
                bancoService.realizarDeposito(txtCuenta.getText(), monto, txtEmpleado.getText());
                txtOutput.setText("Depósito realizado exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error en depósito: " + e.getMessage());
            }
        }
    }
    
    private void realizarRetiro() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtCuenta = new JTextField();
        JTextField txtMonto = new JTextField();
        JTextField txtEmpleado = new JTextField("E001");
        
        panel.add(new JLabel("Número Cuenta:"));
        panel.add(txtCuenta);
        panel.add(new JLabel("Monto:"));
        panel.add(txtMonto);
        panel.add(new JLabel("ID Empleado:"));
        panel.add(txtEmpleado);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Retiro", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                double monto = Double.parseDouble(txtMonto.getText());
                bancoService.realizarRetiro(txtCuenta.getText(), monto, txtEmpleado.getText());
                txtOutput.setText("Retiro realizado exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error en retiro: " + e.getMessage());
            }
        }
    }
    
    private void verMovimientos() {
        String numero = JOptionPane.showInputDialog(this, "Número de cuenta:");
        if (numero != null && !numero.trim().isEmpty()) {
            try {
                bancoService.verMovimientos(numero);
                txtOutput.setText("Movimientos consultados (ver en consola)");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void listarClientes() {
        try {
            bancoService.listarClientes();
            txtOutput.setText("Clientes listados (ver en consola)\n");
            
            // También mostrar en GUI
            List<Cliente> clientes = bancoService.getClienteRepo().getListaClientes();
            txtOutput.append("=== CLIENTES EN SISTEMA ===\n");
            for (Cliente c : clientes) {
                txtOutput.append(c.toString() + "\n");
            }
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
    
    private void agregarCliente() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCorreo = new JTextField();
        
        panel.add(new JLabel("ID Cliente:"));
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
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Cliente", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                bancoService.agregarCliente(
                    txtId.getText(), txtDni.getText(), txtNombre.getText(),
                    txtApellido.getText(), txtDireccion.getText(),
                    txtTelefono.getText(), txtCorreo.getText()
                );
                txtOutput.setText("Cliente agregado exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void eliminarCliente() {
        String id = JOptionPane.showInputDialog(this, "ID del cliente a eliminar:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                bancoService.eliminarCliente(id);
                txtOutput.setText("Cliente eliminado exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void listarEmpleados() {
        try {
            bancoService.listarEmpleados();
            txtOutput.setText("Empleados listados (ver en consola)\n");
            
            List<Empleado> empleados = bancoService.getEmpleadoRepo().getListaEmpleados();
            txtOutput.append("=== EMPLEADOS ===\n");
            for (Empleado e : empleados) {
                txtOutput.append(e.toString() + "\n");
            }
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
    
    private void agregarEmpleado() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField txtId = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCargo = new JTextField();
        
        panel.add(new JLabel("ID Empleado:"));
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
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Empleado", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                bancoService.agregarEmpleado(
                    txtId.getText(), txtDni.getText(), txtNombre.getText(),
                    txtApellido.getText(), txtDireccion.getText(),
                    txtTelefono.getText(), txtCargo.getText()
                );
                txtOutput.setText("Empleado agregado exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void listarCuentas() {
        try {
            bancoService.listarCuentas();
            txtOutput.setText("Cuentas listadas (ver en consola)\n");
            
            List<Cuenta> cuentas = bancoService.getCuentaRepo().getListaCuentas();
            txtOutput.append("=== CUENTAS ===\n");
            for (Cuenta c : cuentas) {
                txtOutput.append(c.toString() + "\n");
            }
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
    
    private void agregarCuenta() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtNumero = new JTextField();
        JTextField txtTipo = new JTextField("Ahorros");
        JTextField txtSaldo = new JTextField("0.0");
        
        panel.add(new JLabel("Número Cuenta:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);
        panel.add(new JLabel("Saldo Inicial:"));
        panel.add(txtSaldo);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Cuenta", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                double saldo = Double.parseDouble(txtSaldo.getText());
                bancoService.agregarCuenta(txtNumero.getText(), txtTipo.getText(), saldo);
                txtOutput.setText("Cuenta agregada exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void eliminarCuenta() {
        String numero = JOptionPane.showInputDialog(this, "Número de cuenta a eliminar:");
        if (numero != null && !numero.trim().isEmpty()) {
            try {
                bancoService.eliminarCuenta(numero);
                txtOutput.setText("Cuenta eliminada exitosamente");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void buscarCliente() {
        String id = JOptionPane.showInputDialog(this, "ID del cliente:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                bancoService.buscarClientePorId(id);
                txtOutput.setText("Búsqueda realizada (ver en consola)");
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void buscarCuenta() {
        String numero = JOptionPane.showInputDialog(this, "Número de cuenta:");
        if (numero != null && !numero.trim().isEmpty()) {
            try {
                Cuenta cuenta = bancoService.getCuentaRepo().buscarPorNumero(numero);
                if (cuenta != null) {
                    txtOutput.setText("Cuenta encontrada:\n" + cuenta.toString());
                } else {
                    txtOutput.setText("Cuenta no encontrada");
                }
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }
    }
    
    private void listarTransacciones() {
        try {
            bancoService.listarTransacciones();
            txtOutput.setText("Transacciones listadas (ver en consola)");
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
    
    private void verUsuariosSistema() {
        try {
            // Usar tu GestorClinica para mostrar usuarios
            txtOutput.setText("Usuarios del sistema:\n");
            txtOutput.append("1. Administradores:\n");
            txtOutput.append("   - jordan.paredes / admin123\n");
            txtOutput.append("   - brayan.sanchez / admin456\n\n");
            txtOutput.append("2. Empleados:\n");
            txtOutput.append("   - kevin.peralta / empleado123\n");
            txtOutput.append("   - elkin.ramos / empleado456\n");
            txtOutput.append("   - fernando.solsol / empleado789\n\n");
            txtOutput.append("3. Clientes:\n");
            txtOutput.append("   - edwar.saire / cliente123\n");
            txtOutput.append("   - elon.musk / cliente456\n");
            txtOutput.append("   - satya.nadella / cliente789\n");
            txtOutput.append("   - tim.cook / cliente012\n");
            txtOutput.append("   - sundar.pichai / cliente345\n");
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
    
    private void misCuentas() {
        try {
            txtOutput.setText("Mis cuentas (cliente):\n");
            // Aquí iría la lógica para obtener cuentas del cliente actual
            txtOutput.append("Esta función está en desarrollo\n");
            txtOutput.append("Por ahora, use 'Consultar Saldo' o 'Ver Movimientos'\n");
        } catch (Exception e) {
            txtOutput.setText("Error: " + e.getMessage());
        }
    }
}