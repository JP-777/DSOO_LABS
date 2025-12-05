package mainGUI;

import GUI.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BancoMainGUI {
    public static void main(String[] args) {
        // Configurar el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            loginFrame.setLocationRelativeTo(null); // Centrar en pantalla
        });
    }
}