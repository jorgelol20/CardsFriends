import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class mainMenu {
    //Todos los componentes de el menú
    private JPanel mainPanel;
    private JButton botonInicio;
    private JButton botonRegistro;
    private JButton botonMinimizar;
    private JButton botonSalir;
    private JLabel logo;
    private JLabel separadorCasero;
    private JButton ayudaButton;

    ////////////////////////////////////
    public mainMenu(JFrame frame) {
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                inicioSesionMenu.main();
            }
        });
        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                registrarseMenu.main();
            }
        });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"Si has olvidado la contraseña, el usuario \n o tienes cualquier otra duda, escriba a jorgejorgemonovar@gmail.com","Panel Ayuda ",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    public static void main() {
        //Creación de una ventana emergente para el programa. Si no, no se muestra nada.
        //Le da nombre a la ventana.
        JFrame frame = new JFrame("CardFriends");
        //Indica el panel que tiene que abrirse
        frame.setContentPane(new mainMenu(frame).mainPanel);
        //Quita la barra de acciones superior de Windows
        frame.setUndecorated(true);
        //Indica que ocurre cuando se cierra la ventana
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //Ajusta el tamaño justo para la ventana
        frame.setSize(340, 160);
        //Indica que la ventana es visible
        frame.setVisible(true);
        //Indica que la ventana no es reajustable
        frame.setResizable(false);
        //Indica que aparezca la ventana en el centro de la pantalla.
        frame.setLocationRelativeTo(null);
        //Indicamos el icono que tendrá en la barra de tareas
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());

    }
}
