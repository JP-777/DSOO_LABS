package GUI.operations;

import service.BancoService;
import DSOO_LABS.laboratorio7.model.Cuenta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BalanceQueryFrame extends JFrame {
    private BancoService bancoService;
    private JTextField txtNumeroCuenta;
    private JButton btnConsultar;
    private JButton btnCancelar;
    private JLabel lblResultado;
    private JLabel lblSaldo;
    
    public BalanceQueryFrame(BancoService bancoService) {
        this.bancoService = bancoService;
        initComponents();
        setupFrame();
    }
    
    private void setupFrame() {
        setTitle("Sistema Bancario - Consultar Saldo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel lblTitle = new JLabel("CONSULTA DE SALDO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel de entrada
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Ingrese número de cuenta"));
        
        JLabel lblCuenta = new JLabel("Número de Cuenta:");
        lblCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtNumeroCuenta = new JTextField(15);
        txtNumeroCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBackground(new Color(70, 130, 180));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConsultar.addActionListener(this::consultarSaldo);
        
        inputPanel.add(lblCuenta);
        inputPanel.add(txtNumeroCuenta);
        inputPanel.add(btnConsultar);
        
        // Panel de resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Información de la Cuenta"));
        resultPanel.setBackground(Color.WHITE);
        
        lblResultado = new JLabel("Ingrese un número de cuenta y haga click en Consultar", 
                                 JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.PLAIN, 12));
        lblResultado.setForeground(Color.GRAY);
        
        lblSaldo = new JLabel(" ", JLabel.CENTER);
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        lblSaldo.setForeground(new Color(0, 128, 0));
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(lblResultado, BorderLayout.NORTH);
        centerPanel.add(lblSaldo, BorderLayout.CENTER);
        
        resultPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnCancelar = new JButton("Cerrar");
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        
        // Ensamblar
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        add(mainPanel);
    }
    
    private void consultarSaldo(ActionEvent e) {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        
        if (numeroCuenta.isEmpty()) {
            lblResultado.setText("ERROR: Ingrese un número de cuenta");
            lblResultado.setForeground(Color.RED);
            lblSaldo.setText("");
            return;
        }
        
        try {
            // Simulación de consulta
            // Cuenta cuenta = bancoService.getCuentaRepo().buscarPorNumero(numeroCuenta);
            
            // Datos de ejemplo
            String tipoCuenta = "Ahorros";
            double saldo = 2500.00;
            
            lblResultado.setText("Cuenta: " + numeroCuenta + " | Tipo: " + tipoCuenta);
            lblResultado.setForeground(Color.BLACK);
            
            lblSaldo.setText("S/ " + String.format("%.2f", saldo));
            lblSaldo.setForeground(new Color(0, 128, 0));
            
        } catch (Exception ex) {
            lblResultado.setText("ERROR: Cuenta no encontrada o error en la consulta");
            lblResultado.setForeground(Color.RED);
            lblSaldo.setText("");
        }
    }
}