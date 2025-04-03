package Dao;

import Modele.Lieu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuDAOImpl implements LieuDAO {
    private DaoFactory daoFactory;

    public LieuDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addLieu(Lieu lieu) {
        String query = "INSERT INTO lieu (nom_lieu, adresse) VALUES (?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lieu.getNomLieu());
            stmt.setString(2, lieu.getAdresse());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Lieu getLieuById(int id) {
        String query = "SELECT * FROM lieu WHERE id_lieu = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("nom_lieu"),
                        rs.getString("adresse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Lieu> getAllLieux() {
        List<Lieu> lieux = new ArrayList<>();
        String query = "SELECT * FROM lieu";
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lieux.add(new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("nom_lieu"),
                        rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieux;
    }

    @Override
    public void updateLieu(Lieu lieu) {
        String query = "UPDATE lieu SET nom_lieu = ?, adresse = ? WHERE id_lieu = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lieu.getNomLieu());
            stmt.setString(2, lieu.getAdresse());
            stmt.setInt(3, lieu.getIdLieu());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLieu(int id) {
        String query = "DELETE FROM lieu WHERE id_lieu = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
