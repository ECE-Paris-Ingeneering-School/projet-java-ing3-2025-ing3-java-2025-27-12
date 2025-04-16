import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "";

        if (role.equalsIgnoreCase("patient")) {
            // Affiche les rdv pris par le patient avec un spécialiste
            sql = """
            SELECT c.date_heure, u.nom, u.prenom, u.specialisation
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON c.id_specialiste = u.id
            WHERE r.id_patient = ?
            ORDER BY c.date_heure
        """;
        } else if (role.equalsIgnoreCase("specialiste")) {
            // Affiche les patients ayant pris un rdv avec ce spécialiste
            sql = """
            SELECT c.date_heure, u.nom, u.prenom
            FROM rendez_vous r
            JOIN creneau c ON r.id_creneau = c.id
            JOIN utilisateur u ON r.id_patient = u.id
            WHERE c.id_specialiste = ?
            ORDER BY c.date_heure
        """;
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
                } else {
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




}
