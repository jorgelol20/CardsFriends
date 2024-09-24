import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class userMenu {
    //Todos los componentes de el menú
    private JPanel panelPrincipal;
    private JTextPane panelCartas;
    private JPanel panelAñadir;
    private JTextField introduceCarta;
    private JButton botonAñadir;
    private JButton botonSalir;
    private JTextField introduceCantidad;
    private JLabel textoCantidad;
    private JLabel textoJuego;
    private JTextField introduceJuego;
    private JButton botonBuscar;
    private JButton botonMinimizar;
    private JButton volverButton;
    private JButton botonPokemon;
    private JButton botonYugi;
    private JButton botonMagic;
    private JComboBox desplegableCantidad;
    private JTable tablaPrincipal;
    private JLabel separadorCasero;
    private static String juego;
    //////////////////////////////////////

    //Credenciales de la base de datos
        //Están marcadas como -final- para que no sean modificables a lo largo del uso del programa
    private static final String jdbcUrl = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
    private static final String username = "postgres.ghlnlytcmdkthuiywwye";
    private static final String password1 = "Cards_Friends_";
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public userMenu(String usuario, JFrame frame) {
        //Creación de la tabla
        lista_cartas(usuario, tablaPrincipal);
        //Creamos columnModel para cambiar propiedades de las columnas
        TableColumnModel columnModel = tablaPrincipal.getColumnModel();
        //Aumentamos el tamaño de la columna 0 ("Nombre") y la columna 2 ("Cantidad")
        columnModel.getColumn(0).setPreferredWidth(190);
        columnModel.getColumn(2).setPreferredWidth(60);
        //Cambiamos el color a la tabla
        tablaPrincipal.setBackground(Color.black);
        tablaPrincipal.setForeground(Color.WHITE);
        tablaPrincipal.setFont(Font.getFont("Arial Black 14 pt"));

        //Iconos de los botones al seleccionarlos
        ImageIcon bPcolor = new ImageIcon("src/main/resources/icons/cartaP.png");
        ImageIcon bPgris = new ImageIcon("src/main/resources/icons/cartaP2.png");
        ImageIcon bMcolor = new ImageIcon("src/main/resources/icons/cartaM.png");
        ImageIcon bMgris= new ImageIcon("src/main/resources/icons/cartaM2.png");
        ImageIcon bYcolor = new ImageIcon("src/main/resources/icons/cartaY.png");
        ImageIcon bYgris = new ImageIcon("src/main/resources/icons/cartaY2.png");



        botonAñadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carta = introduceCarta.getText();
                String candida = (String) desplegableCantidad.getSelectedItem();
                if (carta.isEmpty()) {
                    JOptionPane.showConfirmDialog(null, "El nombre de la carta no puede estar en blanco", "Error", JOptionPane.OK_CANCEL_OPTION);
                } else if (juego == null) {
                    JOptionPane.showConfirmDialog(null, "El juego al que pertenece la carta no puede estar en blanco", "Error", JOptionPane.OK_CANCEL_OPTION);
                }else {
                    try {
                        añadir_cartas(usuario, carta, juego, candida);
                    } catch (SQLException ex) {
                        JOptionPane.showConfirmDialog(null, "No se ha podido añadir la carta a la base de datos", "Error con la base de datos", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    lista_cartas(usuario, tablaPrincipal);
                    introduceCarta.setText("");
                    juego = "";
                    botonPokemon.setIcon(bPcolor);
                    botonMagic.setIcon(bMcolor);
                    botonYugi.setIcon(bYcolor);
                    JOptionPane.showConfirmDialog(null, "La carta se ha añadido a la base de datos", "Carta añadida con éxito", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //Botones de la barra superior
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        botonMinimizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                mainMenu.main();
            }
        });
        //Boton de buscar cartas de otros jugadores
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Función actualmente en desarrollo, espere a siguientes actualizaciones", "Función no disponible", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*Al seleccionar un boton de una de las cartas, las otras se pondrán en gris
         * y la variable "juego" pasará a tener el valor del nombre del juego.     */
        botonPokemon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonPokemon.setIcon(bPcolor);
                botonMagic.setIcon(bMgris);
                botonYugi.setIcon(bYgris);
                juego = "Pokémon TCG";
            }
        });
        botonMagic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonPokemon.setIcon(bPgris);
                botonMagic.setIcon(bMcolor);
                botonYugi.setIcon(bYgris);
                juego = "MTG Arena";
            }
        });
        botonYugi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonPokemon.setIcon(bPgris);
                botonMagic.setIcon(bMgris);
                botonYugi.setIcon(bYcolor);
                juego = "Yu-Gi-Oh";
            }
        });
    }
    //Función para añadir cartas
    private static void añadir_cartas(String usuario, String carta, String juego, String cantidad) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password1)) {
            // Consulta SQL para seleccionar usuarios
            String selectQuery = "SELECT * FROM USERS";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = selectStmt.executeQuery();
                while (resultSet.next()) {
                    // Procesar el resultado
                }
            }
            /*La parte de la consulta me la arreglo ChatGPT debido a que tenía error de síntaxis
             *de SQL debido a que estoy acostumbrado a MySQL y Supabase funciona con PostgreSQL*/

            // Consulta SQL para insertar datos en la tabla "cartas"
            String insertQuery = "INSERT INTO cartas (nombre, dueño, juego, cantidad) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, carta);
                insertStmt.setString(2, usuario);
                insertStmt.setString(3, juego);
                insertStmt.setString(4, cantidad);
                //Ejecuta el update con los valores introducidos
                insertStmt.executeUpdate();
            }
        }
    }
    //Función para sacar la lista de cartas del usuario
    private static String lista_cartas(String usuario, JTable tablaPrincipal) {
        DefaultTableModel tabla = new DefaultTableModel(new Object[]{"Nombre","Juego","Cantidad"},0);
        StringBuilder cartas = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password1)) {
            // Consulta SQL para seleccionar cartas del usuario
            String selectQuery = "SELECT * FROM cartas WHERE dueño = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, usuario);
                ResultSet resultSet = selectStmt.executeQuery();
                //Seguirá haciendo consutas hasta que ya no haya más iteracciones, significando que ya ha revisado toda la BD
                while (resultSet.next()) {
                    // Procesar el resultado de la tabla cartas
                    String nombreCarta = resultSet.getString("nombre");
                    String juegoCarta = resultSet.getString("juego");
                    String cantidadCarta = resultSet.getString("cantidad");
                    // Aquí puedes imprimir o procesar los resultados como desees
                    tabla.addRow(new Object[]{nombreCarta,juegoCarta,cantidadCarta});
                    //cartas.append("Nombre: ").append(nombreCarta).append(", Juego: ").append(juegoCarta).append(", Cantidad: ").append(cantidadCarta).append("\n");
                }
                tablaPrincipal.setModel(tabla);
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null,"Ha ocurrido un error al obtener el listado de cartas", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public static void main(String usuario) {
        JFrame frame = new JFrame("CardsFriends");
        frame.setContentPane(new userMenu(usuario, frame).panelPrincipal);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(720, 420);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());
    }
}

