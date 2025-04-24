import javax.swing.*;
import java.awt.*;

import java.sql.*;


/**
 * Fenêtre Swing permettant à un nouvel utilisateur de créer un compte.
 * Cette classe fournit un formulaire d'inscription avec validation des champs.
 * Le rôle est fixé automatiquement à "patient".
 *
 * @author Mathis
 * @version 1.0
 */

public class FenetreInscription extends JFrame {
    /**
     * Constructeur qui initialise la fenêtre d'inscription.
     * Crée les composants graphiques, gère les événements de bouton,
     * et enregistre un utilisateur dans la base de données.
     */
    public FenetreInscription() {
        setTitle("Inscription");


        setSize(800, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 240, 255));

        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Créer un compte", SwingConstants.CENTER);
            titre.setFont(new Font("Arial", Font.BOLD, 26));

            titre.setForeground(new Color(30, 30, 60));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));


            formPanel.setBackground(new Color(230, 240, 255));

        JTextField tfNom = new JTextField();
        JTextField tfPrenom = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField tfPassword = new JPasswordField();

        JButton btnInscrire = new JButton("S'inscrire");
        btnInscrire.setBackground(new Color(0, 120, 215));
        btnInscrire.setForeground(Color.WHITE);

        JButton btnVersConnexion = new JButton("Déjà un compte ? Se connecter");
        btnVersConnexion.setForeground(Color.BLUE);
        btnVersConnexion.setBorderPainted(false);
        btnVersConnexion.setContentAreaFilled(false);
        btnVersConnexion.addActionListener(e -> {
            dispose();
            new FenetreConnexion();
        });

        btnInscrire.addActionListener(e -> {
            String nom = tfNom.getText().trim();
            String prenom = tfPrenom.getText().trim();
            String email = tfEmail.getText().trim();
            String mdp = new String(tfPassword.getPassword()).trim();

            // Vérification des champs
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification du format email
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Adresse email invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = ConnexionBDD.getConnexion()) {
                String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, specialisation) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, email);
                stmt.setString(4, mdp);
                stmt.setString(5, "patient");
                stmt.setNull(6, Types.VARCHAR); // Pas de spécialisation

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                dispose();
                new FenetreConnexion();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        formPanel.add(new JLabel("Nom :")); formPanel.add(tfNom);


        formPanel.add(new JLabel("Prénom :")); formPanel.add(tfPrenom);
        formPanel.add(new JLabel("Email :")); formPanel.add(tfEmail);


        formPanel.add(new JLabel("Mot de passe :")); formPanel.add(tfPassword);
        formPanel.add(new JLabel("")); formPanel.add(btnInscrire);

        add (titre, BorderLayout.NORTH);
        add (formPanel, BorderLayout.CENTER);
        add (btnVersConnexion, BorderLayout.SOUTH);

        setVisible(true);
    }


}
