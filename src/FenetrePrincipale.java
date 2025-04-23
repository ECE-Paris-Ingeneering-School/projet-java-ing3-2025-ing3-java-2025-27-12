import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
// IMPORT org?ouzhdzed
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;
import Modele.Avis;

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
    //private CardLayout cardLayout;
    //private JPanel mainPanel;
    //private int idUtilisateurConnecte;
    //private JPanel creneauPanel;
    // private int semaineAffichee = 0;
    //private CreneauDAO creneauDAO;



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

        Jlabel titre = ("Bienvenue dans randeez-vous", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", "" , """, """, """, ", 20));

        tite.(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JTextArea infos = new JTextArea(
                "Notr les disponibilités des spécialistes, " +

        );
        infos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infos.setEditable(false);


        infos.setBackground(panel.getBackground());
            infos.setBorder(BorderFactory.(10, 30, 30, 30000));

            panel.add(titre, BorderLayout.NORTH);
        p
        anel.add(infos, BorderLayout.CENTER);

        return panel;
    }
    }
     private JPanel createInfosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 255, 250));

        Jlabel titre = ("Bienvenue dans randeez-vous", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", "" , """, """, """, ", 20));

        tite.(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JTextArea infos = new JTextArea(
                "Notr les disponibilités des spécialistes, " +

        );
        infos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infos.setEditable(false);


        infos.setBackground(panel.getBackground());
            infos.setBorder(BorderFactory.(10, 30, 30, 30000));

            panel.add(titre, BorderLayout.NORTH);
        p
        anel.add(infos, BorderLayout.CENTER);

        return panel;
    }
    }
    ]*
    }
    }
     infos.setBackground(panel.getBackground());
            infos.setBorder(BorderFactory.(10, 30, 30, 30000));

            panel.add(titre, BorderLayout.NORTH);
        p
        anel.add(infos, BorderLayout.CENTER);

        return panel;
    }
    ]*
    }
    }
*/

        private JPanel createInfosPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    panel.setBackground(new Color(245, 250, 255));

    Color couleurAccent = new Color(60, 120, 180);


     Font titreFont = new Font("Serif", Font.BOLD, 30);
    Font sousTitreFont = new Font("SansSerif", Font.PLAIN, 16);


    JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(245, 250, 255));
    topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

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

    ImageIcon logoIcon = new ImageIcon("src/Images/Logo.png");
    Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    logoIcon = new ImageIcon(img);
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    topPanel.add(textePanel, BorderLayout.WEST);
    topPanel.add(logoLabel, BorderLayout.EAST);

    // === CENTRE ===
    JPanel centre = new JPanel();
    centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
    centre.setBackground(Color.WHITE);
    centre.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

    JLabel messageAccueil = new JLabel("Bienvenue sur votre plateforme de santé !");


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


    description.setWrapStyleWord(true);

    description.setEditable(false);

    description.setOpaque(false);
    description.setAlignmentX(Component.CENTER_ALIGNMENT);

    centre.add(messageAccueil);
    centre.add(Box.createVerticalStrut(20));
    centre.add(description);


    centre.add(Box.createVerticalStrut(30));


    try {
        Connection conn = ConnexionBDD.getConnexion();
        UtilisateurDAO dao = new UtilisateurDAO(conn);


        List<Utilisateur> specialistes = dao.getSpecialistes();

        for (Utilisateur u : specialistes) {
            double moyenne = new CreneauDAO(conn).getMoyenneNotePourSpecialiste(u.getId());



            JPanel card = new JPanel();

            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            card.setBackground(new Color(250, 250, 250));

            card.setMaximumSize(new Dimension(500, 130));

                card.setAlignmentX(Component.CENTER_ALIGNMENT); ///

            card.add(new JLabel(" " + u.getPrenom() + " " + u.getNom()));

            card.add(new JLabel(" Ville : " + u.getVille()));

            card.add(new JLabel(" Spécialité : " + (u.getSpecialisation() != null ? u.getSpecialisation() : "Non renseignée")));
                card.add(new JLabel(" Email : " + u.getEmail()));

                card.add(new JLabel(" Moyenne des notes : " + String.format("%.2f", moyenne) + " / 5"));

            centre.add(card);

            centre.add(Box.createVerticalStrut(15));
        }

    }
    catch (Exception ex) {
        ex.printStackTrace();
        centre.add(new JLabel(" Impossible de charger les spécialistes."));
    }

    panel.add(topPanel, BorderLayout.NORTH);

    panel.add(new JScrollPane(centre), BorderLayout.CENTER);

    return panel;
}
        private JPanel createRechercheAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel(" Recherche utilisateur (Admin)");


        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));

        // recherche
        JPanel formPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);


        JButton searchBtn = new JButton("Rechercher");
        formPanel.add(new JLabel("Nom, prénom ou email :"));

        formPanel.add(searchField);
        formPanel.add(searchBtn);

        panel.add(formPanel);

        // Box utilisateur
        JComboBox<Utilisateur> listeUtilisateurs = new JComboBox<>();
        panel.add(Box.createVerticalStrut(10));
        panel.add(listeUtilisateurs);

        // Boutons
        JPanel boutonPanel = new JPanel(new FlowLayout());
        JButton btnFuturs = new JButton(" À venir");
        JButton btnPasses = new JButton(" Passés");
        boutonPanel.add(btnFuturs);
        boutonPanel.add(btnPasses);
        panel.add(boutonPanel);

        // Affihcga e résultat
        JTextArea resultArea = new JTextArea(12, 60);
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        panel.add(scroll);

        // Recherches  dess utilisateur
        searchBtn.addActionListener(e -> {
            listeUtilisateurs.removeAllItems();
            resultArea.setText("");
            String critere = searchField.getText().trim();

            try {
                Connection conn = ConnexionBDD.getConnexion();

                UtilisateurDAO dao = new UtilisateurDAO(conn);

                List<Utilisateur> resultats = dao.chercherParCritereAdmin(critere);
                if (resultats.isEmpty()) {
                    resultArea.setText("Aucun utilisateur trouvé.");
                } else {
                    for (Utilisateur u : resultats) listeUtilisateurs.addItem(u);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                resultArea.setText("Erreur lors de la recherche.");
            }
        });

        // Affichages créneau
        ActionListener afficherRDVs = e -> {
            resultArea.setText("");
                Utilisateur utilisateur = (Utilisateur) listeUtilisateurs.getSelectedItem();

                if (utilisateur == null) return;

            boolean futurs = (e.getSource() == btnFuturs);

            try {

                Connection conn = ConnexionBDD.getConnexion();
                CreneauDAO dao = new CreneauDAO(conn);

                List<String> rdvs;

                if (utilisateur.getRole().equalsIgnoreCase("patient")) {
                    rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId(), "patient", futurs);
                  //  rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId(), futurs);
                    //  rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId(), );
                    //  rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId(), "");
                    //  rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId());

                } else {
                     rdvs = dao.getRendezVousPourUtilisateur(utilisateur.getId(), "specialiste", futurs);


                }

                if (rdvs.isEmpty()) {
                    resultArea.setText("Aucun rendez vous " + (futurs ? "à venir." : "passé."));
                } else {
                    for (String ligne : rdvs) {
                        resultArea.append(" " + ligne + "\n");


                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

                resultArea.setText("Erreur lors du chargement des rendez-vous.");
            }
        };

        btnFuturs.addActionListener(afficherRDVs);

        btnPasses.addActionListener(afficherRDVs);

        return panel;
    }




    public JPanel createPrendreRDVPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(255, 250, 240));



        creneauPanel = new JPanel();
        JScrollPane scroll = new JScrollPane(creneauPanel);
        panel.add(scroll, BorderLayout.CENTER);

        ///
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

                    if (!roleUtilisateurConnecte.equalsIgnoreCase("admin")) {
                        creneauDAO.genererCreneauxMoisCompletSiManquants(selected.getId());
                        afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
                    } else {
                        // admin Graphiques
                        creneauPanel.removeAll();
                        creneauPanel.revalidate();
                        creneauPanel.repaint();


                        basPanel.removeAll();
                        if (!roleUtilisateurConnecte.equalsIgnoreCase("admin")) {
                            JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            comboPanel.add(new JLabel("Spécialiste : "));
                            comboPanel.add(comboSpecialistes);
                            basPanel.add(comboPanel);
                        }
                        basPanel.add(Box.createVerticalStrut(10));
                        basPanel.add(createGraphiqueIndividuelPanel());


                            panel.revalidate();
                    }
                }


            });

            if (!roleUtilisateurConnecte.equalsIgnoreCase("admin")) {
                // === Header (non-admin uniquement)
                JPanel navigation = new JPanel(new FlowLayout());
                    JButton btnSemainePrecedente = new JButton("← Semaine précédente");
                JButton btnSemaineSuivante = new JButton("Semaine suivante →");

                navigation.add(btnSemainePrecedente);
                        navigation.add(btnSemaineSuivante);

                JPanel header = new JPanel(new BorderLayout());


                JLabel titre = new JLabel(" Prise de rendez-vous", SwingConstants.CENTER);
                titre.setFont(new Font("Arial", Font.BOLD, 18));


                header.add(titre, BorderLayout.NORTH);
                     header.add(navigation, BorderLayout.SOUTH);
                panel.add(header, BorderLayout.NORTH);

                // Navigation
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

                JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                comboPanel.add(new JLabel("Spécialiste : "));

                comboPanel.add(comboSpecialistes);
                    basPanel.add(comboPanel);
            } else {
                // Aussi Graphique
                JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                comboPanel.add(new JLabel("Spécialiste : "));
                comboPanel.add(comboSpecialistes);
                basPanel.add(comboPanel);
                basPanel.add(Box.createVerticalStrut(10));


                basPanel.add(createGraphiqueIndividuelPanel());

            }

            panel.add(basPanel, BorderLayout.SOUTH);

            // === Affichage initial
            if (comboSpecialistes.getItemCount() > 0) {

                Utilisateur selected = (Utilisateur) comboSpecialistes.getItemAt(0);
                if (!roleUtilisateurConnecte.equalsIgnoreCase("admin")) {

                    creneauDAO.genererCreneauxMoisCompletSiManquants(selected.getId());

                        afficherCreneauxSurUneSemaine(selected.getId(), creneauPanel, creneauDAO);
                } else {

                    comboSpecialistes.setSelectedIndex(0);  // déclenche actionListener pour maj graphique
                }
            }

        } catch (SQLException ex) {
                    ex.printStackTrace();
             panel.add(new JLabel("Erreur de chargement des spécialistes."), BorderLayout.CENTER);
        }



        return panel;
    }







/*
    private void afficherCreneaux(int idSpecialiste, JPanel cible, CreneauDAO creneauDAO) {
        cible.removeAll();
        cible.setLayout(new GridLayout(5, 5, 10, 10)); // 5 jours × 5 créneaux

        List<Creneau> creneaux = creneauDAO.getCreneauxDisponiblesPour(idSpecialiste);
//
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
*/
    private JPanel createComptePanel(String nom, String prenom, String email, String role, String specialisation) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBackground(Color.decode("#e6f0ff"));

        JLabel titre = new JLabel(" Mon Compte");
            titre.setFont(new Font("Arial", Font.BOLD, 22));
            titre.setForeground(new Color(0, 51, 102));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(Box.createVerticalStrut(25));
        panel.add(titre);
            panel.add(Box.createVerticalStrut(20));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color labelColor = new Color(30, 30, 30);

            JLabel lblNom = new JLabel("Nom : " + nom);
            JLabel lblPrenom = new JLabel("Prénom : " + prenom);
            JLabel lblEmail = new JLabel("Email : " + email);

        JLabel lblRole = new JLabel("Rôle : " + role);
        JLabel lblSpec = new JLabel("Spécialisation : " + (role.equalsIgnoreCase("specialiste") ? specialisation : "N/A"));

        for (JLabel lbl : new JLabel[]{lblNom, lblPrenom, lblEmail, lblRole, lblSpec}) {


            lbl.setFont(labelFont);
                lbl.setForeground(labelColor);
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(lbl);
                panel.add(Box.createVerticalStrut(8));
        }

        panel.add(Box.createVerticalStrut(20));



        //Bouton pour MDP
        JButton btnChangerMDP = new JButton(" Changer mon mot de passe");

        styliserBouton(btnChangerMDP, new Color(0, 102, 204));

        panel.add(btnChangerMDP);

        btnChangerMDP.addActionListener(e -> afficherPopupChangementMdp(email));

        // Ajouter un spécialiste
        if ("admin".equalsIgnoreCase(role)) {
            panel.add(Box.createVerticalStrut(15));
            JButton btnAjouterSpecialiste = new JButton(" Ajouter un spécialiste");


            styliserBouton(btnAjouterSpecialiste, new Color(0, 153, 102));
            panel.add(btnAjouterSpecialiste);

                btnAjouterSpecialiste.addActionListener(e -> ouvrirFenetreAjoutSpecialiste());
        }


        panel.add(Box.createVerticalStrut(20));
        JButton deconnexionBtn = new JButton(" Déconnexion");

        styliserBouton(deconnexionBtn, Color.RED);
        panel.add(deconnexionBtn);

        deconnexionBtn.addActionListener(e -> {


            int confirm = JOptionPane.showConfirmDialog(null,         "Voulez-vous vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {


                dispose();
                new FenetreAccueil();
            }


        });

        panel.add(Box.createVerticalStrut(25));
        return panel;

    }


    private void afficherPopupChangementMdp(String email) {
        JDialog dialog = new JDialog((Frame) null, " Changer de mot de passe", true);
            dialog.setSize(520, 380);
         dialog.setLocationRelativeTo(null);

        JPanel content = new JPanel();


        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        content.setBackground(new Color(240, 248, 255));

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel titre = new JLabel("Changer votre mot de passe");

        titre.setFont(new Font("Segoe UI",      Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setForeground(new Color(0, 51,           102));

        JPasswordField pfAncien = new JPasswordField();
        JPasswordField pfNouveau = new JPasswordField();
        JPasswordField pfConfirmer = new JPasswordField();

        pfAncien.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));


        pfNouveau.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            pfConfirmer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl1 = new JLabel("Ancien mot de passe :");
        JLabel lbl2 = new JLabel("Nouveau mot de passe :");


        JLabel lbl3 = new JLabel("Confirmer le mot de passe :");

        for (JLabel lbl : new JLabel[]{lbl1, lbl2, lbl3}) {
            lbl.setFont(font);

            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        for (JComponent field : new JComponent[]{pfAncien, pfNouveau, pfConfirmer}) {
            field.setFont(font);
        }

        JButton btnValider = new JButton(" Valider");
        styliserBouton(btnValider, new Color(0, 102, 204));


        btnValider.addActionListener(e -> {
            String ancien = new String(pfAncien.getPassword()).trim();

            String nouveau = new String(pfNouveau.getPassword()).trim();
                String confirmer = new String(pfConfirmer.getPassword()).trim();


                if (nouveau.isEmpty() || !nouveau.equals(confirmer)) {

                    JOptionPane.showMessageDialog(dialog, " Les mots de passe ne correspondent pas.");
                return;
            }

            try (Connection conn = ConnexionBDD.getConnexion()) {

                PreparedStatement check = conn.prepareStatement("SELECT mot_de_passe FROM utilisateur WHERE email = ?");

                        check.setString(1, email);
                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    String actuel = rs.getString("mot_de_passe");
                    if (!actuel.equals(ancien)) {


                        JOptionPane.showMessageDialog(dialog, " Ancien mot de passe incorrect.");
                        return;


                    }


                }

                PreparedStatement update = conn.prepareStatement("UPDATE utilisateur SET mot_de_passe = ? WHERE email = ?");

                update.setString(1, nouveau);
                    update.setString(2, email);
                update.executeUpdate();

                JOptionPane.showMessageDialog(dialog, " Mot de passe mis à jour !");

                 dialog.dispose();

            } catch (Exception ex) {

                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, " Erreur lors de la mise à jour.");
            }
        });

        ///
        content.add(titre);
        content.add(Box.createVerticalStrut(15));
        content.add(lbl1); content.add(pfAncien);
        content.add(Box.createVerticalStrut(10));
        content.add(lbl2); content.add(pfNouveau);


        content.add(Box.createVerticalStrut(10));
        content.add(lbl3); content.add(pfConfirmer);
        content.add(Box.createVerticalStrut(20));
        content.add(btnValider);

        dialog.add(content);
        dialog.setVisible(true);
    }


    private void ouvrirFenetreAjoutSpecialiste() {
        JFrame ajoutFrame = new JFrame("Ajouter un  spécialiste");

        ajoutFrame.setSize(520, 520);
        ajoutFrame.setLocationRelativeTo(null);
        ajoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JTextField tfNom = new JTextField();
        JTextField tfPrenom = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfSpecialisation = new JTextField();

        JTextField tfRue = new JTextField();
        JTextField tfVille = new JTextField();
        JPasswordField tfMdp = new JPasswordField();


        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        for (JComponent field : new JComponent[]{tfNom, tfPrenom, tfEmail, tfSpecialisation, tfRue, tfVille, tfMdp}) {

            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            field.setFont(font);
        }

        form.add(new JLabel("Nom :")); form.add(tfNom);
        form.add(Box.createVerticalStrut(8));

        form.add(new JLabel("Prénom :")); form.add(tfPrenom);
        form.add(Box.createVerticalStrut(8));

        form.add(new JLabel("Email :")); form.add(tfEmail);
            form.add(Box.createVerticalStrut(8));
        form.add(new JLabel("Spécialisation :")); form.add(tfSpecialisation);
        form.add(Box.createVerticalStrut(8));
        form.add(new JLabel("Rue :")); form.add(tfRue);
        form.add(Box.createVerticalStrut(8));
        form.add(new JLabel("Ville :")); form.add(tfVille);



        form.add(Box.createVerticalStrut(8));
        form.add(new JLabel("Mot de passe :")); form.add(tfMdp);
        form.add(Box.createVerticalStrut(15));

        JButton btnValider = new JButton(" Ajouter le spécialiste");
        styliserBouton(btnValider, new Color(0, 153, 102));
        form.add(btnValider);

        btnValider.addActionListener(e -> {
            String nomSPE = tfNom.getText().trim();
            String prenomSPE = tfPrenom.getText().trim();
            String emailSPE = tfEmail.getText().trim();
            String specialisationSPE = tfSpecialisation.getText().trim();

            String rue = tfRue.getText().trim();
            String ville = tfVille.getText().trim();

            String mdp = new String(tfMdp.getPassword());

            if (nomSPE.isEmpty() || prenomSPE.isEmpty() ||                     emailSPE.isEmpty() || specialisationSPE.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(ajoutFrame, "Tous les champs sont obligatoires.");


                return;
            }

            try {
                Connection conn                 = ConnexionBDD.getConnexion();
                String checkSQL = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
                PreparedStatement checkStmt              = conn.prepareStatement(checkSQL);
                checkStmt.setString(1, emailSPE);


                ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                if (rs.getInt(1) > 0) {


                    JOptionPane.showMessageDialog(ajoutFrame, "Cet email est déjà utilisé.");
                    return;
                }

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

                JOptionPane.showMessageDialog(ajoutFrame, "Spécialiste ajouté avec succès !");
                        ajoutFrame.dispose();

            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ajoutFrame, "Erreur lors de l'ajout du spécialiste.");
            }


        });

        ajoutFrame.add(form);
        ajoutFrame.setVisible(true);
    }

    private JPanel createRecherchePanel() {

        if (roleUtilisateurConnecte.equalsIgnoreCase("patient")) {

            return createRecherchePatientPanel();
        }
        if(roleUtilisateurConnecte.equalsIgnoreCase("admin")) {

            return createRechercheAdminPanel();

            }















        if (roleUtilisateurConnecte.equalsIgnoreCase("specialiste")) {
            return createRechercheSpecialistePanel();
            }

        ///
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

        JLabel titre = new JLabel(" Rechercher un patient");
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

        //  filtres
        JPanel filtrePanel = new JPanel(new FlowLayout());
        JButton btnFuturs = new JButton(" À venir");
        JButton btnPasses = new JButton(" Passés");
        filtrePanel.add(btnFuturs);
        filtrePanel.add(btnPasses);
        panel.add(filtrePanel);

        JTextArea resultArea = new JTextArea(10, 60);
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        panel.add(scroll);

        JPanel mailPanel = new JPanel();
        JButton mailBtn = new JButton("Envoyer un mail");
        mailBtn.setVisible(false);
        mailPanel.add(mailBtn);
        panel.add(mailPanel);

        final boolean[] afficherAVenir = {true}; // filtre par défaut

        // Action du bouton "Rechercher"
        searchBtn.addActionListener(e -> {
            String critere = searchField.getText().trim();
            listePatients.removeAllItems();
            resultArea.setText("");
            mailBtn.setVisible(false);

            if (critere.isEmpty()) {
                resultArea.setText(" Veuillez entrer un nom, prénom ou email.");
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
                resultArea.setText(" Erreur de recherche en base.");
            }
        });


        listePatients.addActionListener(e -> {
            Utilisateur patient = (Utilisateur) listePatients.getSelectedItem();
            if (patient == null) return;



            chargerRDVEtAfficher(resultArea, mailBtn, patient.getEmail(), afficherAVenir[0]);

        });


        btnFuturs.addActionListener(e -> {
            afficherAVenir[0] = true;
            Utilisateur patient = (Utilisateur) listePatients.getSelectedItem();


            if (patient != null) {

                chargerRDVEtAfficher(resultArea, mailBtn, patient.getEmail(), true);
            }

        });

        btnPasses.addActionListener(e -> {
            afficherAVenir[0] = false;
            Utilisateur patient = (Utilisateur) listePatients.getSelectedItem();

            if (patient != null) {

                chargerRDVEtAfficher(resultArea, mailBtn, patient.getEmail(), false);
            }

        });

        return panel;
    }



    private void chargerRDVEtAfficher(JTextArea resultArea, JButton mailBtn, String email, boolean afficherAVenir) {
        resultArea.setText("");
        mailBtn.setVisible(false);

        try {
            Connection conn = ConnexionBDD.getConnexion();
            CreneauDAO dao = new CreneauDAO(conn);

            List<String> infos = dao.rechercherPatientEtRendezVousAvecFiltre(idUtilisateurConnecte, email, afficherAVenir);

            for (String ligne : infos) {

                if (!ligne.startsWith("EMAIL:")) {
                    resultArea.append(ligne + "\n");


                } else {
                    String adresse = ligne.substring(6);
                    mailBtn.setVisible(true);
                    mailBtn.setActionCommand(adresse);


                    mailBtn.addActionListener(evt -> {
                        try {

                            Desktop.getDesktop().mail(new URI("mailto:" + adresse));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(resultArea, "Erreur lors de l'ouverture du mail.");
                        }

                    });
                }





            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText(" Erreur chargendents RDV.");
        }
    }


    private void styliserBouton(JButton bouton, Color bgColor) {
        bouton.setFocusPainted(false);
        bouton.setForeground(Color.WHITE);
        bouton.setBackground(bgColor);
        bouton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bouton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }



    private JPanel createRecherchePatientPanel() {
        JPanel panel = new JPanel();


        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Rechercher des créneaux disponibles");

        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titre);

        panel.add(Box.createVerticalStrut(15));

        JPanel formPanel = new JPanel(new FlowLayout());

                JLabel specialiteLabel = new JLabel("Spécialité :");
            JComboBox<String> specialiteBox = new JComboBox<>();


        JLabel villeLabel = new JLabel("Ville :");
        JComboBox<String> villeBox = new JComboBox<>();
         villeBox.addItem("Aucune"); // option par défaut

                try {
                    Connection conn = ConnexionBDD.getConnexion();
                    UtilisateurDAO dao = new UtilisateurDAO(conn);

                    // Remplir spécialités
                    List<String> specialites = dao.getSpecialitesDisponibles();
                    for (String s : specialites) {
                        specialiteBox.addItem(s);
                    }

            // Remplir villes
            List<String> villes = dao.getVillesDisponibles(); // Tu dois ajouter cette méthode dans UtilisateurDAO
            for (String v : villes) {
                villeBox.addItem(v);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            specialiteBox.addItem("Aucune spécialité trouvée");
            villeBox.addItem("Erreur ville");
        }

                    JLabel dateLabel = new JLabel("Date (aaaa-mm-jj) :");
                    JTextField dateField = new JTextField(10);

                    JButton rechercherBtn = new JButton("Rechercher");

                    formPanel.add(specialiteLabel);
                    formPanel.add(specialiteBox);
                    formPanel.add(villeLabel);
                    formPanel.add(villeBox);
                    formPanel.add(dateLabel);
                    formPanel.add(dateField);
                    formPanel.add(rechercherBtn);

        panel.add(formPanel);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        panel.add(scrollPane);



        rechercherBtn.addActionListener(e -> {
            resultPanel.removeAll();

            String specialite = (String) specialiteBox.getSelectedItem();


            String ville = villeBox.getSelectedItem().equals("Aucune") ? null : (String) villeBox.getSelectedItem();
            String dateStr = dateField.getText();

            try {
                LocalDate date = LocalDate.parse(dateStr);
                Connection conn = ConnexionBDD.getConnexion();
                CreneauDAO dao = new CreneauDAO(conn);


                List<Creneau> resultats = dao.rechercherCreneauxComplet(specialite, ville, date);

                if (resultats.isEmpty()) {
                    resultPanel.add(new JLabel("Aucun créneau disponible pour ce jour."));
                } else {
                    for (Creneau c : resultats) {
                        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        ligne.setMaximumSize(new Dimension(700, 40));


                                    String texte = "" + c.getDateHeure() + " - Dr. " +
                                            c.getPrenomSpecialiste() + " " + c.getNomSpecialiste() +
                                            " (" + c.getSpecialisation() + ") à " + c.getVille();
                                    JLabel lbl = new JLabel(texte);

                                    JButton reserver = new JButton("Réserver");
                        reserver.setBackground(Color.GREEN);

                        reserver.addActionListener(ev -> {
                            int confirm = JOptionPane.showConfirmDialog(panel, "Confirmer la réservation ?");
                            if (confirm == JOptionPane.YES_OPTION) {
                                dao.reserverCreneau(c.getId(), idUtilisateurConnecte);
                                JOptionPane.showMessageDialog(panel, "Créneau réservé !");
                                rechercherBtn.doClick();
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
                        resultPanel.add(new JLabel(" Erreur : Veuillez entrer une date au format aaaa-mm-jj"));
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

        JLabel titre = new JLabel(" Mes rendez-vous");
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));

        try {
            Connection conn = ConnexionBDD.getConnexion();
            CreneauDAO dao = new CreneauDAO(conn);


            if (roleUtilisateurConnecte.equalsIgnoreCase("admin")) {
                JPanel comboPanel = new JPanel(new FlowLayout());
                comboPanel.add(new JLabel("Choisir un spécialiste :"));
                JComboBox<Utilisateur> box = new JComboBox<>();
                UtilisateurDAO userDAO = new UtilisateurDAO(conn);

                for (Utilisateur u : userDAO.getSpecialistes()) {
                    box.addItem(u);
                }

                comboPanel.add(box);
                panel.add(comboPanel);

                JPanel resultPanel = new JPanel();


                resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
                JScrollPane scroll = new JScrollPane(resultPanel);

                panel.add(scroll);

                box.addActionListener(e -> {
                    resultPanel.removeAll();
                    Utilisateur selected = (Utilisateur) box.getSelectedItem();
                    if (selected != null) {
                        int id = selected.getId();
                        double moyenne = dao.getMoyenneNotePourSpecialiste(id);
                        List<String> commentaires = dao.getCommentairesPourSpecialiste(id);

                        resultPanel.add(new JLabel("Moyenne des notes : " + String.format("%.2f", moyenne) + " / 5"));
                        resultPanel.add(Box.createVerticalStrut(10));

                        if (commentaires.isEmpty()) {
                            resultPanel.add(new JLabel("Aucun commentaire trouver."));


                        } else {
                            resultPanel.add(new JLabel("Commentaires :"));
                            for (String com : commentaires) {
                                    resultPanel.add(new JLabel("• " + com));
                            }
                        }
                    }


                    resultPanel.revalidate();


                    resultPanel.repaint();
                });


                if (box.getItemCount() > 0) {
                    box.setSelectedIndex(0);
                }

                return panel;
            }


            if (roleUtilisateurConnecte.equalsIgnoreCase("specialiste")) {


                List<String> rdvs = dao.getRendezVousPourUtilisateur(idUtilisateurConnecte, "specialiste", true);

                if (rdvs.isEmpty()) {
                        panel.add(new JLabel("Aucun rendez-vous trouvé."));
                    } else {
                        for (String r : rdvs) {
                            panel.add(new JLabel(" " + r));
                        }
                    }


                    double moyenne = dao.getMoyenneNotePourSpecialiste(idUtilisateurConnecte);
                List<String> commentaires = dao.getCommentairesPourSpecialiste(idUtilisateurConnecte);

                panel.add(Box.createVerticalStrut(20));
                panel.add(new JLabel("Moyenne des notes : " + String.format("%.2f", moyenne) + " / 5"));
                panel.add(Box.createVerticalStrut(10));
                if (commentaires.isEmpty()) {
                    panel.add(new JLabel("Aucun commentaire."));
                } else {
                    panel.add(new JLabel(" Commentaires reçus :"));
                    for (String com : commentaires) {
                        panel.add(new JLabel("• " + com));
                    }
                }

                return panel;
            }


            JPanel btnPanel = new JPanel(new FlowLayout());
            JButton btnFuturs = new JButton("À venir");


            JButton btnPasses = new JButton("Passés");
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

                Map<String, Integer> mapIdRdv = new HashMap<>();

                try {


                    List<String> rdvs = dao.getRendezVousPourUtilisateurAvecId(idUtilisateurConnecte, roleUtilisateurConnecte, afficherAVenir, mapIdRdv);

                    if (rdvs.isEmpty()) {
                        resultPanel.add(new JLabel("Aucun rendez-vous " + (afficherAVenir ? "à venir." : "passé.")));


                    } else {
                        for (String ligne : rdvs) {
                            int idRdv = mapIdRdv.getOrDefault(ligne, -1);
                            if (afficherAVenir) {
                                JPanel lignePanel = new JPanel(new BorderLayout());
                                JLabel lbl = new JLabel("" + ligne);
                                JButton annulerBtn = new JButton("Annuler");
                                annulerBtn.setForeground(Color.RED);

                                annulerBtn.addActionListener(ev -> {
                                    int confirm = JOptionPane.showConfirmDialog(panel,
                                            "Voulez-vous annuler ce rendez-vous ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                                try {
                                                    dao.annulerRendezVous(idRdv);
                                                    JOptionPane.showMessageDialog(panel, "Rendez-vous annulé !");
                                                    btnFuturs.doClick();


                                                } catch (SQLException ex) {
                                                    ex.printStackTrace();
                                                    JOptionPane.showMessageDialog(panel, "Erreur lors de l’annulation.");
                                                }
                                    }
                                });

                                lignePanel.add(lbl, BorderLayout.CENTER);
                                lignePanel.add(annulerBtn, BorderLayout.EAST);

                                resultPanel.add(lignePanel);
                            }
                            else {
                                if (!dao.avisExistePourRendezVous(idRdv)) {
                                    JButton btn = new JButton(" " + ligne);
                                    btn.addActionListener(ev -> afficherPopupEvaluation(idRdv, ligne, null));


                                    resultPanel.add(btn);
                                } else {
                                    JButton btn = new JButton("Modifier mon avis : " + ligne);
                                    Avis avis = dao.getAvisPourRendezVous(idRdv);
                                    btn.addActionListener(ev -> afficherPopupEvaluation(idRdv, ligne, avis));
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

            btnFuturs.doClick();

        } catch (Exception e) {
            e.printStackTrace();
            panel.add(new JLabel("Erreur chatgementRDV."));
        }

        return panel;
    }


/*
    private JPanel createGraphiqueOccupationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());



        try {
            Connection conn = ConnexionBDD.getConnexion();
            CreneauDAO dao = new CreneauDAO(conn);
            Map<String, Integer[]> stats = dao.getTauxOccupationParSpecialiste();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for     (Map.Entry<String, Integer[]> entry : stats.entrySet()) {
                        String nom = entry.getKey();
                Integer[] valeurs = entry.getValue();
                dataset.addValue(valeurs[0], "Disponibles", nom);
                data    set.addValue(valeurs[1], "Réservés", nom);
            }



            JFree   Chart    chart = ChartFactory.createBarChart(
                            "Taux       d'ocupation des spécialistes",
                    "Spécialiste    "   ,
                    "Nombre de créneaux",
                    dataset,
                    PlotOrientatio  n.VERTICAL,
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
    */
    /*
    private JPanel       createConnexionInvitePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder
        (BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel   titre = new JLabel(" Vous n'êtes pas connecté");
        titre.setFont
        (new Font("Arial", Font.BOLD, 18));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.       setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConnexion.addActionListener(e -> {
            new FenetreConnexion();
            dispose();
        });

        panel.add(titre);
        panel.add(Box.createVerticalStrut(20));


        panel.add(btnConnexion);

        return panel;
    }
    */

    private JPanel createGraphiqueIndividuelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titre = new JLabel(" Statistiques individuelles");
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
                    0,
                    "Erreur",
                    "",
                    "",
                    "specialiste",
                    "",
                    "",
                    ""
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

                        dataset.addValue(stats[0],      "Disponibles", selected.getNom());
                    dataset.addValue(stats[1], "Réservés", selected.getNom());

                    JFreeChart chart = ChartFactory.createBarChart(
                            "Occupation de " + selected.getPrenom() + " " + selected.getNom(),
                            "Statut", "Nombre de créneaux",
                            dataset,


                            PlotOrientation.VERTICAL,

                            true, true, false
                    )
                            ;

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


        for (int i = 0; i < 5; i++) {
            LocalDate jour = lundi.plusDays(i);
            String nomJour = jour.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);


            JLabel labelJour = new JLabel(nomJour + " " + jour, SwingConstants.CENTER);
             labelJour.setFont(new Font("SansSerif", Font.BOLD, 14));

            cible.add(labelJour);

        }



        for (int ligne = 0; ligne < 5; ligne++) {
            for (int jour = 0; jour < 5; jour++) {
                LocalDate currentDay = lundi.plusDays(jour);
                        List<Creneau> creneauxJour = dao.getCreneauxPourJour(idSpecialiste, currentDay)
                                .stream()

                                .filter(c -> !c.getDateHeure().isBefore(maintenant)) // exclude past

                                .sorted(Comparator.comparing(Creneau::getDateHeure))
                                .collect(Collectors.toList());

                if   (ligne < creneauxJour.size()) {
                    Creneau c = creneauxJour.get(ligne);
                    JButton btn = new JButton(c.getDateHeure().toLocalTime().format(heureFormatter));
                        btn.setBackground(c.isDisponible() ? Color.GREEN : Color.RED);
                    btn.setEnabled(c.isDisponible());



                    btn.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Confirmer ce rendez-vous ?");
                        if      (confirm == JOptionPane.YES_OPTION) {
                            dao.reserverCreneau(c.getId(), idUtilisateurConnecte);


                            afficherCreneauxSurUneSemaine(idSpecialiste, cible, dao);
                        }

                    });

                    cible       .add(btn);
                } else

                {
                    cible.add(new JLabel(""));
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

        JLabel rdvLabel = new JLabel(" " + description);
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
