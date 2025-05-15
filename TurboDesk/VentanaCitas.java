package TurboDesk;

import javax.swing.*;
import java.awt.*;

public class VentanaCitas extends JFrame {

    public VentanaCitas() {
        setTitle("Gestión de Citas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Gestión de Citas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnPedirCita = new JButton("Pedir Cita");
        JButton btnCalendario = new JButton("Calendario");

        btnPedirCita.addActionListener(e -> JOptionPane.showMessageDialog(this, "Pedir Cita"));
        btnCalendario.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ver Calendario"));

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));//botones
        panelBotones.add(btnPedirCita);
        panelBotones.add(btnCalendario);



        setLayout(new BorderLayout(20, 20));
        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            MenuInicio menu = new MenuInicio();
            menu.setVisible(true);
            dispose(); // Cerrar esta ventana
        });
        panelBotones.add(btnVolver);


    }
}
