package DSOO_LABS.laboratorio9.BancoGUI;

import javax.swing.*;

public class BancoMainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}