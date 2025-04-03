package Dao;

import Modele.Lieu;
import java.util.List;

public interface LieuDAO {
    void addLieu(Lieu lieu);
    Lieu getLieuById(int id);
    List<Lieu> getAllLieux();
    void updateLieu(Lieu lieu);
    void deleteLieu(int id);
}
