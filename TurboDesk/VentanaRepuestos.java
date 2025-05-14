package TurboDesk;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class VentanaRepuestos extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaRepuestos() {
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
        
        // Fondo de la ventana
        JButton btnNewButton = new JButton();
        btnNewButton.setEnabled(false);
        btnNewButton.setBounds(0, 0, 484, 390);
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Alumno1\\Desktop\\istockphoto-1281648010-612x612.jpg"));
        panelBotones.add(btnNewButton);
    }

    // Método para mostrar la lista de coches disponibles y permitir la compra
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

                int opcion = JOptionPane.showConfirmDialog(
                        this, scrollPane, "Selecciona un coche para comprar", JOptionPane.OK_CANCEL_OPTION);

                if (opcion == JOptionPane.OK_OPTION) {
                    int selectedIndex = listaCoches.getSelectedIndex();
                    if (selectedIndex != -1) {
                        int cocheID = cochesIds.get(selectedIndex);
                        eliminarCoche(cocheID);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se seleccionó ningún coche.");
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al comprar coches: " + ex.getMessage());
        }
    }

    // Método para eliminar el coche de la base de datos al comprarlo
    private void eliminarCoche(int cocheID) {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            String sql = "DELETE FROM coches WHERE COCHE_ID = " + cocheID;
            conexion.ejecutarInsertDeleteUpdate(sql);
            JOptionPane.showMessageDialog(this, "Coche comprado.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al comprar el coche: " + ex.getMessage());
        }
    }

    // Método para mostrar el formulario de venta de coches
    private void mostrarFormularioVenta() {
        JDialog dialog = new JDialog(this, "Registrar Coche", true);
        dialog.setSize(400, 350);
        dialog.getContentPane().setLayout(new GridLayout(7, 2));

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

        // Botón para guardar el coche en la base de datos
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> insertarCoche(txtCocheID.getText(), txtModelo.getText(), txtMarca.getText(), txtAnio.getText(),
                                                        txtKilometraje.getText(), dialog));

        dialog.getContentPane().add(lblCocheID); dialog.getContentPane().add(txtCocheID);
        dialog.getContentPane().add(lblModelo); dialog.getContentPane().add(txtModelo);
        dialog.getContentPane().add(lblMarca); dialog.getContentPane().add(txtMarca);
        dialog.getContentPane().add(lblAnio); dialog.getContentPane().add(txtAnio);
        dialog.getContentPane().add(lblKilometraje); dialog.getContentPane().add(txtKilometraje);
        dialog.getContentPane().add(new JLabel()); dialog.getContentPane().add(btnGuardar);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Método corregido para insertar coches en la base de datos
    private void insertarCoche(String cocheID, String modelo, String marca, String anio, String kilometraje, JDialog dialog) {
        try {
            ConexionMySQL conexion = new ConexionMySQL("root", "", "taller_repuestos");
            conexion.conectar();
            String sql = "INSERT INTO coches (COCHE_ID, MODELO, MARCA, ANIO, KILOMETRAJE) VALUES (?, ?, ?, ?, ?)";
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, Integer.parseInt(cocheID));
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            pstmt.setInt(4, Integer.parseInt(anio));
            pstmt.setInt(5, Integer.parseInt(kilometraje));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Coche registrado exitosamente.");
            dialog.dispose();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar el coche: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaRepuestos().setVisible(true));
    }
}
