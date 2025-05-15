package com.empresa.tallercoches;

import javax.swing.*;
import java.awt.*;


public class MenuInicio extends JFrame {
	private JTextField txtSopprte;
	

    public MenuInicio() {
    	
        // Configurar ventana
        setTitle("Gestión de Taller de Coches");
        setSize(1340, 890);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        JPanel panelBotones = getPanelBotones();

        // Añadir componentes a la ventana
        getContentPane().setLayout(new BorderLayout(20, 20));
        getContentPane().add(panelBotones, BorderLayout.CENTER);
    }

    private JPanel getPanelBotones() {
        JButton btnRepuestos = new JButton("Coches");
        btnRepuestos.setForeground(new Color(255, 255, 255));
        btnRepuestos.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRepuestos.setBackground(new Color(0, 0, 0));
        btnRepuestos.setBounds(127, 136, 113, 41);
        JButton btnCitas = new JButton("Citas");
        btnCitas.setForeground(new Color(255, 255, 255));
        btnCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnCitas.setBackground(Color.BLACK);
        btnCitas.setBounds(127, 206, 113, 41);


        

        btnCitas.addActionListener(e -> {
            NuevaCita g= new NuevaCita();
          g.setVisible(true);
            
        });


        // Panel para los botones

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.GRAY);
        panelBotones.setLayout(null);
        panelBotones.add(btnRepuestos);
        panelBotones.add(btnCitas);
                
                JButton btnCitas_2 = new JButton("Soporte");
                btnCitas_2.setForeground(Color.WHITE);
                btnCitas_2.setFont(new Font("Tahoma", Font.BOLD, 11));
                btnCitas_2.setBackground(Color.BLACK);
                btnCitas_2.setBounds(127, 277, 113, 41);
                panelBotones.add(btnCitas_2);
                
                txtSopprte = new JTextField();
                txtSopprte.setText("sopprte");
                txtSopprte.setBounds(139, 277, 86, 20);
                panelBotones.add(txtSopprte);
                txtSopprte.setColumns(10);
                
                JButton btnNewButton = new JButton("New button");
                btnNewButton.setBounds(136, 286, 89, 23);
                panelBotones.add(btnNewButton);
                
                JLabel lblNewLabel = new JLabel("");
                lblNewLabel.setForeground(new Color(255, 255, 255));
                lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Alumno1\\Downloads\\A stylized logo for a car parts company named TurboDesk, featuring a sleek, chrome-like design incorporating car parts such as a piston, spark plug, or gear. The logo should be visually appealing, using a bold, m.jpg"));
                lblNewLabel.setBounds(-136, -57, 1450, 953);
                panelBotones.add(lblNewLabel);
        return panelBotones;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuInicio ventana = new MenuInicio();
            ventana.setVisible(true);
        });

    }
}


