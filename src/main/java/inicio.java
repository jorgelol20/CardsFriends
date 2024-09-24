import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class inicio {
    private JProgressBar progressBar1;
    private JPanel panel1;

    public static void main(String[] args) {
        try {
            comprobar();
            mainMenu.main();
        } catch (SQLException e) {
            System.exit(0);
        }

    }

    public static void comprobar() throws SQLException {

        /* En estas líneas se establece el contacto con la base de datos que está hospedada en
         *este caso en Supabase. En este caso el usuario y la contraseña de mysql son 'postgres.ghlnlytcmdkthuiywwye' y 'Cards_Friends_'.*/

        //Credenciales de la base de datos
        final String jdbcUrl = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
        final String username = "postgres.ghlnlytcmdkthuiywwye";
        final String password = "Cards_Friends_";

        try {
            // Registrar el driver manualmente
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        }
        //Esto se ejecutará si no
        catch (ClassNotFoundException e) {

        } //Si no se puede conectar a la BD, saltará un mensaje de error
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "La base de datos no se encuentra disponible en estos momentos.", "ERROR INÉSPERADO", JOptionPane.WARNING_MESSAGE);
        }
    }
}
