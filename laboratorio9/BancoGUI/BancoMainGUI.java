
package BancoGUI;

import BancoGUI.LoginFrame;

public class BancoMainGUI {
    public static void main(String[] args) {
        // Configuración básica
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        // Mostrar login
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}