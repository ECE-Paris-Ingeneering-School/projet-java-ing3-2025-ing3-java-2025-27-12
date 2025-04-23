import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import Modele.Avis;

public class CreneauDAO {

    private Connection connection;

    public CreneauDAO(Connection connection) {
        this.connection = connection;
    }



    /*
    public List<Creneau> getCreneauxDisponiblesPour(int idSpecialiste) {
        List<Creneau> creneaux = nList<>();
        String sql = """
        SELECT c.id, c.date_heure, c.disponible,
              r u ON c.id_specialiste = u.id
        WHERE c.id_specialiste = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt= ps.executeQuery();

            while
                int id = rs.getInt("id");
                int idSpec = rs.getInt("id_spec");
                LocalDateTime dateHeure = rs.getTimestamp("date_heure").toLocalDateTime();
                boolean dis.getString("specialisation");

                creneaux.add(new Crenispo, nom, prenom, specialisation));
            }

        } catch (SQLException elkjkb) {
          tStackTrace();
        }

        return creneaux;
    }
    */

    /*
    public Lis> patients = new ArrayList<>();

        String sql = """
        SELECT u.nom, u.prenom, u.email, r.date_reservation, c.date_heure
        FROM rendez_vous r


        JOIN creneau c ON r.id_creneau = c.id
        JOIN uti_specialiste = ?
        ORDER BY c.date_heure
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInrs = ps.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                Stringsa = rs.getTimestamp("date_reservation").toLocalDateTime().toString();

                String ligne = " +Heure + " | Réservé le : " + dateResa;
                patients.add(ligne);
            }

        } catch (SQLEeption e) {
            e.printStackTrace();
        }

        return patients;
    }
    */

    public List<String>      rechercherPatientEtRendezVousAvecFiltre(int idSpecialiste, String emailPatient, boolean afficherAVenir) {
        List<String> resultats = new ArrayList<>();

        String sql = "SELECT r.id AS id_rdv, u.nom, u.prenom, u.email, c.date_heure " +
                "FROM rendez_vous r " +

                "JOIN utilisateur u ON u.id = r.id_patient " +

                "JOIN creneau c ON c.id = r.id_creneau " +
                "WHERE c.id_specialiste = ? AND u.email = ? AND " +
                (afficherAVenir ? "c.date_heure >= NOW()" : "c.date_heure < NOW()") +
                " ORDER BY c.date_heure DESC";

        try (PreparedStatement stmt =
                     connection.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);
            stmt.setString(2, emailPatient);

            ResultSet rs =
                    stmt.executeQuery();
            while (rs.next()) {
                String nom
                        = rs.getString("nom");
                String prenom
                        = rs.getString("prenom");
                  String email
                        = rs.getString("email");
                String date
                        = rs.getTimestamp("date_heure").toLocalDateTime().toString();

                resultats.add("🕓 " + date + " - " + prenom + " " + nom);
                resultats.add("EMAIL:" + email); // utilisé pour bouton mail
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resultats;
    }
    public void         annulerRendezVous(int idRendezVous) throws SQLException {
        String getCreneau = "SELECT id_creneau FROM rendez_vous WHERE id = ?";


        String deleteRDV
                = "DELETE FROM rendez_vous WHERE id = ?";
        String setDispo
                = "UPDATE creneau SET disponible = TRUE WHERE id = ?";

        try (PreparedStatement ps1
                     = connection.prepareStatement(getCreneau);
             PreparedStatement ps2
                     = connection.prepareStatement(deleteRDV);
             PreparedStatement ps3
                     = connection.prepareStatement(setDispo)) {


            ps1.setInt(1, idRendezVous);
            ResultSet rs = ps1.executeQuery();

            int idCreneau = -1;

            if (rs.next()) {

                idCreneau = rs.getInt("id_creneau");
            }

            if (idCreneau != -1) {

                ps2.setInt(1, idRendezVous);
                ps2.executeUpdate();



                ps3.setInt(1, idCreneau);
                ps3.executeUpdate();
            }


        }


    }


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


                updateStmt
                        .setInt(1, idCreneau);
                updateStmt.
                        executeUpdate();


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


public      List<Creneau> rechercherCreneauxComplet(String specialite, String ville, LocalDate date) {
            List<Creneau> resultats = new ArrayList<>();

        String sql = "SELECT c.*, u.nom, u.prenom, u.specialisation, u.ville " +
                "FROM creneau c " +

                "JOIN utilisateur u ON u.id = c.id_specialiste " +
                "WHERE u.specialisation = ? AND DATE(c.date_heure) = ?" +
                (ville != null ? " AND u.ville = ?" : "") +
                " AND c.disponible = TRUE";

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, specialite);

                    stmt.setDate(2, Date.valueOf(date));

                    if (ville != null) {
                        stmt.setString(3, ville);
                    }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                int idSpec = rs.getInt("id_specialiste");

                LocalDateTime dateHeure = rs.getTimestamp("date_heure").toLocalDateTime();
                boolean dispo = rs.getBoolean("disponible");

                Creneau c = new Creneau(
                        id,
                        idSpec,

                        dateHeure,

                        dispo,
                        rs.getString("nom"),

                        rs.getString("prenom"),
                        rs.getString("specialisation")
                );
                c.setVille(rs.getString("ville"));

                resultats.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }






    public Integer[] getOccupationPourSpecialiste(int idSpecialiste) {
        Integer[] stats = new Integer[2]; // [disponibles, réservés]
        String sql = "SELECT " +
                "SUM(CASE WHEN disponible = TRUE THEN 1 ELSE 0 END) AS disponibles, " +

                "SUM(CASE WHEN disponible = FALSE THEN 1 ELSE 0 END) AS reserves " +
                "FROM creneau WHERE id_specialiste = ?";

        try         (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);
            try         (ResultSet rs = stmt.executeQuery()) {
                if       (rs.next()) {
                    stats[0]
                            = rs.getInt("disponibles");

                    stats[1]
                            = rs.getInt("reserves");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
    public void genererCreneauxMoisCompletSiManquants(int idSpecialiste) {
        LocalDate aujourdHui
                = LocalDate.now();
        LocalDate dateFin
                = aujourdHui.plusWeeks(4); // 1 mois

        for (LocalDate date
             = aujourdHui; date.isBefore(dateFin); date = date.plusDays(1)) {

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

            String sql
                    = "INSERT INTO creneau (id_specialiste, date_heure, disponible) VALUES (?, ?, 1)";
            PreparedStatement stmt
                    = connection.prepareStatement(sql);

            stmt.setInt(1, idSpecialiste);
            stmt.setTimestamp(2, Timestamp.valueOf(dateHeure));
            stmt.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public List<Creneau> getCreneauxPourJour(int idSpecialiste, LocalDate date) {
        List<Creneau> creneaux
                = new ArrayList<>();

        String sql = "SELECT * FROM creneau WHERE id_specialiste = ? AND DATE(date_heure) = ? ORDER BY date_heure";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idSpecialiste);
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id
                        = rs.getInt("id");
                Timestamp dateHeure
                        = rs.getTimestamp("date_heure");
                boolean dispo = rs.getBoolean("disponible");

                String nom = "";
                String prenom = "";
                String specialisation = "";

                creneaux.add(new Creneau(id, idSpecialiste, dateHeure.toLocalDateTime(), dispo, nom, prenom, specialisation));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creneaux;
             }
    public       List<String> getRendezVousPourUtilisateur(int idUtilisateur, String role, boolean aVenir) {
        List<String> resultats = new ArrayList<>();

        String sql = """
        SELECT r.id AS id_rdv, c.date_heure, 
               s.nom AS nom_specialiste, s.prenom AS prenom_specialiste,
               p.nom AS nom_patient, p.prenom AS prenom_patient
        FROM rendez_vous r
        JOIN creneau c ON r.id_creneau = c.id
            
        JOIN utilisateur s ON c.id_specialiste = s.id
        JOIN utilisateur p ON r.id_patient = p.id
        WHERE 
    """;

        if (role.equals("patient")) {
            sql += "p.id = ?";
        } else if (role.equals("specialiste")) {
            sql += "s.id = ?";
        } else {
            return resultats; ///
        }

        sql += aVenir ? " AND c.date_heure >= NOW()" : " AND c.date_heure < NOW()";
        sql += " ORDER BY c.date_heure DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);

            ResultSet rs
                    = stmt.executeQuery();

            while (rs.next()) {
                LocalDateTime dateHeure
                        = rs.getTimestamp("date_heure").toLocalDateTime();

                String ligne;
                if (role.equals("patient")) {
                    String nom = rs.getString("nom_specialiste");

                    String prenom = rs.getString("prenom_specialiste");
                        ligne = "RDV avec Dr. " + prenom + " " + nom + " le " + dateHeure;
                } else {

                    String nom = rs.getString("nom_patient");
                        String prenom = rs.getString("prenom_patient");
                        ligne = "RDV avec " + prenom + " " + nom + " le " + dateHeure;
                }

                resultats.add(ligne);

            }

        } catch (SQLException e) {


            e.printStackTrace();
        }

                return       resultats;
    }

    public   boolean      avisExistePourRendezVous(int idRendezVous) {
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



    public       List<String>         getRendezVousPourUtilisateurAvecId(int idUtilisateur, String role, boolean aVenir, Map<String, Integer> mapIdRdv) {
        List<String> resultats = new ArrayList<>();

        String sql;
        if (role.equalsIgnoreCase("patient")) {
            sql = "SELECT r.id AS id_rdv, c.date_heure, s.nom AS nom_spe, s.prenom AS prenom_spe " +
                    "FROM rendez_vous r " +
                    "JOIN creneau c ON r.id_creneau = c.id " +


                    "JOIN utilisateur s ON s.id = c.id_specialiste " +

                    "WHERE r.id_patient = ?" +

                    (aVenir ? " AND c.date_heure >= NOW()" : " AND c.date_heure < NOW()") +

                    " ORDER BY c.date_heure DESC";
        } else if (role
                .equalsIgnoreCase("specialiste")) {
            sql = "SELECT r.id AS id_rdv, c.date_heure, p.nom AS nom_pat, p.prenom AS prenom_pat " +
                    "FROM rendez_vous r " +

                    "JOIN creneau c ON r.id_creneau = c.id " +
                    "JOIN utilisateur p ON p.id = r.id_patient " +

                    "WHERE c.id_specialiste = ?" +
                    (aVenir ? " AND c.date_heure >= NOW()" : " AND c.date_heure < NOW()") +
                    " ORDER BY c.date_heure DESC";
        } else {
            return resultats;
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRdv = rs.getInt("id_rdv");
                Timestamp dateHeure = rs.getTimestamp("date_heure");

                String nom = role.equals("patient") ? rs.getString("nom_spe") : rs.getString("nom_pat");
                String prenom = role.equals("patient") ? rs.getString("prenom_spe") : rs.getString("prenom_pat");


                String ligne = "RDV avec Dr. " + prenom + " " + nom + " le " + dateHeure.toLocalDateTime();

                resultats.add(ligne);
                mapIdRdv.put(ligne, idRdv);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resultats;
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
        return null;
    }

    public double getMoyenneNotePourSpecialiste(int idSpecialiste) {
        String sql = "SELECT AVG(a.note) as moyenne FROM avis a " +
                "JOIN rendez_vous r ON a.id_rendez_vous = r.id " +

                "JOIN creneau c ON r.id_creneau = c.id " +
                "WHERE c.id_specialiste = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("moyenne");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public   List<String> getCommentairesPourSpecialiste(int idSpecialiste) {
         List<String> commentaires = new ArrayList<>();

        String sql
                = "SELECT a.commentaire FROM avis a " +
                "JOIN rendez_vous r ON a.id_rendez_vous = r.id " +
                "JOIN creneau c ON r.id_creneau = c.id " +
                "WHERE c.id_specialiste = ? AND a.commentaire IS NOT NULL AND a.commentaire <> ''";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);


            ResultSet rs = stmt.executeQuery();

            while (rs
                    .next()) {
                commentaires.add(rs.getString("commentaire"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentaires;
            }


}
