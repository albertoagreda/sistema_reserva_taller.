package com.empresa.tallercoches;

import javax.swing.*;
import com.toedter.calendar.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class NuevaCita extends JFrame {
    private JButton btnVolver;

    public NuevaCita() {
    	getContentPane().setForeground(new Color(0, 0, 0));
    	getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
    	getContentPane().setBackground(new Color(240, 240, 240));
        setTitle("Nueva Cita");
        setSize(766, 842);
        setLocationRelativeTo(null);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(197, 154, 187, 44);
        JTextField txtApellidos = new JTextField();
        txtApellidos.setBounds(197, 221, 187, 44);

        JDayChooser dayChooser = new JDayChooser();
        dayChooser.setBounds(197, 286, 187, 133);
        JMonthChooser monthChooser = new JMonthChooser();
        monthChooser.getComboBox().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        monthChooser.setBounds(197, 430, 158, 104);
        monthChooser.getSpinner().setBounds(0, 0, 121, 104);
        JYearChooser yearChooser = new JYearChooser();
        yearChooser.getSpinner().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        yearChooser.setBounds(197, 545, 158, 63);
        yearChooser.getSpinner().setBounds(0, 0, 124, 64);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        btnConfirmar.setBackground(new Color(255, 0, 0));
        btnConfirmar.setBounds(225, 619, 101, 34);

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
            cal.set(anio, mes, dia);
            Date fechaSeleccionada = new java.sql.Date(cal.getTimeInMillis());

            java.util.List<Date> fechasOcupadas = obtenerFechasOcupadas();
            for (Date fecha : fechasOcupadas) {
                if (fecha.equals(fechaSeleccionada)) {
                    JOptionPane.showMessageDialog(null, "Ese día ya está reservado. Elige otro.");
                    return;
                }
            }

            guardarCitaEnBD(nombre, apellidos, fechaSeleccionada);
            dispose();
            JOptionPane.showMessageDialog(null, "Cita registrada correctamente.");
        });

        btnVolver = new JButton("Volver al Menú");
        btnVolver.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
        btnVolver.setBackground(new Color(255, 255, 0));
        btnVolver.setBounds(509, 596, 143, 40);
        btnVolver.addActionListener(e -> {
            
            dispose();
        });

        JButton btnVerFechas = new JButton("Ver Fechas Ocupadas");
        btnVerFechas.setBackground(new Color(135, 206, 235));
        btnVerFechas.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        btnVerFechas.setBounds(475, 179, 187, 40);
        btnVerFechas.addActionListener(e -> {
            java.util.List<Date> fechas = obtenerFechasOcupadas();
            if (fechas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay citas registradas todavía.");
            } else {
                StringBuilder sb = new StringBuilder("Fechas ocupadas:\n");
                for (Date fecha : fechas) {
                    sb.append("- ").append(fecha.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        });
        getContentPane().setLayout(null);

        getContentPane().add(txtNombre);
        getContentPane().add(txtApellidos);
        getContentPane().add(dayChooser);
        getContentPane().add(monthChooser);
        monthChooser.setLayout(null);
        getContentPane().add(yearChooser);
        yearChooser.setLayout(null);
        getContentPane().add(btnConfirmar);
        getContentPane().add(btnVerFechas);
        getContentPane().add(btnVolver);

        JLabel fondo = new JLabel(new ImageIcon("C:\\Users\\Alumno1\\Pictures\\calendario.jpg"));
        fondo.setBounds(457, 232, 235, 194);
        getContentPane().add(fondo);
        
        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel.setBounds(65, 158, 84, 29);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Apellidos");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel_1.setBounds(62, 236, 125, 29);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Fechas");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel_2.setBounds(62, 324, 87, 34);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Mes");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel_3.setBounds(89, 473, 46, 25);
        getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Año");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel_4.setBounds(100, 579, 46, 14);
        getContentPane().add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setBounds(-15, 0, 775, 803);
        lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\Alumno1\\Downloads\\61oGHNGhaJL._AC_UF894,1000_QL80_.jpg"));
        getContentPane().add(lblNewLabel_5);
    }

    public void mostrarBotonVolver(boolean mostrar) {
        btnVolver.setVisible(mostrar);
    }

    private java.util.List<Date> obtenerFechasOcupadas() {
        java.util.List<Date> fechas = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_citas", "root", "")) {
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

    private void guardarCitaEnBD(String nombre, String apellidos, Date fecha) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_citas", "root", "")) {
            String sql = "INSERT INTO citas (nombre, apellidos, fecha) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, apellidos);
            pst.setDate(3, new java.sql.Date(fecha.getTime()));
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la cita.");
        }
    }
}
