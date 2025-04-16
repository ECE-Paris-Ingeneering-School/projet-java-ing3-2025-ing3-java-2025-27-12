import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreAccueil extends JFrame {

    public FenetreAccueil() {
        setTitle("S.P.A.M - Accueil");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Couleurs & polices personnalisées ===
        Color couleurFond = new Color(245, 250, 255);
        Color couleurAccent = new Color(60, 120, 180);
        Font titreFont = new Font("Serif", Font.BOLD, 30);
        Font sousTitreFont = new Font("SansSerif", Font.PLAIN, 16);
        Font menuFont = new Font("SansSerif", Font.BOLD, 15);

        getContentPane().setBackground(couleurFond);

        // === MENU BAR ===
        JMenuBar menuBar = new JMenuBar();
        JMenu menuInfos = new JMenu("Infos");
        JMenu menuRecherche = new JMenu("Recherche");
        JMenu menuConnexion = new JMenu("Connexion");

        menuInfos.setFont(menuFont);
        menuRecherche.setFont(menuFont);
        menuConnexion.setFont(menuFont);

        menuBar.add(menuInfos);
        menuBar.add(menuRecherche);
        menuBar.add(menuConnexion);

        setJMenuBar(menuBar);

        // === BARRE SUPÉRIEURE ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(couleurFond);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // === Logo à droite ===
        ImageIcon logoIcon = new ImageIcon("src/Images/Logo.png");
        Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // === Texte à gauche ===
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

        // === CENTRE : panneau principal ===
        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel messageAccueil = new JLabel("Bienvenue sur votre plateforme de santé !");
        messageAccueil.setFont(new Font("SansSerif", Font.BOLD, 22));
        messageAccueil.setForeground(new Color(40, 70, 100));
        messageAccueil.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea description = new JTextArea(
                "\nS.P.A.M vous permet de consulter les spécialistes disponibles,\n" +
                        "d'explorer les créneaux ouverts selon vos besoins,\n" +
                        "et de réserver vos rendez-vous simplement, efficacement, et gratuitement.\n\n" +
                        "🧑‍⚕️ Découvrez les profils des spécialistes.\n" +
                        "📅 Accédez à notre moteur de recherche intelligent.\n" +
                        "🔒 Connectez-vous pour bénéficier de toutes les fonctionnalités."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        centre.add(messageAccueil);
        centre.add(Box.createVerticalStrut(20));
        centre.add(description);

        add(centre, BorderLayout.CENTER);

        // === MENU ACTIONS ===
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

        // === Apparition animée des éléments (exemple simple) ===
        Timer animationTimer = new Timer(15, null);
        final int[] yOffset = {30};
        animationTimer.addActionListener(evt -> {
            if (yOffset[0] > 0) {
                centre.setLocation(centre.getX(), centre.getY() - 1);
                yOffset[0]--;
            } else {
                ((Timer) evt.getSource()).stop();
            }
        });
        animationTimer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreAccueil::new);
    }
}