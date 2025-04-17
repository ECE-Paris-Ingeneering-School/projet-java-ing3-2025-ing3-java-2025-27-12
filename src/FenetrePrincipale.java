import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import Modele.Avis;

 // Remplace `your.package.name` par le vrai package

public class FenetrePrincipale extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private int idUtilisateurConnecte;
    private JPanel creneauPanel;
   // private int semaineAffichee = 0;
    private CreneauDAO creneauDAO;
    private LocalDate semaineAffichee = LocalDate.now();
    // commence au lundi de la semaine
   // private JComboBox<Utilisateur> comboSpecialistes;



    private String roleUtilisateurConnecte;

    private JComboBox<Utilisateur> comboSpecialistes;
    public FenetrePrincipale(String nom, String prenom, String email, String role, String specialisation, int idUtilisateur) {
    //    this.idUtilisateurConnecte = idUtilisateur;
        this.idUtilisateurConnecte = idUtilisateur;
        this.roleUtilisateurConnecte = role;
        setTitle("Application - Rendez-vous spécialiste");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);




        // === Barre de menu ===
        JMenuBar menuBar = new JMenuBar();
        JMenu menuInfos = new JMenu("Infos");
        JMenu menuRDV = new JMenu("Prendre rendez-vous");
        JMenu menuCompte = new JMenu("Votre compte");
        JMenu menuMesRDV = new JMenu("Mes rendez-vous");


        JMenu menuRecherche = new JMenu("Recherche");
        menuBar.add(menuRecherche);


        menuBar.add(menuInfos);
        menuBar.add(menuRDV);
        menuBar.add(menuCompte);
        menuBar.add(menuMesRDV);
        setJMenuBar(menuBar);

        // === Panels ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel panelInfos = createInfosPanel();
        JPanel panelPrendreRDV = createPrendreRDVPanel();
        JPanel panelCompte = createComptePanel(nom, prenom, email, role, specialisation);
        JPanel panelMesRDV = createMesRDVPanel();

        mainPanel.add(panelInfos, "Infos");
        mainPanel.add(panelPrendreRDV, "RDV");
        mainPanel.add(panelCompte, "Compte");
        mainPanel.add(panelMesRDV, "MesRDV");


        add(mainPanel);

        // === Listeners pour menus ===
        menuInfos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "Infos");
            }
        });

        menuRDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "RDV");
            }
        });

        menuCompte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "Compte");
            }
        });


        menuMesRDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Supprimer l'ancien panel
                mainPanel.remove(mainPanel.getComponent(3)); // si c'est le 4e ajouté (index 3)

                // Créer un nouveau panel à jour
                JPanel nouveauPanelMesRDV = createMesRDVPanel();
                mainPanel.add(nouveauPanelMesRDV, "MesRDV");

                // Afficher le panel
                cardLayout.show(mainPanel, "MesRDV");
                mainPanel.revalidate();
                mainPanel.repaint();

            }
        });
        menuRecherche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPanel recherchePanel = createRecherchePanel();
                mainPanel.remove(mainPanel.getComponent(4)); // ou ajuste l'index si besoin
                mainPanel.add(recherchePanel, "Recherche");
                cardLayout.show(mainPanel, "Recherche");
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        mainPanel.add(createRecherchePanel(), "Recherche");



        setVisible(true);
    }

/*
    private JPanel createInfosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 255, 250));

        JLabel titre = new JLabel("Bienvenue sur l'application de prise de rendez-vous", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JTextArea infos = new JTextArea(
                "Notre application vous permet de consulter les disponibilités des spécialistes, " +
                        "de prendre rendez-vous rapidement et de suivre votre historique médical.\n\n" +
                        "Voici quelques-uns de nos spécialistes :\n\n" +
                        "🔹 Dr. Marie Dupont - Cardiologue\n" +
                        "🔹 Dr. Ahmed Benali - Dermatologue\n" +
                        "🔹 Dr. Sophie Lambert - Pédiatre\n" +
                        "🔹 Dr. Jean Durand - Psychologue\n" +
                        "🔹 Dr. Clara Nguyen - Gastro-entérologue\n"
        );
        infos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infos.setEditable(false);
        infos.setBackground(panel.getBackground());
        infos.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        panel.add(titre, BorderLayout.NORTH);
        panel.add(infos, BorderLayout.CENTER);

        return panel;
    }
*/

private JPanel createInfosPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(245, 250, 255));

    Color couleurAccent = new Color(60, 120, 180);
    Font titreFont = new Font("Serif", Font.BOLD, 30);
    Font sousTitreFont = new Font("SansSerif", Font.PLAIN, 16);

    // === BARRE SUPÉRIEURE ===
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(new Color(245, 250, 255));
    topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    // Titre à gauche
    JPanel textePanel = new JPanel();
    textePanel.setLayout(new BoxLayout(textePanel, BoxLayout.Y_AXIS));
    textePanel.setBackground(new Color(245, 250, 255));

    JLabel titre = new JLabel("S.P.A.M");
    titre.setFont(titreFont);
    titre.setForeground(couleurAccent);
    JLabel sousTitre = new JLabel("Service de Prise de rendez-vous Avec Médecins");
    sousTitre.setFont(sousTitreFont);
    sousTitre.setForeground(new Color(80, 80, 80));

    textePanel.add(titre);
    textePanel.add(sousTitre);

    // Logo à droite
    ImageIcon logoIcon = new ImageIcon("src/Images/Logo.png");
    Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    logoIcon = new ImageIcon(img);
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    topPanel.add(textePanel, BorderLayout.WEST);
    topPanel.add(logoLabel, BorderLayout.EAST);

    // === CENTRE : Message d'accueil ===
    JPanel centre = new JPanel();
    centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
    centre.setBackground(Color.WHITE);
    centre.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

    JLabel messageAccueil = new JLabel("Bienvenue sur votre plateforme de santé !");
    messageAccueil.setFont(new Font("SansSerif", Font.BOLD, 22));
    messageAccueil.setForeground(new Color(40, 70, 100));
    messageAccueil.setAlignmentX(Component.CENTER_ALIGNMENT);

    JTextArea description = new JTextArea(
            "\\nS.P.A.M vous permet de consulter les spécialistes disponibles,\\n" +
                    "d'explorer les créneaux ouverts selon vos besoins,\\n" +
                    "et de réserver vos rendez-vous simplement, efficacement, et gratuitement.\\n\\n" +
                    "🧑‍⚕️ Découvrez les profils des spécialistes.\\n" +
                    "📅 Accédez à notre moteur de recherche intelligent.\\n" +
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

    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(centre, BorderLayout.CENTER);

    return panel;
}



    private JPanel createPrendreRDVPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 240));

        // === Panel navigation (boutons) ===
        JPanel navigation = new JPanel(new FlowLayout());
        JButton btnSemainePrecedente = new JButton("← Semaine précédente");
        JButton btnSemaineSuivante = new JButton("Semaine suivante →");
        navigation.add(btnSemainePrecedente);
        navigation.add(btnSemaineSuivante);

        // === Titre au-dessus ===
        JPanel header = new JPanel(new BorderLayout());
        JLabel titre = new JLabel("📅 Prise de rendez-vous", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(titre, BorderLayout.NORTH);
        header.add(navigation, BorderLayout.SOUTH);

        panel.add(header, BorderLayout.NORTH);  // ✅ ajoute le header complet

        // === Panel des créneaux ===
        creneauPanel = new JPanel();
        panel.add(new JScrollPane(creneauPanel), BorderLayout.CENTER);

        // === Bas du panel : combo + graphes
        JPanel basPanel = new JPanel();
        basPanel.setLayout(new BoxLayout(basPanel, BoxLayout.Y_AXIS));

        try {
            Connection conn = ConnexionBDD.getConnexion();
            creneauDAO = new CreneauDAO(conn);
            UtilisateurDAO userDao = new UtilisateurDAO(conn);

            comboSpecialistes = new JComboBox<>();
            for (Utilisateur u : userDao.getSpecialistes()) {
                comboSpecialistes.addItem(u);
            }

            comboSpecialistes.addActionListener(e -> {
                Utilisateur selected = (Utilisateur) comboSpecialistes.getSelectedItem();
                if (selected != null) {
                    creneauDAO.genererCreneauxMoisCompletSiManquants(selected.getId());
                    afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
                }
            });

            basPanel.add(comboSpecialistes);

            if (roleUtilisateurConnecte.equalsIgnoreCase("admin")) {
                basPanel.add(Box.createVerticalStrut(20));
                basPanel.add(createGraphiqueOccupationPanel());
                basPanel.add(Box.createVerticalStrut(10));
                basPanel.add(createGraphiqueIndividuelPanel()); // si cette méthode existe
            }

            panel.add(basPanel, BorderLayout.SOUTH);

            // ✅ Affichage initial dès ouverture
            if (comboSpecialistes.getItemCount() > 0) {
                Utilisateur selected = (Utilisateur) comboSpecialistes.getItemAt(0);
                creneauDAO.genererCreneauxMoisCompletSiManquants(selected.getId());
                afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            panel.add(new JLabel("Erreur de chargement des spécialistes."), BorderLayout.CENTER);
        }

        // === Listeners navigation ===
        btnSemainePrecedente.addActionListener(e -> {
            semaineAffichee = semaineAffichee.minusWeeks(1);
            Utilisateur selected = (Utilisateur) comboSpecialistes.getSelectedItem();
            if (selected != null) {
                afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
            }
        });

        btnSemaineSuivante.addActionListener(e -> {
            semaineAffichee = semaineAffichee.plusWeeks(1);
            Utilisateur selected = (Utilisateur) comboSpecialistes.getSelectedItem();
            if (selected != null) {
                afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
            }
        });

        return panel;
    }




    private void afficherCreneaux(int idSpecialiste, JPanel cible, CreneauDAO creneauDAO) {
        cible.removeAll();
        cible.setLayout(new GridLayout(5, 5, 10, 10)); // 5 jours × 5 créneaux

        List<Creneau> creneaux = creneauDAO.getCreneauxDisponiblesPour(idSpecialiste);

        for (Creneau c : creneaux) {
            JButton btn = new JButton(c.getDateHeure().toLocalDate() + " " + c.getDateHeure().toLocalTime());
            btn.setBackground(c.isDisponible() ? Color.GREEN : Color.RED);
            btn.setEnabled(c.isDisponible());

            btn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Confirmer ce rendez-vous ?");
                if (confirm == JOptionPane.YES_OPTION) {
                    creneauDAO.reserverCreneau(c.getId(), idUtilisateurConnecte);
                    afficherCreneaux(idSpecialiste, cible, creneauDAO); // mise à jour
                }
            });

            cible.add(btn);
        }

        cible.revalidate();
        cible.repaint();
    }

    private JPanel createComptePanel(String nom, String prenom, String email, String role, String specialisation) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel titre = new JLabel("👤 Mon Compte");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Nom : " + nom));
        panel.add(new JLabel("Prénom : " + prenom));
        panel.add(new JLabel("Email : " + email));
        panel.add(new JLabel("Rôle : " + role));
        panel.add(new JLabel("Spécialisation : " + (role.equalsIgnoreCase("specialiste") ? specialisation : "N/A")));

        panel.add(Box.createVerticalStrut(20));

        // === Bouton de déconnexion ===
        JButton deconnexionBtn = new JButton("Déconnexion");
        deconnexionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deconnexionBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Ferme la fenêtre actuelle
                new FenetreAccueil(); // Retour accueil
            }
        });

        // === Bouton d’ajout de spécialiste (visible uniquement pour admin) ===
        if ("admin".equalsIgnoreCase(role)) {
            JButton btnAjouterSpecialiste = new JButton("Ajouter un spécialiste");
            btnAjouterSpecialiste.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAjouterSpecialiste.addActionListener(e -> {
                JFrame ajoutFrame = new JFrame("Ajouter un spécialiste");
                ajoutFrame.setSize(400, 350);
                ajoutFrame.setLocationRelativeTo(null);
                ajoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel form = new JPanel();
                form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
                form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

                JTextField tfNom = new JTextField();
                JTextField tfPrenom = new JTextField();
                JTextField tfEmail = new JTextField();
                JTextField tfSpecialisation = new JTextField();
                JPasswordField tfMdp = new JPasswordField();
                JTextField tfRue = new JTextField();
                JTextField tfVille = new JTextField();


                form.add(new JLabel("Nom :")); form.add(tfNom);
                form.add(new JLabel("Prénom :")); form.add(tfPrenom);
                form.add(new JLabel("Email :")); form.add(tfEmail);
                form.add(new JLabel("Spécialisation :")); form.add(tfSpecialisation);
                form.add(new JLabel("Mot de passe :")); form.add(tfMdp);
                form.add(new JLabel("Rue :")); form.add(tfRue);
                form.add(new JLabel("Ville :")); form.add(tfVille);



                JButton btnValider = new JButton("Ajouter");
                btnValider.addActionListener(evt -> {
                    String nomSPE = tfNom.getText().trim();
                    String prenomSPE = tfPrenom.getText().trim();
                    String emailSPE = tfEmail.getText().trim();
                    String specialisationSPE = tfSpecialisation.getText().trim();
                    String rue = tfRue.getText().trim();
                    String ville = tfVille.getText().trim();

                    String mdp = new String(tfMdp.getPassword());

                    if (nomSPE.isEmpty() || emailSPE.isEmpty() || specialisationSPE.isEmpty() || mdp.isEmpty()) {
                        JOptionPane.showMessageDialog(ajoutFrame, "Tous les champs sont obligatoires.");
                        return;
                    }

                    try {
                        Connection conn = ConnexionBDD.getConnexion();

                        // Vérifie si l'email est déjà utilisé
                        String checkSQL = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
                        PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
                        checkStmt.setString(1, emailSPE);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        if (rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(ajoutFrame, "Cet email est déjà utilisé.");
                            return;
                        }

                        // Insertion du spécialiste
                        String sql = "INSERT INTO utilisateur (nom, prenom, email, specialisation, rue, ville, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?, ?, 'specialiste')";

                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, nomSPE);
                        ps.setString(2, prenomSPE);
                        ps.setString(3, emailSPE);
                        ps.setString(4, specialisationSPE);
                        ps.setString(5, rue);
                        ps.setString(6, ville);
                        ps.setString(7, mdp);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(ajoutFrame, "Spécialiste ajouté !");
                        ajoutFrame.dispose();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ajoutFrame, "Erreur lors de l'ajout du spécialiste.");
                    }
                });

                form.add(Box.createVerticalStrut(15));
                form.add(btnValider);

                ajoutFrame.add(form);
                ajoutFrame.setVisible(true);
            });

            panel.add(Box.createVerticalStrut(20));
            panel.add(btnAjouterSpecialiste);
        }

        panel.add(Box.createVerticalStrut(15));
        panel.add(deconnexionBtn);
        return panel;
    }

    private JPanel createRecherchePanel() {
        // Si l'utilisateur est un patient → il cherche des créneaux disponibles
        if (roleUtilisateurConnecte.equalsIgnoreCase("patient")) {
            return createRecherchePatientPanel();
        }

        // Si l'utilisateur est un spécialiste → il cherche ses patients et leurs RDV
        if (roleUtilisateurConnecte.equalsIgnoreCase("specialiste")) {
            return createRechercheSpecialistePanel();
        }

        // Sinon (par précaution, ou si un rôle inconnu)
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Rôle utilisateur inconnu ou non pris en charge.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    private JPanel createRechercheSpecialistePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("🔍 Rechercher un patient");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));

        JPanel formPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Rechercher");
        formPanel.add(new JLabel("Nom, prénom ou email :"));
        formPanel.add(searchField);
        formPanel.add(searchBtn);
        panel.add(formPanel);

        JComboBox<Utilisateur> listePatients = new JComboBox<>();
        panel.add(Box.createVerticalStrut(10));
        panel.add(listePatients);

        JTextArea resultArea = new JTextArea(10, 60);
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        panel.add(scroll);

        JPanel mailPanel = new JPanel();
        JButton mailBtn = new JButton("Envoyer un mail");
        mailBtn.setVisible(false);
        mailPanel.add(mailBtn);
        panel.add(mailPanel);

        // Action du bouton "Rechercher"
        searchBtn.addActionListener(e -> {
            String critere = searchField.getText().trim();
            listePatients.removeAllItems();
            resultArea.setText("");
            mailBtn.setVisible(false);

            if (critere.isEmpty()) {
                resultArea.setText("❌ Veuillez entrer un nom, prénom ou email.");
                return;
            }

            try {
                Connection conn = ConnexionBDD.getConnexion();
                UtilisateurDAO userDao = new UtilisateurDAO(conn);
                List<Utilisateur> patients = userDao.chercherPatientsPourSpecialiste(idUtilisateurConnecte, critere);

                if (patients.isEmpty()) {
                    resultArea.setText("Aucun patient trouvé.");
                } else {
                    for (Utilisateur u : patients) {
                        listePatients.addItem(u);
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                resultArea.setText("❌ Erreur de recherche en base.");
            }
        });

        // Action quand on sélectionne un patient
        listePatients.addActionListener(e -> {
            Utilisateur patient = (Utilisateur) listePatients.getSelectedItem();
            if (patient == null) return;

            resultArea.setText("");
            mailBtn.setVisible(true);
            mailBtn.setActionCommand(patient.toString());

            try {
                Connection conn = ConnexionBDD.getConnexion();
                CreneauDAO dao = new CreneauDAO(conn);
                List<String> infos = dao.rechercherPatientEtRendezVous(idUtilisateurConnecte, patient.getEmail());

                for (String ligne : infos) {
                    if (!ligne.startsWith("EMAIL:")) {
                        resultArea.append(ligne + "\n");
                    } else {
                        String email = ligne.substring(6);
                        mailBtn.addActionListener(evt -> {
                            try {
                                Desktop.getDesktop().mail(new URI("mailto:" + email));
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel, "Erreur lors de l'ouverture du mail.");
                            }
                        });
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                resultArea.setText("❌ Erreur lors du chargement des informations.");
            }
        });

        return panel;
    }




    private JPanel createRecherchePatientPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("🔎 Rechercher des créneaux disponibles");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titre);
        panel.add(Box.createVerticalStrut(15));

        JPanel formPanel = new JPanel(new FlowLayout());

        JLabel specialiteLabel = new JLabel("Spécialité :");
        JComboBox<String> specialiteBox = new JComboBox<>();
        try {
            Connection conn = ConnexionBDD.getConnexion();
            UtilisateurDAO dao = new UtilisateurDAO(conn);
            List<String> specialites = dao.getSpecialitesDisponibles();
            for (String s : specialites) {
                specialiteBox.addItem(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            specialiteBox.addItem("Aucune spécialité trouvée");
        }


        JLabel dateLabel = new JLabel("Date (aaaa-mm-jj) :");
        JTextField dateField = new JTextField(10);

        JButton rechercherBtn = new JButton("Rechercher");

        formPanel.add(specialiteLabel);
        formPanel.add(specialiteBox);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(rechercherBtn);

        panel.add(formPanel);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        panel.add(scrollPane);

        // Action du bouton Rechercher
        rechercherBtn.addActionListener(e -> {
            resultPanel.removeAll();

            String specialite = (String) specialiteBox.getSelectedItem();
            String dateStr = dateField.getText();

            try {
                LocalDate date = LocalDate.parse(dateStr);
                Connection conn = ConnexionBDD.getConnexion();
                CreneauDAO dao = new CreneauDAO(conn);
                List<Creneau> resultats = dao.rechercherCreneauxComplet(specialite, date);

                if (resultats.isEmpty()) {
                    resultPanel.add(new JLabel("Aucun créneau disponible pour ce jour."));
                } else {
                    for (Creneau c : resultats) {
                        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        ligne.setMaximumSize(new Dimension(700, 40));
                        String texte = "📅 " + c.getDateHeure() + " - Dr. " +
                                c.getPrenomSpecialiste() + " " + c.getNomSpecialiste() +
                                " (" + c.getSpecialisation() + ")";
                        JLabel lbl = new JLabel(texte);

                        JButton reserver = new JButton("Réserver");
                        reserver.setBackground(Color.GREEN);

                        reserver.addActionListener(ev -> {
                            int confirm = JOptionPane.showConfirmDialog(panel, "Confirmer la réservation ?");
                            if (confirm == JOptionPane.YES_OPTION) {
                                dao.reserverCreneau(c.getId(), idUtilisateurConnecte);
                                JOptionPane.showMessageDialog(panel, "Créneau réservé !");
                                rechercherBtn.doClick(); // Recharge la recherche
                            }
                        });

                        ligne.add(lbl);
                        ligne.add(reserver);
                        resultPanel.add(ligne);
                    }
                }

                resultPanel.revalidate();
                resultPanel.repaint();

            } catch (Exception ex) {
                resultPanel.removeAll();
                resultPanel.add(new JLabel("❌ Erreur : Veuillez entrer une date au format aaaa-mm-jj"));
                resultPanel.revalidate();
                resultPanel.repaint();
            }
        });

        return panel;
    }


    private JPanel createMesRDVPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 245, 238));

        JLabel titre = new JLabel("📆 Mes rendez-vous");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnFuturs = new JButton("📅 À venir");
        JButton btnPasses = new JButton("📜 Passés");
        btnPanel.add(btnFuturs);
        btnPanel.add(btnPasses);
        panel.add(btnPanel);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        panel.add(scrollPane);

        ActionListener refresh = (e) -> {
            resultPanel.removeAll();
            boolean afficherAVenir = (e.getSource() == btnFuturs);

            try {
                Connection conn = ConnexionBDD.getConnexion();
                CreneauDAO dao = new CreneauDAO(conn);

                // On suppose que la méthode renvoie Map<idRdv, texte>
                Map<Integer, String> rdvs = dao.getRendezVousPourUtilisateurAvecId(idUtilisateurConnecte, roleUtilisateurConnecte, afficherAVenir);

                if (rdvs.isEmpty()) {
                    resultPanel.add(new JLabel("Aucun rendez-vous " + (afficherAVenir ? "à venir." : "passé.")));
                } else {
                    for (Map.Entry<Integer, String> entry : rdvs.entrySet()) {
                        int idRdv = entry.getKey();
                        String texte = entry.getValue();

                        if (afficherAVenir) {
                            resultPanel.add(new JLabel("• " + texte));
                        } else {
                            Avis avis = dao.getAvisPourRendezVous(idRdv);
                            if (avis == null) {
                                JButton btn = new JButton("📝 Noter : " + texte);
                                btn.addActionListener(ev -> afficherPopupEvaluation(idRdv, texte, null));
                                resultPanel.add(btn);
                            } else {
                                JButton btn = new JButton("🟢 Modifier mon avis : " + texte);
                                btn.addActionListener(ev -> afficherPopupEvaluation(idRdv, texte, avis));
                                resultPanel.add(btn);
                            }

                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                resultPanel.add(new JLabel("Erreur de chargement."));
            }

            resultPanel.revalidate();
            resultPanel.repaint();
        };

        btnFuturs.addActionListener(refresh);
        btnPasses.addActionListener(refresh);

        // Chargement par défaut
        btnFuturs.doClick();

        return panel;
    }



    private JPanel createGraphiqueOccupationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            Connection conn = ConnexionBDD.getConnexion();
            CreneauDAO dao = new CreneauDAO(conn);
            Map<String, Integer[]> stats = dao.getTauxOccupationParSpecialiste();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map.Entry<String, Integer[]> entry : stats.entrySet()) {
                String nom = entry.getKey();
                Integer[] valeurs = entry.getValue();
                dataset.addValue(valeurs[0], "Disponibles", nom);
                dataset.addValue(valeurs[1], "Réservés", nom);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Taux d'occupation des spécialistes",
                    "Spécialiste",
                    "Nombre de créneaux",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            panel.add(chartPanel, BorderLayout.CENTER);

        } catch (Exception e) {
            panel.add(new JLabel("Erreur lors de la génération du graphique"));
            e.printStackTrace();
        }

        return panel;
    }
    private JPanel createConnexionInvitePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel titre = new JLabel("🔒 Vous n'êtes pas connecté");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConnexion.addActionListener(e -> {
            new FenetreConnexion(); // Ouvre la fenêtre de connexion
            dispose(); // Ferme la fenêtre actuelle (non connecté)
        });

        panel.add(titre);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnConnexion);

        return panel;
    }

    private JPanel createGraphiqueIndividuelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titre = new JLabel("📊 Statistiques individuelles");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<Utilisateur> comboBox = new JComboBox<>();
        JPanel graphiquePanel = new JPanel(new BorderLayout());

        try {
            Connection conn = ConnexionBDD.getConnexion();
            UtilisateurDAO dao = new UtilisateurDAO(conn);
            List<Utilisateur> specialistes = dao.getSpecialistes();
            for (Utilisateur s : specialistes) {
                comboBox.addItem(s);
            }
        } catch (SQLException e) {
            new Utilisateur(
                    0,          // id
                    "Erreur",   // nom
                    "",         // prénom
                    "",         // email
                    "specialiste", // rôle
                    "",         // spécialisation
                    "",         // ville
                    ""          // rue
            );

            e.printStackTrace();
        }

        comboBox.addActionListener(e -> {
            graphiquePanel.removeAll();

            Utilisateur selected = (Utilisateur) comboBox.getSelectedItem();
            if (selected != null) {
                try {
                    Connection conn = ConnexionBDD.getConnexion();
                    CreneauDAO dao = new CreneauDAO(conn);
                    Integer[] stats = dao.getOccupationPourSpecialiste(selected.getId());

                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    dataset.addValue(stats[0], "Disponibles", selected.getNom());
                    dataset.addValue(stats[1], "Réservés", selected.getNom());

                    JFreeChart chart = ChartFactory.createBarChart(
                            "Occupation de " + selected.getPrenom() + " " + selected.getNom(),
                            "Statut", "Nombre de créneaux",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true, true, false
                    );

                    ChartPanel chartPanel = new ChartPanel(chart);
                    graphiquePanel.add(chartPanel, BorderLayout.CENTER);
                    graphiquePanel.revalidate();
                    graphiquePanel.repaint();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    graphiquePanel.add(new JLabel("Erreur de chargement du graphique."));
                }
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(comboBox);
        panel.add(Box.createVerticalStrut(15));
        panel.add(graphiquePanel);

        return panel;
    }
    private void afficherCreneauxSurUneSemaine(int idSpecialiste, JPanel cible, CreneauDAO dao) {
        cible.removeAll();
        cible.setLayout(new GridLayout(6, 5, 10, 10)); // 1 ligne pour titres + 5 lignes de créneaux

        LocalDate lundi = semaineAffichee.with(DayOfWeek.MONDAY);
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // === Ligne de titres (jours de la semaine)
        for (int i = 0; i < 5; i++) {
            LocalDate jour = lundi.plusDays(i);
            String nomJour = jour.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            JLabel labelJour = new JLabel(nomJour + " " + jour, SwingConstants.CENTER);
            labelJour.setFont(new Font("SansSerif", Font.BOLD, 14));
            cible.add(labelJour);
        }

        // === 5 lignes de créneaux (max par jour)
        for (int ligne = 0; ligne < 5; ligne++) {
            for (int jour = 0; jour < 5; jour++) {
                LocalDate currentDay = lundi.plusDays(jour);
                List<Creneau> creneauxJour = dao.getCreneauxPourJour(idSpecialiste, currentDay)
                        .stream()
                        .filter(c -> !c.getDateHeure().isBefore(maintenant)) // exclude past
                        .sorted(Comparator.comparing(Creneau::getDateHeure))
                        .collect(Collectors.toList());

                if (ligne < creneauxJour.size()) {
                    Creneau c = creneauxJour.get(ligne);
                    JButton btn = new JButton(c.getDateHeure().toLocalTime().format(heureFormatter));
                    btn.setBackground(c.isDisponible() ? Color.GREEN : Color.RED);
                    btn.setEnabled(c.isDisponible());

                    btn.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Confirmer ce rendez-vous ?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            dao.reserverCreneau(c.getId(), idUtilisateurConnecte);
                            afficherCreneauxSurUneSemaine(idSpecialiste, cible, dao);
                        }
                    });

                    cible.add(btn);
                } else {
                    cible.add(new JLabel("")); // case vide
                }
            }
        }

        cible.revalidate();
        cible.repaint();
    }


    private void afficherPopupEvaluation(int idRdv, String description, Avis avis) {
        JFrame frame = new JFrame("Avis sur le rendez-vous");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel rdvLabel = new JLabel("📅 " + description);
        rdvLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JComboBox<Integer> noteBox = new JComboBox<>(new Integer[]{1,2,3,4,5});
        JTextArea commentaire = new JTextArea(4, 20);
        commentaire.setLineWrap(true);
        commentaire.setWrapStyleWord(true);

        if (avis != null) {
            noteBox.setSelectedItem(avis.getNote());
            commentaire.setText(avis.getCommentaire());
        }

        JButton valider = new JButton((avis == null) ? "Envoyer" : "Modifier");

        valider.addActionListener(e -> {
            int note = (int) noteBox.getSelectedItem();
            String texte = commentaire.getText().trim();

            try {
                Connection conn = ConnexionBDD.getConnexion();
                PreparedStatement ps;
                if (avis == null) {
                    ps = conn.prepareStatement("INSERT INTO avis (id_rendez_vous, note, commentaire) VALUES (?, ?, ?)");
                } else {
                    ps = conn.prepareStatement("UPDATE avis SET note = ?, commentaire = ? WHERE id_rendez_vous = ?");
                }

                if (avis == null) {
                    ps.setInt(1, idRdv);
                    ps.setInt(2, note);
                    ps.setString(3, texte);
                } else {
                    ps.setInt(1, note);
                    ps.setString(2, texte);
                    ps.setInt(3, idRdv);
                }

                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Avis enregistré !");
                frame.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'enregistrement.");
            }
        });

        panel.add(rdvLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Note :"));
        panel.add(noteBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Commentaire :"));
        panel.add(new JScrollPane(commentaire));
        panel.add(Box.createVerticalStrut(10));
        panel.add(valider);

        frame.add(panel);
        frame.setVisible(true);
    }







}
