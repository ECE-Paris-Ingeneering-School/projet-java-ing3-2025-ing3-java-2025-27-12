import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FenetreConnexion extends JFrame {
    public FenetreConnexion() {
        setTitle("Connexion");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 250, 240));
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Connexion", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(new Color(30, 30, 60));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        formPanel.setBackground(new Color(255, 250, 240));

        JTextField tfEmail = new JTextField();
        JPasswordField tfPassword = new JPasswordField();

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setBackground(new Color(46, 139, 87));
        btnConnexion.setForeground(Color.WHITE);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBackground(new Color(205, 92, 92));
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.addActionListener(e -> {
            dispose();
            new FenetreAccueil();
        });

        JButton btnVersInscription = new JButton("Pas encore de compte ? Créer un compte");
        btnVersInscription.setForeground(Color.BLUE);
        btnVersInscription.setBorderPainted(false);
        btnVersInscription.setContentAreaFilled(false);
        btnVersInscription.addActionListener(e -> {
            dispose();
            new FenetreInscription();
        });

        btnConnexion.addActionListener(e -> {
            try (Connection conn = ConnexionBDD.getConnexion()) {
                String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tfEmail.getText());
                stmt.setString(2, new String(tfPassword.getPassword()));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String role = rs.getString("role");
                    String email = rs.getString("email");
                    String specialisation = rs.getString("specialisation");
                    int idUtilisateur = rs.getInt("id");
                    dispose();
                    new FenetrePrincipale(nom, prenom, email, role, specialisation, idUtilisateur);
                } else {
                    JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        formPanel.add(new JLabel("Email :"));
        formPanel.add(tfEmail);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(tfPassword);
        formPanel.add(btnAnnuler);
        formPanel.add(btnConnexion);

        add(titre, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(btnVersInscription, BorderLayout.SOUTH);

        setVisible(true);
    }
}
