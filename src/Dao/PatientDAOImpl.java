package Dao;

import Modele.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    private DaoFactory daoFactory;

    public PatientDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addPatient(Patient patient) {
        String query = "INSERT INTO patient (nom, prenom, email, mot_de_passe, type_patient) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getMotDePasse());
            stmt.setString(5, patient.getTypePatient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatientById(int id) {
        String query = "SELECT * FROM patient WHERE id_patient = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("type_patient")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patient";
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("type_patient")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public void updatePatient(Patient patient) {
        String query = "UPDATE patient SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, type_patient = ? WHERE id_patient = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getMotDePasse());
            stmt.setString(5, patient.getTypePatient());
            stmt.setInt(6, patient.getIdPatient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatient(int id) {
        String query = "DELETE FROM patient WHERE id_patient = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
