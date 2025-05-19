package TurboDesk;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Coches extends JFrame {

    private static final long serialVersionUID = 1L;

    public Coches() {
        setTitle("Venta y Compra de Coches");
        setSize(501, 421);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Botón para vender un coche
        JButton btnVender = new JButton("Vender");
        btnVender.setBounds(116, 202, 250, 71);
        btnVender.setForeground(Color.WHITE);
        btnVender.setBackground(Color.BLACK);
        btnVender.addActionListener(e -> mostrarFormularioVenta());

        // Botón para comprar un coche
        JButton btnComprar = new JButton("Comprar");
        btnComprar.setBounds(116, 90, 250, 71);
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setBackground(Color.BLACK);
        btnComprar.addActionListener(e -> mostrarListaCoches());

        // Panel donde se colocan los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBounds(0, 0, 484, 439);
        panelBotones.setLayout(null);
        panelBotones.add(btnVender);
        panelBotones.add(btnComprar);

        // Título de la ventana
        JLabel titulo = new JLabel("Venta y Compra de Coches", SwingConstants.CENTER);
        titulo.setBounds(-138, 11, 756, 26);
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("SansSerif", Font.PLAIN, 22));
        panelBotones.add(titulo);

        // Botón para volver al menú principal
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(Color.BLACK);
        btnVolver.setBounds(310, 353, 164, 23);
        btnVolver.addActionListener(e -> {
            MenuInicio menu = new MenuInicio();
            menu.setVisible(true);
            dispose(); // Cierra esta ventana
        });
        panelBotones.add(btnVolver);

        getContentPane().setLayout(null);
        getContentPane().add(panelBotones);
    }

    private void mostrarFormularioVenta() {
        JDialog dialog = new JDialog(this, "Registrar Coche", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(7, 2));

        // Campos de entrada
        JLabel lblCocheID = new JLabel("Coche ID:");
        JTextField txtCocheID = new JTextField();
        JLabel lblModelo = new JLabel("Modelo:");
        JTextField txtModelo = new JTextField();
        JLabel lblMarca = new JLabel("Marca:");
        JTextField txtMarca = new JTextField();
        JLabel lblAnio = new JLabel("Año:");
        JTextField txtAnio = new JTextField();
        JLabel lblKilometraje = new JLabel("Kilometraje:");
        JTextField txtKilometraje = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> insertarCoche(txtCocheID.getText(), txtModelo.getText(),
                txtMarca.getText(), txtAnio.getText(), txtKilometraje.getText(), dialog));

        dialog.add(lblCocheID);
        dialog.add(txtCocheID);
        dialog.add(lblModelo);
        dialog.add(txtModelo);
        dialog.add(lblMarca);
        dialog.add(txtMarca);
        dialog.add(lblAnio);
        dialog.add(txtAnio);
        dialog.add(lblKilometraje);
        dialog.add(txtKilometraje);
        dialog.add(new JLabel());
        dialog.add(btnGuardar);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void insertarCoche(String cocheID, String modelo, String marca, String anio, String kilometraje, JDialog dialog) {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            String sql = "INSERT INTO coches (COCHE_ID, MODELO, MARCA, ANIO, KILOMETRAJE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(cocheID));
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            pstmt.setInt(4, Integer.parseInt(anio));
            pstmt.setInt(5, Integer.parseInt(kilometraje));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Coche registrado.");
            dialog.dispose();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar el coche: " + ex.getMessage());
        }
    }

    private void mostrarListaCoches() {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            Connection conn = conexion.getConexion();
            Statement stmt = conn.createStatement();
            String query = "SELECT COCHE_ID, MODELO, MARCA, ANIO, KILOMETRAJE FROM coches";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<String> coches = new ArrayList<>();
            ArrayList<Integer> cochesIds = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("COCHE_ID");
                String cocheInfo = "ID: " + id +
                        " - Modelo: " + rs.getString("MODELO") +
                        ", Marca: " + rs.getString("MARCA") +
                        ", Año: " + rs.getInt("ANIO") +
                        ", Kilometraje: " + rs.getInt("KILOMETRAJE");

                coches.add(cocheInfo);
                cochesIds.add(id);
            }

            if (coches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay coches en el concesionario.");
            } else {
                JList<String> listaCoches = new JList<>(coches.toArray(new String[0]));
                JScrollPane scrollPane = new JScrollPane(listaCoches);

                Object[] options = {"Comprar", "Editar", "Cancelar"};
                int opcion = JOptionPane.showOptionDialog(this, scrollPane, "Selecciona una opción",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);

                if (opcion == 0) { // Comprar
                    int selectedIndex = listaCoches.getSelectedIndex();
                    if (selectedIndex != -1) {
                        int cocheID = cochesIds.get(selectedIndex);
                        eliminarCoche(cocheID);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se seleccionó ningún coche.");
                    }
                } else if (opcion == 1) { // Editar
                    int selectedIndex = listaCoches.getSelectedIndex();
                    if (selectedIndex != -1) {
                        int cocheID = cochesIds.get(selectedIndex);
                        mostrarFormularioEdicion(cocheID);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se seleccionó ningún coche.");
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al comprar coches: " + ex.getMessage());
        }
    }

    private void eliminarCoche(int cocheID) {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            String sql = "DELETE FROM coches WHERE COCHE_ID = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
            pstmt.setInt(1, cocheID);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Coche comprado.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al comprar el coche: " + ex.getMessage());
        }
    }

    private void mostrarFormularioEdicion(int cocheID) {
        JDialog dialog = new JDialog(this, "Editar Coche", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(7, 2));

        JLabel lblModelo = new JLabel("Modelo:");
        JTextField txtModelo = new JTextField();
        JLabel lblMarca = new JLabel("Marca:");
        JTextField txtMarca = new JTextField();
        JLabel lblAnio = new JLabel("Año:");
        JTextField txtAnio = new JTextField();
        JLabel lblKilometraje = new JLabel("Kilometraje:");
        JTextField txtKilometraje = new JTextField();

        JButton btnGuardar = new JButton("Actualizar");
        btnGuardar.addActionListener(e -> actualizarCoche(cocheID, txtModelo.getText(), txtMarca.getText(),
                txtAnio.getText(), txtKilometraje.getText(), dialog));

        dialog.add(lblModelo);
        dialog.add(txtModelo);
        dialog.add(lblMarca);
        dialog.add(txtMarca);
        dialog.add(lblAnio);
        dialog.add(txtAnio);
        dialog.add(lblKilometraje);
        dialog.add(txtKilometraje);
        dialog.add(new JLabel());
        dialog.add(btnGuardar);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

	
	
    private void actualizarCoche(int cocheID, String modelo, String marca, String anio, String kilometraje, JDialog dialog) {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            String sql = "UPDATE coches SET MODELO = ?, MARCA = ?, ANIO = ?, KILOMETRAJE = ? WHERE COCHE_ID = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);

            pstmt.setString(1, modelo);
            pstmt.setString(2, marca);
            pstmt.setInt(3, Integer.parseInt(anio));
            pstmt.setInt(4, Integer.parseInt(kilometraje));
            pstmt.setInt(5, cocheID);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Coche actualizado.");
            dialog.dispose();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace(); // Mostrar error en consola
            JOptionPane.showMessageDialog(this, "Error al actualizar el coche: " + ex.getMessage());
        }
    }


	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> new Coches().setVisible(true));
	}
}
