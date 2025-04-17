import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import Modele.Avis;

public class CreneauDAO {

    private Connection connection;

    public CreneauDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Récupère tous les créneaux disponibles (non réservés)
     */
    public List<Creneau> getCreneauxDisponibles() {
        List<Creneau> creneaux = new ArrayList<>();
        String sql = "SELECT * FROM creneau WHERE disponible = TRUE";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialisation = rs.getString("specialisation");

                int id = rs.getInt("id");
                int idSpecialiste = rs.getInt("id_specialiste");
                LocalDateTime dateHeure = rs.getTimestamp("date_heure").toLocalDateTime();
                boolean dispo = rs.getBoolean("disponible");
                creneaux.add(new Creneau(id, idSpecialiste, dateHeure, dispo, nom, prenom, specialisation));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creneaux;
    }

    /**
     * Récupère tous les créneaux (disponibles ou non) d’un spécialiste
     */
    public List<Creneau> getCreneauxDisponiblesPour(int idSpecialiste) {
        List<Creneau> creneaux = new ArrayList<>();
        String sql = """
        SELECT c.id, c.date_heure, c.disponible,
               u.id AS id_spec, u.nom, u.prenom, u.specialisation
        FROM creneau c
        JOIN utilisateur u ON c.id_specialiste = u.id
        WHERE c.id_specialiste = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSpecialiste);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSpec = rs.getInt("id_spec");
                LocalDateTime dateHeure = rs.getTimestamp("date_heure").toLocalDateTime();
                boolean dispo = rs.getBoolean("disponible");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialisation = rs.getString("specialisation");

                creneaux.add(new Creneau(id, idSpec, dateHeure, dispo, nom, prenom, specialisation));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creneaux;
    }
    public List<String> rechercherPatientsPourSpecialiste(int idSpecialiste) {
        List<String> patients = new ArrayList<>();

        String sql = """
        SELECT u.nom, u.prenom, u.email, r.date_reservation, c.date_heure
        FROM rendez_vous r
        JOIN creneau c ON r.id_creneau = c.id
        JOIN utilisateur u ON r.id_patient = u.id
        WHERE c.id_specialiste = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSpecialiste);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String dateHeure = rs.getTimestamp("date_heure").toLocalDateTime().toString();
                String dateResa = rs.getTimestamp("date_reservation").toLocalDateTime().toString();

                String ligne = "👤 " + prenom + " " + nom + " | 📧 " + email +
                        " | 📅 Rendez-vous : " + dateHeure + " | Réservé le : " + dateResa;
                patients.add(ligne);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }


    /**
     * Réserve un créneau : crée un rendez-vous pour le patient et rend le créneau indisponible
     */
    public void reserverCreneau(int idCreneau, int idPatient) {
        String insertSQL = "INSERT INTO rendez_vous (id_patient, id_creneau) VALUES (?, ?)";
        String updateSQL = "UPDATE creneau SET disponible = FALSE WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                 PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {

                insertStmt.setInt(1, idPatient);
                insertStmt.setInt(2, idCreneau);
                insertStmt.executeUpdate();

                updateStmt.setInt(1, idCreneau);
                updateStmt.executeUpdate();

                connection.commit();
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Génère automatiquement 5 jours de créneaux (matin + après-midi) pour un spécialiste
     */
    public void genererCreneauxSiManquants(int idSpecialiste) {
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        for (int i = 0; i < 5; i++) {
            LocalDateTime jour = now.plusDays(i);

            LocalTime[] heures = {
                    LocalTime.of(8, 0), LocalTime.of(10, 0),
                    LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)
            };

            for (LocalTime h : heures) {
                LocalDateTime dateHeure = jour.with(h);
                if (!creneauExiste(idSpecialiste, dateHeure)) {
                    try (PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO creneau (id_specialiste, date_heure, disponible) VALUES (?, ?, TRUE)")) {
                        ps.setInt(1, idSpecialiste);
                        ps.setTimestamp(2, Timestamp.valueOf(dateHeure));
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Vérifie si un créneau existe déjà en base pour un spécialiste et une date/heure
     */
    private boolean creneauExiste(int idSpecialiste, LocalDateTime dateHeure) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id FROM creneau WHERE id_specialiste = ? AND date_heure = ?")) {
            ps.setInt(1, idSpecialiste);
            ps.setTimestamp(2, Timestamp.valueOf(dateHeure));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<String> getRendezVousPourPatient(int idPatient) {
        List<String> liste = new ArrayList<>();

        String sql = """
        SELECT c.date_heure, u.nom, u.prenom, u.specialisation
        FROM rendez_vous r
        JOIN creneau c ON r.id_creneau = c.id
        JOIN utilisateur u ON c.id_specialiste = u.id
        WHERE r.id_patient = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Timestamp dateHeure = rs.getTimestamp("date_heure");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialisation = rs.getString("specialisation");

                String rdv = "📅 " + dateHeure.toLocalDateTime().toString() +
                        " avec Dr. " + prenom + " " + nom +
                        " (" + specialisation + ")";
                liste.add(rdv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
    public List<String> getRendezVousPourUtilisateur(int idUtilisateur, String role) {
        List<String> liste = new ArrayList<>();
        String sql;

        if (role.equalsIgnoreCase("patient")) {
            sql = """
            SELECT c.date_heure, u.nom, u.prenom, u.specialisation
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON c.id_specialiste = u.id
            WHERE r.id_patient = ?
            ORDER BY c.date_heure
        """;
        } else if (role.equalsIgnoreCase("specialiste")) {
            sql = """
            SELECT c.date_heure, u.nom, u.prenom
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON r.id_patient = u.id
            WHERE c.id_specialiste = ?
            ORDER BY c.date_heure
        """;
        } else {
            // rôle non reconnu (admin, ou autre)
            return liste; // retourne une liste vide sans planter
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp dateHeure = rs.getTimestamp("date_heure");
                String date = dateHeure.toLocalDateTime().toString();

                if (role.equalsIgnoreCase("patient")) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String specialisation = rs.getString("specialisation");
                    liste.add("📅 " + date + " avec Dr. " + prenom + " " + nom + " (" + specialisation + ")");
                } else if (role.equalsIgnoreCase("specialiste")) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    liste.add("📅 " + date + " - Patient : " + prenom + " " + nom);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public List<String> rechercherCreneaux(String specialite, LocalDate date) {
        List<String> resultats = new ArrayList<>();

        String sql = """
        SELECT c.date_heure, u.nom, u.prenom, u.specialisation
        FROM creneau c
        JOIN utilisateur u ON c.id_specialiste = u.id
        WHERE c.disponible = TRUE
          AND u.specialisation LIKE ?
          AND DATE(c.date_heure) = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + specialite + "%");
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("date_heure");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String spec = rs.getString("specialisation");

                resultats.add("📅 " + ts.toLocalDateTime() + " - Dr. " + prenom + " " + nom + " (" + spec + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }
    public List<Creneau> rechercherCreneauxComplet(String specialite, LocalDate date) {
        List<Creneau> resultats = new ArrayList<>();

        String sql = """
       SELECT c.id, c.date_heure, c.disponible,
                                                                       u.id AS id_spec, u.nom, u.prenom, u.specialisation
                                                                
        FROM creneau c
        JOIN utilisateur u ON c.id_specialiste = u.id
        WHERE c.disponible = TRUE
          AND u.specialisation LIKE ?
          AND DATE(c.date_heure) = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + specialite + "%");
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSpecialiste = rs.getInt("id_spec");
                LocalDateTime dateHeure = rs.getTimestamp("date_heure").toLocalDateTime();
                boolean dispo = rs.getBoolean("disponible");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialisation = rs.getString("specialisation");

                resultats.add(new Creneau(id, idSpecialiste, dateHeure, dispo, nom, prenom, specialisation));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }

    public List<String> rechercherPatientEtRendezVous(int idSpecialiste, String emailRecherche) {
        List<String> resultats = new ArrayList<>();

        String sql = """
        SELECT u.nom, u.prenom, u.email, c.date_heure
        FROM utilisateur u
        JOIN rendez_vous r ON u.id = r.id_patient
        JOIN creneau c ON r.id_creneau = c.id
        WHERE c.id_specialiste = ?
          AND u.email = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSpecialiste);
            ps.setString(2, emailRecherche);

            ResultSet rs = ps.executeQuery();

            String patientNom = null, patientPrenom = null, patientEmail = null;
            List<String> rdvs = new ArrayList<>();

            while (rs.next()) {
                if (patientNom == null) {
                    patientNom = rs.getString("nom");
                    patientPrenom = rs.getString("prenom");
                    patientEmail = rs.getString("email");
                }

                String dateHeure = rs.getTimestamp("date_heure").toLocalDateTime().toString();
                rdvs.add("📅 " + dateHeure + " - Patient : " + patientPrenom + " " + patientNom);
            }

            if (patientNom != null) {
                resultats.add("👤 " + patientPrenom + " " + patientNom);
                resultats.add("📧 " + patientEmail);
                resultats.add("📆 Rendez-vous à venir :");
                resultats.addAll(rdvs);
                resultats.add("EMAIL:" + patientEmail);
            } else {
                resultats.add("Aucun rendez-vous trouvé pour ce patient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultats.add("❌ Erreur lors de la récupération des RDV.");
        }

        return resultats;
    }
    public Map<String, Integer[]> getTauxOccupationParSpecialiste() {
        Map<String, Integer[]> stats = new HashMap<>();

        String sql = """
        SELECT u.nom, u.prenom, u.specialisation,
               SUM(CASE WHEN c.disponible = TRUE THEN 1 ELSE 0 END) AS disponibles,
               SUM(CASE WHEN c.disponible = FALSE THEN 1 ELSE 0 END) AS reserves
        FROM utilisateur u
        JOIN creneau c ON u.id = c.id_specialiste
        WHERE u.role = 'specialiste'
        GROUP BY u.id
    """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nom = rs.getString("prenom") + " " + rs.getString("nom") + " (" + rs.getString("specialisation") + ")";
                int libres = rs.getInt("disponibles");
                int reserves = rs.getInt("reserves");
                stats.put(nom, new Integer[] { libres, reserves });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }


    public Integer[] getOccupationPourSpecialiste(int idSpecialiste) {
        Integer[] stats = new Integer[2]; // [disponibles, réservés]
        String sql = "SELECT " +
                "SUM(CASE WHEN disponible = TRUE THEN 1 ELSE 0 END) AS disponibles, " +
                "SUM(CASE WHEN disponible = FALSE THEN 1 ELSE 0 END) AS reserves " +
                "FROM creneau WHERE id_specialiste = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats[0] = rs.getInt("disponibles");
                    stats[1] = rs.getInt("reserves");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
    public void genererCreneauxMoisCompletSiManquants(int idSpecialiste) {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate dateFin = aujourdHui.plusWeeks(4); // 1 mois

        for (LocalDate date = aujourdHui; date.isBefore(dateFin); date = date.plusDays(1)) {
            // seulement lundi à vendredi
            if (date.getDayOfWeek().getValue() >= 6) continue;

            for (int heure : new int[]{8, 10, 14, 16, 18}) {
                LocalDateTime creneauHeure = date.atTime(heure, 0);
                if (!existeCreneau(idSpecialiste, creneauHeure)) {
                    ajouterCreneau(idSpecialiste, creneauHeure);
                }
            }
        }
    }
    private boolean existeCreneau(int idSpecialiste, LocalDateTime dateHeure) {
        try {
            String sql = "SELECT COUNT(*) FROM creneau WHERE id_specialiste = ? AND date_heure = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSpecialiste);
            stmt.setTimestamp(2, Timestamp.valueOf(dateHeure));
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void ajouterCreneau(int idSpecialiste, LocalDateTime dateHeure) {
        try {
            String sql = "INSERT INTO creneau (id_specialiste, date_heure, disponible) VALUES (?, ?, 1)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSpecialiste);
            stmt.setTimestamp(2, Timestamp.valueOf(dateHeure));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Creneau> getCreneauxPourJour(int idSpecialiste, LocalDate date) {
        List<Creneau> creneaux = new ArrayList<>();

        String sql = "SELECT * FROM creneau WHERE id_specialiste = ? AND DATE(date_heure) = ? ORDER BY date_heure";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSpecialiste);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp dateHeure = rs.getTimestamp("date_heure");
                boolean dispo = rs.getBoolean("disponible");

                String nom = ""; // ou une valeur récupérée si tu l'as
                String prenom = "";
                String specialisation = "";

                creneaux.add(new Creneau(id, idSpecialiste, dateHeure.toLocalDateTime(), dispo, nom, prenom, specialisation));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creneaux;
    }
    public List<String> getRendezVousPourUtilisateur(int idUtilisateur, String role, boolean aVenir) {
        List<String> liste = new ArrayList<>();
        String sql;

        if (role.equalsIgnoreCase("patient")) {
            sql = """
            SELECT c.date_heure, u.nom, u.prenom, u.specialisation
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON c.id_specialiste = u.id
            WHERE r.id_patient = ?
            ORDER BY c.date_heure
        """;
        } else if (role.equalsIgnoreCase("specialiste")) {
            sql = """
            SELECT c.date_heure, u.nom, u.prenom
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON r.id_patient = u.id
            WHERE c.id_specialiste = ?
            ORDER BY c.date_heure
        """;
        } else {
            return liste;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp dateHeure = rs.getTimestamp("date_heure");
                LocalDateTime ldt = dateHeure.toLocalDateTime();

                if (aVenir && ldt.isBefore(LocalDateTime.now())) continue;
                if (!aVenir && ldt.isAfter(LocalDateTime.now())) continue;

                String ligne;

                if (role.equalsIgnoreCase("patient")) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String specialisation = rs.getString("specialisation");
                    ligne = "📅 " + ldt + " avec Dr. " + prenom + " " + nom + " (" + specialisation + ")";
                } else {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    ligne = "📅 " + ldt + " - Patient : " + prenom + " " + nom;
                }

                liste.add(ligne);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
    public boolean avisExistePourRendezVous(int idRendezVous) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id FROM avis WHERE id_rendez_vous = ?")) {
            ps.setInt(1, idRendezVous);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void enregistrerAvis(int idRendezVous, int note, String commentaire) {
        String sql = "INSERT INTO avis (id_rendez_vous, note, commentaire) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRendezVous);
            ps.setInt(2, note);
            ps.setString(3, commentaire);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Map<Integer, String> getRendezVousPourUtilisateurAvecId(int idUtilisateur, String role, boolean aVenir) {
        Map<Integer, String> rdvs = new LinkedHashMap<>();

        String conditionTemps = aVenir ? ">= NOW()" : "< NOW()";
        String sql = "";

        if (role.equalsIgnoreCase("patient")) {
            sql = """
            SELECT r.id, c.date_heure, u.nom, u.prenom, u.specialisation
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON c.id_specialiste = u.id
            WHERE r.id_patient = ? AND c.date_heure """ + conditionTemps + " ORDER BY c.date_heure";
        } else if (role.equalsIgnoreCase("specialiste")) {
            sql = """
            SELECT r.id, c.date_heure, u.nom, u.prenom
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON r.id_patient = u.id
            WHERE c.id_specialiste = ? AND c.date_heure """ + conditionTemps + " ORDER BY c.date_heure";
        } else {
            return rdvs;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idRdv = rs.getInt("id");
                String dateHeure = rs.getTimestamp("date_heure").toLocalDateTime().toString();

                if (role.equalsIgnoreCase("patient")) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String specialisation = rs.getString("specialisation");
                    rdvs.put(idRdv, dateHeure + " avec Dr. " + prenom + " " + nom + " (" + specialisation + ")");
                } else {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    rdvs.put(idRdv, dateHeure + " - Patient : " + prenom + " " + nom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rdvs;
    }
    public Avis getAvisPourRendezVous(int idRendezVous) {
        String sql = "SELECT note, commentaire FROM avis WHERE id_rendez_vous = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRendezVous);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int note = rs.getInt("note");
                String commentaire = rs.getString("commentaire");
                return new Avis(idRendezVous, note, commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun avis trouvé
    }



}
