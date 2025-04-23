import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class FenetreAccueil extends JFrame {

    public FenetreAccueil() {
        setTitle("S.P.A.M - Accueil");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color couleurFond =
                new Color(245, 250, 255);
        Color couleurAccent =
                new Color(60, 120, 180);
        Font titreFont =
                new Font("Serif", Font.BOLD, 30);
            Font sousTitreFont =
                new Font("SansSerif", Font.PLAIN, 16);
        Font menuFont =
                new Font("SansSerif", Font.BOLD, 15);

        getContentPane().
                setBackground(couleurFond);

        JMenuBar menuBar =
                new JMenuBar();
        JMenu menuInfos =
                new JMenu("Infos");
        JMenu menuRecherche =
                new JMenu("Recherche");
        JMenu menuConnexion =
                new JMenu("Connexion");

        menuInfos.setFont(menuFont);
        menuRecherche.setFont(menuFont);


        menuConnexion.setFont(menuFont);

        menuBar.add(menuInfos);
       // menuBar.add(menuRecherche);
        //menuBar.add(menuRecherche);


            menuBar.add(menuConnexion);

        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setBackground(couleurFond);
            topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        ImageIcon logoIcon = new ImageIcon("src/Images/Logo.png");
            Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);


        JLabel logoLabel = new JLabel(logoIcon);
                logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);


                JPanel textePanel = new JPanel();
        textePanel.setLayout(new BoxLayout(textePanel, BoxLayout.Y_AXIS));
        textePanel.setBackground(couleurFond);

        JLabel titre = new JLabel("S.P.A.M");

        titre.setFont(titreFont);
            titre.setForeground(couleurAccent);
        JLabel sousTitre = new JLabel("Service de Prise de rendez-vous Avec Médecins");
            sousTitre.setFont(sousTitreFont);
        sousTitre.setForeground(new Color(80, 80, 80));

        textePanel.add(titre);
         textePanel.add(sousTitre);

        topPanel.add(textePanel, BorderLayout.WEST);

        topPanel.add(logoLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

            JPanel centre =
                    new JPanel();
            centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(Color.WHITE);

        centre.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel messageAccueil =
                new JLabel("Bienvenue sur votre plateforme de santé !");
        messageAccueil.setFont(new Font("SansSerif", Font.BOLD, 22));
            messageAccueil.setForeground(new Color(40, 70, 100));


        messageAccueil.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea description = new JTextArea(
                "S.P.A.M vous permet de consulter les spécialistes disponibles,\n" +
                        "d'explorer les créneaux ouverts selon vos besoins,\n" +
                        "et de réserver vos rendez-vous simplement, efficacement, et gratuitement.\n\n" +
                        " Découvrez les profils des spécialistes ci-dessous.\n" +
                        " Accédez à notre moteur de recherche intelligent.\n" +
                        " Connectez-vous pour bénéficier de toutes les fonctionnalités."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setLineWrap(true);


        description.
                setWrapStyleWord(true);
        description
                .setEditable(false);
        description
                .setOpaque(false);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        centre.add(messageAccueil);
        centre.add(Box.createVerticalStrut(20));
        centre.add(description);


        centre.add(Box.createVerticalStrut(30));

        try {
            Connection conn = ConnexionBDD
                    .getConnexion();
            UtilisateurDAO dao = new UtilisateurDAO(conn);
            CreneauDAO creneauDAO = new CreneauDAO(conn);

            List<Utilisateur> specialistes = dao.getSpecialistes();

            for (Utilisateur u : specialistes) {
                double moyenne =
                        creneauDAO.getMoyenneNotePourSpecialiste(u.getId());

                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                        card.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                                BorderFactory.createEmptyBorder(10, 15, 10, 15)
                        ));
                card.setBackground(new Color(250, 250, 250));
                card.setMaximumSize(new Dimension(500, 130));
                card.setAlignmentX(Component.CENTER_ALIGNMENT);

                card.add(new JLabel(" " + u.getPrenom() + " " + u.getNom()));


                card.add(new JLabel(" Ville : " + (u.getVille() != null ? u.getVille() : "Non renseignée")));
                card.add(new JLabel(" Spécialité : " + (u.getSpecialisation() != null ? u.getSpecialisation() : "Non renseignée")));
                card.add(new JLabel(" Email : " + u.getEmail()));
                card.add(new JLabel(" Moyenne des notes : " + String.format("%.2f", moyenne) + " / 5"));

                centre.add(card);
                centre.add(Box.createVerticalStrut(15));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            centre.add(new JLabel("Impossible charger spécialiste"));
        }

        JScrollPane scrollPane = new JScrollPane(centre);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        menuInfos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Accédez à toutes les informations utiles sur l'application S.P.A.M.", "Infos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuRecherche.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Recherche ouverte à tous. Connectez-vous pour réserver !", "Recherche", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuConnexion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new FenetreConnexion();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreAccueil::new);
    }
}
