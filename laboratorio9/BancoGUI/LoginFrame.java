package DSOO_LABS.laboratorio9.BancoGUI; // Nota el cambio a lab9

import DSOO_LABS.laboratorio9.service.BancoService;
import DSOO_LABS.laboratorio9.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private BancoService bancoService;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    
    public LoginFrame() {
        // Inicializamos el servicio UNIFICADO
        bancoService = new BancoService();
        
        setTitle("Login - Sistema Bancario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> realizarLogin());
        panel.add(btnLogin, gbc);
        
        add(panel);
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        // Llamada limpia al servicio (Sin intermediarios raros)
        Usuario usuarioAutenticado = bancoService.login(usuario, password);
        
        if (usuarioAutenticado != null) {
            bancoService.setUsuarioActual(usuarioAutenticado);
            
            // Pasamos el servicio autenticado al MainFrame
            MainFrame mainFrame = new MainFrame(bancoService);
            mainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.\nPrueba: admin / admin123");
        }
    }
}