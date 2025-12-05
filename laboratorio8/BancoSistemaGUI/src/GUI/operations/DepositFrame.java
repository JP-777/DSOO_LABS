package GUI.operations;

import service.BancoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DepositFrame extends JFrame {
    private BancoService bancoService;
    private JTextField txtNumeroCuenta;
    private JTextField txtMonto;
    private JTextField txtEmpleadoId;
    private JButton btnRealizarDeposito;
    private JButton btnCancelar;
    private JLabel lblResultado;
    
    public DepositFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Realizar Depósito");
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
        titlePanel.setBackground(new Color(60, 179, 113));
        JLabel lblTitle = new JLabel("DEPÓSITO BANCARIO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Información del Depósito"));
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
        formPanel.add(new JLabel("Monto a Depositar (S/):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        txtMonto = new JTextField(20);
        formPanel.add(txtMonto, gbc);
        
        // ID Empleado
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("ID Empleado:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        txtEmpleadoId = new JTextField(20);
        txtEmpleadoId.setText("E001"); // Valor por defecto
        formPanel.add(txtEmpleadoId, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnRealizarDeposito = new JButton("Realizar Depósito");
        btnRealizarDeposito.setBackground(new Color(60, 179, 113));
        btnRealizarDeposito.setForeground(Color.WHITE);
        btnRealizarDeposito.setFont(new Font("Arial", Font.BOLD, 14));
        btnRealizarDeposito.addActionListener(this::realizarDeposito);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnRealizarDeposito);
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
    
    private void realizarDeposito(ActionEvent e) {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        String montoStr = txtMonto.getText().trim();
        String empleadoId = txtEmpleadoId.getText().trim();
        
        // Validaciones básicas
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
            
            // Ejecutar depósito a través del servicio
            // bancoService.realizarDeposito(numeroCuenta, monto, empleadoId);
            
            // Simulación por ahora
            lblResultado.setText("EXITO: Depósito de S/ " + monto + 
                               " realizado en cuenta " + numeroCuenta);
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