package Dao;

import Modele.Patient;
import java.util.List;

public interface PatientDAO {
    void addPatient(Patient patient);
    Patient getPatientById(int id);
    List<Patient> getAllPatients();
    void updatePatient(Patient patient);
    void deletePatient(int id);
}
