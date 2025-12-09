package BancoGUI;

import DSOO_LABS.laboratorio7.service.BancoService;
import DSOO_LABS.laboratorio7.service.GestorClinica;
import DSOO_LABS.laboratorio7.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private BancoService bancoService;
    private GestorClinica gestorClinica;
    
    public LoginFrame() {
        bancoService = new BancoService();
        gestorClinica = new GestorClinica(
            bancoService.getClienteRepo(),
            bancoService.getEmpleadoRepo()
        );
        
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena, gbc);
        
        // Botones
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(e -> realizarLogin());
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        
        btnPanel.add(btnLogin);
        btnPanel.add(btnSalir);
        panel.add(btnPanel, gbc);
        
        // Info usuarios
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("Prueba con: jordan.paredes / admin123", JLabel.CENTER);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        panel.add(lblInfo, gbc);
        
        add(panel);
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }
        
        Usuario user = gestorClinica.login(usuario, contrasena);
        
        if (user != null) {
            bancoService.setUsuarioActual(user);
            JOptionPane.showMessageDialog(this, 
                "Bienvenido: " + user.getNombreUsuario() + 
                "\nRol: " + user.getTipo());
            
            this.dispose();
            new MainFrame(bancoService).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }
}