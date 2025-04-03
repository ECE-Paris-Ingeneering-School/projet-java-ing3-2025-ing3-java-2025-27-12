package Dao;

import Modele.Specialiste;
import java.util.List;

public interface SpecialisteDAO {
    void addSpecialiste(Specialiste specialiste);
    Specialiste getSpecialisteById(int id);
    List<Specialiste> getAllSpecialistes();
    void updateSpecialiste(Specialiste specialiste);
    void deleteSpecialiste(int id);
}
