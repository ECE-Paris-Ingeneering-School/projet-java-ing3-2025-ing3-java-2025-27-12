package Dao;

import Modele.Specialiste;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialisteDAOImpl implements SpecialisteDAO {
    private DaoFactory daoFactory;

    public SpecialisteDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addSpecialiste(Specialiste specialiste) {
        String query = "INSERT INTO specialiste (nom, prenom, specialisation, qualification) VALUES (?, ?, ?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, specialiste.getNom());
            stmt.setString(2, specialiste.getPrenom());
            stmt.setString(3, specialiste.getSpecialisation());
            stmt.setString(4, specialiste.getQualification());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Specialiste getSpecialisteById(int id) {
        String query = "SELECT * FROM specialiste WHERE id_specialiste = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Specialiste> getAllSpecialistes() {
        List<Specialiste> specialistes = new ArrayList<>();
        String query = "SELECT * FROM specialiste";
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                specialistes.add(new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialistes;
    }

    @Override
    public void updateSpecialiste(Specialiste specialiste) {
        String query = "UPDATE specialiste SET nom = ?, prenom = ?, specialisation = ?, qualification = ? WHERE id_specialiste = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, specialiste.getNom());
            stmt.setString(2, specialiste.getPrenom());
            stmt.setString(3, specialiste.getSpecialisation());
            stmt.setString(4, specialiste.getQualification());
            stmt.setInt(5, specialiste.getIdSpecialiste());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpecialiste(int id) {
        String query = "DELETE FROM specialiste WHERE id_specialiste = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
