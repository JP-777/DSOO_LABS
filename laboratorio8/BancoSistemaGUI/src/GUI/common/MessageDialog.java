package GUI.common;

import javax.swing.*;
import java.awt.*;

public class MessageDialog {
    
    public static void showSuccessDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
            message,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showWarningDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
            message,
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
    }
    
    public static boolean showConfirmDialog(Component parent, String message) {
        int response = JOptionPane.showConfirmDialog(parent,
            message,
            "Confirmar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        return response == JOptionPane.YES_OPTION;
    }
    
    public static String showInputDialog(Component parent, String message) {
        return JOptionPane.showInputDialog(parent,
            message,
            "Entrada",
            JOptionPane.QUESTION_MESSAGE);
    }
}