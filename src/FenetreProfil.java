/*
import javax.swing.*;
import java.awt.*;

public class FenetreProfil extends JFrame {

    public FenetreProfil(String nom, String prenom, String email, String role, String specialisation) {
        setTitle("Application - Rendez-vous spécialiste");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Barre de menu ===
        JMenuBar menuBar = new JMenuBar();
        JMenu menuInfos = new JMenu("Infos");
        JMenu menuRDV = new JMenu("Prendre rendez-vous");
        JMenu menuCompte = new JMenu("Votre compte");
        JMenu menuMesRDV = new JMenu("Mes rendez-vous");

        menuBar.add(menuInfos);
        menuBar.add(menuRDV);
        menuBar.add(menuCompte);
        menuBar.add(menuMesRDV);
        setJMenuBar(menuBar);

        // === Contenu principal (infos) ===
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 255, 250));
        panel.setLayout(new BorderLayout());

        JLabel titre = new JLabel("Bienvenue sur l'application de prise de rendez-vous", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JTextArea texteInfos = new JTextArea(
                "Notre application vous permet de consulter les disponibilités des spécialistes, " +
                        "de prendre rendez-vous rapidement et de suivre votre historique médical.\n\n" +
                        "Voici quelques-uns de nos spécialistes disponibles :\n\n" +
                        "🔹 Dr. Marie Dupont - Cardiologue\n" +
                        "🔹 Dr. Ahmed Benali - Dermatologue\n" +
                        "🔹 Dr. Sophie Lambert - Pédiatre\n" +
                        "🔹 Dr. Jean Durand - Psychologue\n" +
                        "🔹 Dr. Clara Nguyen - Gastro-entérologue\n\n" +
                        "Cliquez sur l'onglet 'Prendre rendez-vous' pour commencer votre parcours de santé."
        );
        texteInfos.setEditable(false);
        texteInfos.setFont(new Font("SansSerif", Font.PLAIN, 15));
        texteInfos.setBackground(new Color(245, 255, 250));
        texteInfos.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        panel.add(titre, BorderLayout.NORTH);
        panel.add(texteInfos, BorderLayout.CENTER);

        // === Affichage dans la fenêtre ===
        add(panel);
        setVisible(true);
    }
}
*/