import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class registrarseMenu {
    //Todos los componentes de el menú
    private JTextField indicarUsuario;
    private JPasswordField indicarContraseña;
    private JLabel textoContrasena;
    private JLabel textoUsuario;
    private JButton botonRegistrarse;
    private JPanel panelPrincipal;
    private JTextArea textArea1;
    private JButton botonSalir;
    private JButton botonMinimizar;
    private JButton volverButton;
    private JLabel separadorCasero;
    private JLabel separadorCasero2;
    //////////////////////////////////////

    public registrarseMenu(JFrame frame) {
        textArea1.setVisible(false);
        botonRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contrasena = indicarContraseña.getText();
                if (passwordF(contrasena)) {
                    try {
                        String usuario = indicarUsuario.getText();
                        if (registrar(usuario,contrasena)) {
                            // Creará una notificación indicando que se ha registrado el usuario
                            JOptionPane.showMessageDialog(null, "Te has registrado con exito");
                            frame.setVisible(false);
                            userMenu.main(usuario);
                        } else {
                            JOptionPane.showMessageDialog(null, "El usuario ya existe");
                        }
                    } catch (SQLException ex) {
                    }
                } else {
                    frame.setSize(340, 240);
                    textArea1.setVisible(true);
                    textArea1.setText("La contraseña debe tener: \n-8 carácteres o más. \n-Un carácter especial.    -_#!$%&'()*+,./ \n-Un carácter numérico. \n-Una letra minúscula. \n-Una letra mayúscula.");
                }
            }
        });
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
    }
    //Comprueba la contraseña que el usuario ha indicado en el "formulario"
    private static boolean passwordF(String password) {
        //Valores a comprobar (Siempre false por defecto)
        boolean mayuscula = false;
        boolean minuscula = false;
        boolean numero = false;
        boolean especial = false;
        boolean correcto;

        for (int i = 0; i < password.length(); i++) {
            if ((int) password.charAt(i) < 91 && (int) password.charAt(i) > 64) {
                mayuscula = true;
            } else if ((int) password.charAt(i) < 123 && (int) password.charAt(i) > 96) {
                minuscula = true;
            } else if (((int) password.charAt(i) < 48 && (int) password.charAt(i) > 32) || password.charAt(i) == '_' || password.charAt(i) == '-' || password.charAt(i) == '#') {
                especial = true;
            } else if ((int) password.charAt(i) < 58 && (int) password.charAt(i) > 47) {
                numero = true;
            }
        }
        if ((password.length() < 8 || !especial || !numero || !minuscula || !mayuscula)) {
            correcto = false;
        } else {
            correcto = true;
        }
        return correcto;
    }

    public static boolean registrar(String user, String contrasena) throws SQLException {

        //Credenciales de la base de datos
        final String jdbcUrl = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
        final String username = "postgres.ghlnlytcmdkthuiywwye";
        final String password1 = "Cards_Friends_";

        boolean resultado = false;
        boolean repetido = false;
        try (Connection connectSQL = DriverManager.getConnection(jdbcUrl, username, password1)) {
            // Consulta para verificar si el usuario ya existe
            String selectQuery = "SELECT usuario FROM users WHERE usuario = ?";
            try (PreparedStatement selectStmt = connectSQL.prepareStatement(selectQuery)) {
                selectStmt.setString(1, user);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    // Usuario ya existe
                    repetido = true;
                }
            }

            if (!repetido) {
                // Consulta para insertar un nuevo usuario
                String insertQuery = "INSERT INTO users (usuario, password) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connectSQL.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, user);
                    insertStmt.setString(2, contrasena);
                    insertStmt.executeUpdate();
                    resultado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return resultado;
    }


    public static void main() {
        JFrame frame = new JFrame("CardsFriends");
        frame.setContentPane(new registrarseMenu(frame).panelPrincipal);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(320, 140);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());
    }
}
