package Dao;

import Modele.RendezVous;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAOImpl implements RendezVousDAO {
    private DaoFactory daoFactory;

    public RendezVousDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addRendezVous(RendezVous rendezVous) {
        String query = "INSERT INTO rendezvous (date_heure, id_patient, id_specialiste, id_lieu, note_patient) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, new Timestamp(rendezVous.getDateHeure().getTime()));
            stmt.setInt(2, rendezVous.getIdPatient());
            stmt.setInt(3, rendezVous.getIdSpecialiste());
            stmt.setInt(4, rendezVous.getIdLieu());
            stmt.setString(5, rendezVous.getNotePatient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RendezVous getRendezVousById(int id) {
        String query = "SELECT * FROM rendezvous WHERE id_rdv = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RendezVous(
                        rs.getInt("id_rdv"),
                        rs.getTimestamp("date_heure"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_specialiste"),
                        rs.getInt("id_lieu"),
                        rs.getString("note_patient")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT * FROM rendezvous";
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rendezVousList.add(new RendezVous(
                        rs.getInt("id_rdv"),
                        rs.getTimestamp("date_heure"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_specialiste"),
                        rs.getInt("id_lieu"),
                        rs.getString("note_patient")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    @Override
    public void updateRendezVous(RendezVous rendezVous) {
        String query = "UPDATE rendezvous SET date_heure = ?, id_patient = ?, id_specialiste = ?, id_lieu = ?, note_patient = ? WHERE id_rdv = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, new Timestamp(rendezVous.getDateHeure().getTime()));
            stmt.setInt(2, rendezVous.getIdPatient());
            stmt.setInt(3, rendezVous.getIdSpecialiste());
            stmt.setInt(4, rendezVous.getIdLieu());
            stmt.setString(5, rendezVous.getNotePatient());
            stmt.setInt(6, rendezVous.getIdRdv());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRendezVous(int id) {
        String query = "DELETE FROM rendezvous WHERE id_rdv = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
