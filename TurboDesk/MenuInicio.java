package TurboDesk;

import javax.swing.*;
import java.awt.*;

public class MenuInicio extends JFrame {

    public MenuInicio() {
        // Configurar ventana
        setTitle("");
        setSize(1174, 890);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        JPanel panelBotones = getPanelBotones();

        // Añadir componentes a la ventana
        getContentPane().setLayout(new BorderLayout(20, 20));
        getContentPane().add(panelBotones, BorderLayout.CENTER);
    }

    private JPanel getPanelBotones() {
        JButton btnRepuestos = new JButton("Repuestos");
        btnRepuestos.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRepuestos.setBackground(Color.BLACK);
        btnRepuestos.setBounds(197, 368, 113, 41);
        JButton btnCitas = new JButton("Citas");
        btnCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnCitas.setBackground(Color.BLACK);
        btnCitas.setBounds(434, 368, 113, 41);


        // Añadir eventos a cada botón
        btnRepuestos.addActionListener(e -> {
            VentanaRepuestos repuestos = new VentanaRepuestos();
            repuestos.setVisible(true);
            this.dispose(); // Cierra la ventana actual
        });

        btnCitas.addActionListener(e -> {
            VentanaCitas citas = new VentanaCitas();
            citas.setVisible(true);
            this.dispose(); // Cierra la ventana actual
        });

        // Panel para los botones

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.GRAY);
        panelBotones.setLayout(null);
        panelBotones.add(btnRepuestos);
        panelBotones.add(btnCitas);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(962, 62, 101, 22);
        panelBotones.add(menuBar);
        
        JMenu mnNewMenu = new JMenu("Opciones");
        menuBar.add(mnNewMenu);
        
        JMenu mnNewMenu_1 = new JMenu("Coches");
        mnNewMenu.add(mnNewMenu_1);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Citas");
        mnNewMenu.add(mntmNewMenuItem);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem_1);
        return panelBotones;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuInicio ventana = new MenuInicio();
            ventana.setVisible(true);
        });

    }
}
