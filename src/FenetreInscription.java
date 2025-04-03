import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FenetreInscription extends JFrame {
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

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        formPanel.setBackground(new Color(230, 240, 255));

        JTextField tfNom = new JTextField();
        JTextField tfPrenom = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField tfPassword = new JPasswordField();
        JComboBox<String> cbRole = new JComboBox<>(new String[]{"patient", "specialiste", "admin"});
        JTextField tfSpecialisation = new JTextField();
        JLabel lblSpecialisation = new JLabel("Spécialisation :");

        lblSpecialisation.setVisible(false);
        tfSpecialisation.setVisible(false);

        cbRole.addActionListener(e -> {
            boolean estSpec = cbRole.getSelectedItem().equals("specialiste");
            lblSpecialisation.setVisible(estSpec);
            tfSpecialisation.setVisible(estSpec);
        });

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
            try (Connection conn = ConnexionBDD.getConnexion()) {
                String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, specialisation) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tfNom.getText());
                stmt.setString(2, tfPrenom.getText());
                stmt.setString(3, tfEmail.getText());
                stmt.setString(4, new String(tfPassword.getPassword()));
                stmt.setString(5, cbRole.getSelectedItem().toString());
                stmt.setString(6, cbRole.getSelectedItem().equals("specialiste") ? tfSpecialisation.getText() : null);

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
        formPanel.add(new JLabel("Rôle :")); formPanel.add(cbRole);
        formPanel.add(lblSpecialisation); formPanel.add(tfSpecialisation);
        formPanel.add(new JLabel("")); formPanel.add(btnInscrire);

        add(titre, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(btnVersConnexion, BorderLayout.SOUTH);

        setVisible(true);
    }
}
