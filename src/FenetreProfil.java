

// FenetreProfil.java
import javax.swing.*;
import java.awt.*;

public class FenetreProfil extends JFrame {
    public FenetreProfil(String nom, String prenom, String email, String role, String specialisation) {
        setTitle("Profil utilisateur");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 255, 250));

        setLayout(new GridLayout(6, 1, 5, 5));
        add(new JLabel("Nom : " + nom));
        add(new JLabel("Prénom : " + prenom));
        add(new JLabel("Email : " + email));
        add(new JLabel("Rôle : " + role));

        if (role.equals("specialiste")) {
            add(new JLabel("Spécialisation : " + specialisation));
        } else {
            add(new JLabel("Spécialisation : N/A"));
        }

        setVisible(true);
    }
}
