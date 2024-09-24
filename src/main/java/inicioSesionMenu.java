import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class inicioSesionMenu {
    //Todos los componentes de el menú////////
    private JPanel inicioSesionPanel;
    private JTextField textUsuario;
    private JPasswordField contraseña;
    private JLabel Texto1;
    private JLabel Texto2;
    private JButton botonSalir;
    private JButton botonMinimizar;
    private JButton botonInicio;
    private JLabel logoPokeCards;
    private JButton volverButton;
    //////////////////////////////////////////

    public inicioSesionMenu(JFrame frame) {
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = "";
                String contrasena = "";
                usuario = textUsuario.getText();
                contrasena = contraseña.getText();
                //Intenta iniciar sesión yendo a la función "iniciar"
                try {
                    if (iniciar(usuario, contrasena)) {
                        /*Si las credenciales son correctas saltará una ventana
                         *emergente indicando que has iniciado sesión con éxito y entrarás en el menú de usuario*/
                        JOptionPane.showMessageDialog(botonInicio, "Has iniciado sesión con exito");
                        frame.setVisible(false);
                        userMenu.main(usuario);
                        //Si las credenciales son incorrectas saltará una ventana emergente indicando lo.
                    } else {
                        JOptionPane.showMessageDialog(botonInicio, "El usuario o la contraseña son incorrectos", "Error al iniciar sesión", JOptionPane.WARNING_MESSAGE);
                    }
                        //En caso de error con la base de datos, saltará una ventana emergente indicando lo
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "ERROR", JOptionPane.WARNING_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //Barra superior del menú
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

    ////////////////////////////////////////////////////////
    //////////////////Inicio de sesión//////////////////////
    ////////////////////////////////////////////////////////


    private static boolean iniciar(String user, String contrasena) throws SQLException, ClassNotFoundException {

        //Credenciales de la base de datos
        final String jdbcUrl = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?serverTimezone=UTC";
        final String username = "postgres.ghlnlytcmdkthuiywwye";
        final String password1 = "Cards_Friends_";

        //Resultado siempre devolverá false por defecto.
        boolean resultado = false;

        //Estas variables recogerán su valor de la base de datos.
        String usuario = "";
        String password = "";

        //Realiza la conexión con la base de datos
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password1);

        //Crea un Statement para conectarse
        Statement statement = connection.createStatement();

        /* A la conexión le añade la solicitud SELECT * FROM USUARIOS. Este query en MySQL
         * solicita todos los datos de la tabla USERS.*/
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

        /* Con este while recorremos la base de datos hasta que ya no haya más filas en
         * esta (se detiene de forma automática). Aquí le daremos valor en cada iteración a usuario y password
         * y comprueba si son iguales a los introducidos por el usuario (user, contrasena)*/

        while (resultSet.next()) {
            usuario = (resultSet.getString("usuario"));
            password = (resultSet.getString("password"));

            //Esto es solo para hacer debug.
                /* System.out.println(STR."INTRODUCIDA: \{user} | MySQL: \{usuario}");
                 System.out.println(STR."INTRODUCIDA: \{contrasena} | MySQL: \{password}"); */

            if (usuario.equals(user) && contrasena.equals(password)) {
                resultado = true;
            }
        }
        // Cerrar la conexión a la base de datos para liberar recursos.
        connection.close();
        // Cerrar el objeto Statement para liberar recursos y evitar posibles problemas de fuga de recursos.
        statement.close();
        // Cerrar el objeto ResultSet para liberar recursos y evitar posibles problemas de fuga de recursos.
        resultSet.close();

        return resultado;
    }

    public static void main() {
        JFrame frame = new JFrame("CardFriends");
        frame.setContentPane(new inicioSesionMenu(frame).inicioSesionPanel);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(320, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);;
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());
    }

}
