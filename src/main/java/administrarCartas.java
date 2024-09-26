import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class administrarCartas {

    private static final String jdbcUrl = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
    private static final String username = "postgres.ghlnlytcmdkthuiywwye";
    private static final String password1 = "Cards_Friends_";
    private JPanel panelAdministrar;
    private JButton botonMinimizar;
    private JButton botonSalir;
    private JButton volverButton;
    private JComboBox seleccionCartas;
    private JTable tablaCartas;
    private JPanel panelSuperior;
    private JLabel separadorCasero;
    private JButton actualizarButton;
    private JComboBox cantidadBox;
    private JButton eliminarButton;
    private static String carta;
    private static String cantidad;

    public administrarCartas(String usuario, JFrame frame) {
        lista_cartas(usuario, seleccionCartas, tablaCartas);
        //Creamos columnModel para cambiar propiedades de las columnas
        TableColumnModel columnModel = tablaCartas.getColumnModel();
        //Aumentamos el tamaño de la columna 0 ("Nombre") y la columna 2 ("Cantidad")
        columnModel.getColumn(0).setPreferredWidth(190);
        columnModel.getColumn(2).setPreferredWidth(60);
        //Cambiamos el color a la tabla
        tablaCartas.setBackground(Color.black);
        tablaCartas.setForeground(Color.WHITE);
        tablaCartas.setFont(Font.getFont("Arial Black 14 pt"));
        botonSalir.addActionListener(e -> System.exit(0));
        botonMinimizar.addActionListener(e -> frame.setState(JFrame.ICONIFIED));
        volverButton.addActionListener(e -> {
            frame.setVisible(false);
            userMenu.main(usuario);
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    carta = String.valueOf(seleccionCartas.getSelectedItem());
                    cantidad = String.valueOf(cantidadBox.getSelectedItem());
                    actualizar_carta(usuario,carta,cantidad);
                    JOptionPane.showConfirmDialog(null,"La carta ha sido actualizada correctamente", "Carta actualizada", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    lista_cartas(usuario, seleccionCartas, tablaCartas);
                    //Creamos columnModel para cambiar propiedades de las columnas
                    TableColumnModel columnModel = tablaCartas.getColumnModel();
                    //Aumentamos el tamaño de la columna 0 ("Nombre") y la columna 2 ("Cantidad")
                    columnModel.getColumn(0).setPreferredWidth(190);
                    columnModel.getColumn(2).setPreferredWidth(60);
                    //Cambiamos el color a la tabla
                    tablaCartas.setBackground(Color.black);
                    tablaCartas.setForeground(Color.WHITE);
                    tablaCartas.setFont(Font.getFont("Arial Black 14 pt"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carta = String.valueOf(seleccionCartas.getSelectedItem());
                cantidad = String.valueOf(cantidadBox.getSelectedItem());
                try {
                    int opciones = JOptionPane.showOptionDialog(null, "¿Seguro que deseas eliminar esta carta?", "Eliminar carta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, 1);
                    if (opciones == 1) {

                    } else{
                        eliminar_carta(usuario, carta);
                    JOptionPane.showConfirmDialog(null, "La carta ha sido eliminada correctamente", "Carta eliminada", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    lista_cartas(usuario, seleccionCartas, tablaCartas);
                    //Creamos columnModel para cambiar propiedades de las columnas
                    TableColumnModel columnModel = tablaCartas.getColumnModel();
                    //Aumentamos el tamaño de la columna 0 ("Nombre") y la columna 2 ("Cantidad")
                    columnModel.getColumn(0).setPreferredWidth(190);
                    columnModel.getColumn(2).setPreferredWidth(60);
                    //Cambiamos el color a la tabla
                    tablaCartas.setBackground(Color.black);
                    tablaCartas.setForeground(Color.WHITE);
                    tablaCartas.setFont(Font.getFont("Arial Black 14 pt"));
                }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private static String lista_cartas(String usuario, JComboBox seleccionCartas, JTable tablaPrincipal) {
        DefaultTableModel tabla = new DefaultTableModel(new Object[]{"Nombre","Juego","Cantidad"},0);
        StringBuilder cartas = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password1)) {
            String selectQuery = "SELECT * FROM cartas WHERE dueño = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, usuario);
                ResultSet resultSet = selectStmt.executeQuery();
                while (resultSet.next()) {
                    String nombreCarta = resultSet.getString("nombre");
                    String juegoCarta = resultSet.getString("juego");
                    String cantidadCarta = resultSet.getString("cantidad");
                    tabla.addRow(new Object[]{nombreCarta,juegoCarta,cantidadCarta});

                    cartas.append(nombreCarta + "\n");
                }
                DefaultComboBoxModel<String> boxCartas = new DefaultComboBoxModel<>(cartas.toString().split("\n"));
                seleccionCartas.setModel(boxCartas);
                tablaPrincipal.setModel(tabla);
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null,"Ha ocurrido un error al obtener el listado de cartas", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
    private static void actualizar_carta(String usuario, String carta, String cantidad) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password1)) {
            // Consulta SQL para seleccionar usuarios
            String selectQuery = "SELECT * FROM USERS";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = selectStmt.executeQuery();
                while (resultSet.next()) {
                }
            }
            String updateQuery = "UPDATE cartas SET cantidad = ? WHERE dueño = ? AND nombre = ?";
            try (PreparedStatement insertStmt = connection.prepareStatement(updateQuery)) {
                insertStmt.setString(1, cantidad);
                insertStmt.setString(2, usuario);
                insertStmt.setString(3, carta);
                insertStmt.executeUpdate();
            }
        }
    }
    private static void eliminar_carta(String usuario, String carta) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password1)) {
            // Consulta SQL para seleccionar usuarios
            String selectQuery = "SELECT * FROM USERS";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = selectStmt.executeQuery();
                while (resultSet.next()) {
                }
            }
            String updateQuery = "DELETE FROM cartas WHERE dueño = ? AND nombre = ?";
            try (PreparedStatement insertStmt = connection.prepareStatement(updateQuery)) {
                insertStmt.setString(1, usuario);
                insertStmt.setString(2, carta);
                insertStmt.executeUpdate();
            }
        }
    }
    public static void main(String usuario) {
        JFrame frame = new JFrame();
        frame.setContentPane(new administrarCartas(usuario, frame).panelAdministrar);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(720, 420);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());
    }
}
