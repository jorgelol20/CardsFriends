import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ajustes {

    private JPanel panelAjustes;
    private JButton botonMinimizar;
    private JButton botonSalir;
    private JButton volverButton;
    private JLabel alerta;
    private JPanel panelSuperior;

    public ajustes(String usuario, JFrame frame) {

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
                frame.dispose();
                userMenu.main(usuario);
            }
        });
    }

    public static void main(String usuario) {
        JFrame frame = new JFrame("Ajustes");
        frame.setUndecorated(true);
        frame.setContentPane(new ajustes(usuario, frame).panelAjustes);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/main/resources/icons/pokeball.png").getImage());
    }
}
