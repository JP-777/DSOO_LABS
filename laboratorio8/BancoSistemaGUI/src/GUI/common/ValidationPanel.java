package GUI.common;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ValidationPanel extends JPanel {
    private JLabel statusLabel;
    
    public ValidationPanel(String title) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP
        ));
        
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(statusLabel, BorderLayout.CENTER);
    }
    
    public void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(0, 128, 0));
    }
    
    public void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
    
    public void showWarning(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.ORANGE);
    }
    
    public void clear() {
        statusLabel.setText(" ");
    }
}