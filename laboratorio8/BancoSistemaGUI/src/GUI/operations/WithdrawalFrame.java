package GUI.operations;

import service.BancoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WithdrawalFrame extends JFrame {
    private BancoService bancoService;
    private JTextField txtNumeroCuenta;
    private JTextField txtMonto;
    private JTextField txtEmpleadoId;
    private JButton btnRealizarRetiro;
    private JButton btnCancelar;
    private JLabel lblResultado;
    
    public WithdrawalFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Realizar Retiro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(220, 80, 80));
        JLabel lblTitle = new JLabel("RETIRO BANCARIO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Información del Retiro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Número de cuenta
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Número de Cuenta:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        txtNumeroCuenta = new JTextField(20);
        formPanel.add(txtNumeroCuenta, gbc);
        
        // Monto
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Monto a Retirar (S/):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        txtMonto = new JTextField(20);
        formPanel.add(txtMonto, gbc);
        
        // ID Empleado
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("ID Empleado:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        txtEmpleadoId = new JTextField(20);
        txtEmpleadoId.setText("E001");
        formPanel.add(txtEmpleadoId, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnRealizarRetiro = new JButton("Realizar Retiro");
        btnRealizarRetiro.setBackground(new Color(220, 80, 80));
        btnRealizarRetiro.setForeground(Color.WHITE);
        btnRealizarRetiro.setFont(new Font("Arial", Font.BOLD, 14));
        btnRealizarRetiro.addActionListener(this::realizarRetiro);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnRealizarRetiro);
        buttonPanel.add(btnCancelar);
        
        // Panel de resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Resultado"));
        lblResultado = new JLabel(" ", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.PLAIN, 12));
        resultPanel.add(lblResultado, BorderLayout.CENTER);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void realizarRetiro(ActionEvent e) {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        String montoStr = txtMonto.getText().trim();
        String empleadoId = txtEmpleadoId.getText().trim();
        
        if (numeroCuenta.isEmpty() || montoStr.isEmpty() || empleadoId.isEmpty()) {
            lblResultado.setText("ERROR: Todos los campos son obligatorios");
            lblResultado.setForeground(Color.RED);
            return;
        }
        
        try {
            double monto = Double.parseDouble(montoStr);
            
            if (monto <= 0) {
                lblResultado.setText("ERROR: El monto debe ser mayor a 0");
                lblResultado.setForeground(Color.RED);
                return;
            }
            
            // Simulación
            lblResultado.setText("EXITO: Retiro de S/ " + monto + 
                               " realizado de cuenta " + numeroCuenta);
            lblResultado.setForeground(new Color(0, 128, 0));
            
            // Limpiar campos
            txtMonto.setText("");
            txtNumeroCuenta.requestFocus();
            
        } catch (NumberFormatException ex) {
            lblResultado.setText("ERROR: Monto inválido. Ingrese un número válido");
            lblResultado.setForeground(Color.RED);
        }
    }
}
