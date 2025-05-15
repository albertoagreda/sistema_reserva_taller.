package TurboDesk;

import javax.swing.*;
import java.awt.*;

public class MenuInicio extends JFrame {
    private static final long serialVersionUID = 1L;

    public MenuInicio() {
        // Configurar ventana
        setTitle("Gestión de Taller de Coches - TurboDesk");
        setSize(1340, 890);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        JPanel panelBotones = getPanelBotones();

        // Añadir componentes a la ventana
        getContentPane().setLayout(new BorderLayout(20, 20));
        getContentPane().add(panelBotones, BorderLayout.CENTER);
    }

    private JPanel getPanelBotones() {
        JButton btnCoches = new JButton("Coches");
        btnCoches.setForeground(Color.WHITE);
        btnCoches.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnCoches.setBackground(Color.BLACK);
        btnCoches.setBounds(127, 136, 113, 41);
        btnCoches.addActionListener(e -> {
            Coches ventanaCoches = new Coches();
            ventanaCoches.setVisible(true);
            dispose(); // Cierra la ventana actual
        });

        JButton btnCitas = new JButton("Citas");
        btnCitas.setForeground(Color.WHITE);
        btnCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnCitas.setBackground(Color.BLACK);
        btnCitas.setBounds(127, 206, 113, 41);
        btnCitas.addActionListener(e -> {
            NuevaCita g = new NuevaCita();
            g.setVisible(true);
        });

        JButton btnSoporte = new JButton("Soporte");
        btnSoporte.setForeground(Color.WHITE);
        btnSoporte.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnSoporte.setBackground(Color.BLACK);
        btnSoporte.setBounds(127, 277, 113, 41);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.GRAY);
        panelBotones.setLayout(null);
        panelBotones.add(btnCoches);
        panelBotones.add(btnCitas);
        panelBotones.add(btnSoporte);

        JLabel lblLogo = new JLabel();
        lblLogo.setIcon(new ImageIcon());
        lblLogo.setBounds(-136, -57, 1450, 953);
        panelBotones.add(lblLogo);

        return panelBotones;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuInicio ventana = new MenuInicio();
            ventana.setVisible(true);
        });
    }
}
