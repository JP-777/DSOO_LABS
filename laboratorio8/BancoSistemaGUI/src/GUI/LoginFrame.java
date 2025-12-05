package GUI;

import service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JButton btnVerUsuarios;
    private BancoService bancoService;
    private GestorClinica gestorClinica;
    
    public LoginFrame() {
        initComponents();
        initServices();
        setupFrame();
    }
    
    private void initServices() {
        bancoService = new BancoService();
        gestorClinica = new GestorClinica(
            bancoService.getClienteRepo(),
            bancoService.getEmpleadoRepo()
        );
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel lblTitle = new JLabel("SISTEMA BANCARIO UNSA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Inicio de Sesión"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        txtUsuario = new JTextField(15);
        formPanel.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        txtContrasena = new JPasswordField(15);
        formPanel.add(txtContrasena, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(150, 35));
        
        btnVerUsuarios = new JButton("Ver Usuarios");
        btnVerUsuarios.setBackground(new Color(100, 100, 100));
        btnVerUsuarios.setForeground(Color.WHITE);
        btnVerUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnVerUsuarios);
        
        // Agregar acción al botón de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        // Agregar acción al botón de ver usuarios
        btnVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuariosPredefinidos();
            }
        });
        
        // También permitir login con Enter
        txtContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        // Ensamblar la ventana
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario user = gestorClinica.login(usuario, contrasena);
        
        if (user != null) {
            bancoService.setUsuarioActual(user);
            
            // Mostrar mensaje de bienvenida
            String tipoUsuario = user.getTipo();
            String nombreUsuario = user.getNombreUsuario();
            
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, " + nombreUsuario + "!\n" +
                "Rol: " + tipoUsuario,
                "Login exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Cerrar ventana de login
            this.dispose();
            
            // Abrir menú principal
            SwingUtilities.invokeLater(() -> {
                MainMenuFrame mainMenu = new MainMenuFrame(bancoService);
                mainMenu.setVisible(true);
                mainMenu.setLocationRelativeTo(null);
            });
            
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos\n" +
                "Intente nuevamente",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            
            // Limpiar campo de contraseña
            txtContrasena.setText("");
            txtUsuario.requestFocus();
        }
    }
    
    private void mostrarUsuariosPredefinidos() {
        StringBuilder usuarios = new StringBuilder();
        usuarios.append("=== USUARIOS PREDEFINIDOS ===\n\n");
        usuarios.append("ADMINISTRADORES:\n");
        usuarios.append("• jordan.paredes / admin123\n");
        usuarios.append("• brayan.sanchez / admin456\n\n");
        usuarios.append("EMPLEADOS:\n");
        usuarios.append("• kevin.peralta / empleado123\n");
        usuarios.append("• elkin.ramos / empleado456\n");
        usuarios.append("• fernando.solsol / empleado789\n\n");
        usuarios.append("CLIENTES:\n");
        usuarios.append("• edwar.saire / cliente123\n");
        usuarios.append("• elon.musk / cliente456\n");
        usuarios.append("• satya.nadella / cliente789\n");
        usuarios.append("• tim.cook / cliente012\n");
        usuarios.append("• sundar.pichai / cliente345\n");
        
        JOptionPane.showMessageDialog(this,
            usuarios.toString(),
            "Usuarios del Sistema",
            JOptionPane.INFORMATION_MESSAGE);
    }
} 