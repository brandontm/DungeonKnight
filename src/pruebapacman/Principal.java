package pruebapacman;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;

public class Principal extends JFrame {

    public Principal() {
        add(new Board());

        setTitle("Dungeon Knight");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 430);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
       Principal ex = new Principal();
       ex.setVisible(true);
    }

}
