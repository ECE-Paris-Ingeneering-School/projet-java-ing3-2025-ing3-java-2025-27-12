
// FenetreAccueil.java
import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {
    public Fenetre() {
        setTitle("Accueil");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnConnexion = new JButton("Se connecter");
        JButton btnInscription = new JButton("S'inscrire");

        btnConnexion.addActionListener(e -> {
            dispose();
            new FenetreConnexion();
        });
        btnInscription.addActionListener(e -> {
            dispose();
            new FenetreInscription();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(btnConnexion);
        panel.add(btnInscription);
        add(panel);
        setVisible(true);
    }
}

