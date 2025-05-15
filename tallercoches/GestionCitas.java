package com.empresa.tallercoches;

import javax.swing.*;
import com.toedter.calendar.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class GestionCitas extends JFrame {
    JButton btnPedirCita, btnVolverMenu;

    public GestionCitas() {
        setTitle("Gestión de Citas");
        setSize(400, 300);
        getContentPane().setLayout(null);

        btnPedirCita = new JButton("Pedir Cita");
        btnPedirCita.setBounds(130, 100, 120, 30);
        getContentPane().add(btnPedirCita);

        btnPedirCita.addActionListener(e -> abrirFormularioCita());

        btnVolverMenu = new JButton("Volver al Menú");
        btnVolverMenu.setBounds(130, 160, 120, 30);
        getContentPane().add(btnVolverMenu);

        btnVolverMenu.addActionListener(e -> volverAlMenu());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void abrirFormularioCita() {
        JFrame formulario = new JFrame("Nueva Cita");
        formulario.setSize(1251, 842);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(197, 177, 187, 44);
        JTextField txtApellidos = new JTextField();
        txtApellidos.setBounds(197, 232, 187, 44);

        JDayChooser dayChooser = new JDayChooser();
        dayChooser.setBounds(197, 286, 187, 133);
        JMonthChooser monthChooser = new JMonthChooser();
        monthChooser.setBounds(197, 430, 100, 104);
        JYearChooser yearChooser = new JYearChooser();
        yearChooser.setBounds(197, 545, 103, 90);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(570, 474, 187, 60);

        btnConfirmar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            int dia = dayChooser.getDay();
            int mes = monthChooser.getMonth();
            int anio = yearChooser.getYear();

            if (nombre.isEmpty() || apellidos.isEmpty() || dia == 0) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes, dia, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date fechaSeleccionada = new java.sql.Date(cal.getTimeInMillis());

            List<java.sql.Date> fechasOcupadas = obtenerFechasOcupadas();
            for (java.sql.Date fecha : fechasOcupadas) {
                if (fecha.equals(fechaSeleccionada)) {
                    JOptionPane.showMessageDialog(null, "Ese día ya está reservado. Elige otro.");
                    return;
                }
            }

            guardarCitaEnBD(nombre, apellidos, fechaSeleccionada);
            formulario.dispose();
            JOptionPane.showMessageDialog(null, "Cita registrada correctamente.");
        });

        formulario.getContentPane().setLayout(null);

        JLabel label = new JLabel("Nombre:");
        label.setBounds(110, 133, 187, 133);
        formulario.getContentPane().add(label);
        formulario.getContentPane().add(txtNombre);

        JLabel label_1 = new JLabel("Apellidos:");
        label_1.setBounds(110, 188, 187, 133);
        formulario.getContentPane().add(label_1);
        formulario.getContentPane().add(txtApellidos);

        JLabel label_2 = new JLabel("Día:");
        label_2.setBounds(110, 301, 89, 90);
        formulario.getContentPane().add(label_2);
        formulario.getContentPane().add(dayChooser);

        JLabel label_3 = new JLabel("Mes:");
        label_3.setBounds(124, 460, 49, 50);
        formulario.getContentPane().add(label_3);
        formulario.getContentPane().add(monthChooser);

        JLabel label_4 = new JLabel("Año:");
        label_4.setBounds(124, 573, 64, 33);
        formulario.getContentPane().add(label_4);
        formulario.getContentPane().add(yearChooser);
        yearChooser.setLayout(null);

        formulario.getContentPane().add(btnConfirmar);

        JLabel lblNewLabel = new JLabel("Citas");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setBounds(236, 66, 136, 114);
        formulario.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Alumno1\\Pictures\\calendario.jpg"));
        lblNewLabel_1.setBounds(548, 250, 302, 194);
        formulario.getContentPane().add(lblNewLabel_1);

        JButton btnVerFechas = new JButton("Ver Fechas Ocupadas");
        btnVerFechas.setBounds(570, 620, 187, 40);
        formulario.getContentPane().add(btnVerFechas);

        btnVerFechas.addActionListener(e -> {
            List<java.sql.Date> fechas = obtenerFechasOcupadas();

            if (fechas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay citas registradas todavía.");
            } else {
                StringBuilder sb = new StringBuilder("Fechas ocupadas:\n");
                for (java.sql.Date fecha : fechas) {
                    sb.append("- ").append(fecha.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        });

        formulario.setVisible(true);
    }

    public void volverAlMenu() {
        dispose(); // Cierra la ventana actual
    }

    public static List<java.sql.Date> obtenerFechasOcupadas() {
        List<java.sql.Date> fechas = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/gestion_citas";
        String usuario = "root";
        String contrasena = "";

        try (Connection con = DriverManager.getConnection(url, usuario, contrasena)) {
            String sql = "SELECT fecha FROM citas";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                fechas.add(rs.getDate("fecha"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener fechas ocupadas.");
        }

        return fechas;
    }

    public void guardarCitaEnBD(String nombre, String apellidos, java.sql.Date fecha) {
        String url = "jdbc:mysql://localhost:3306/gestion_citas";
        String usuario = "root";
        String contrasena = "";

        try {
            Connection con = DriverManager.getConnection(url, usuario, contrasena);
            String sql = "INSERT INTO citas (nombre, apellidos, fecha) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, apellidos);
            pst.setDate(3, fecha);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la cita en la base de datos.");
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error cargando el driver JDBC de MySQL.");
            return;
        }

        new GestionCitas();
    }
}

