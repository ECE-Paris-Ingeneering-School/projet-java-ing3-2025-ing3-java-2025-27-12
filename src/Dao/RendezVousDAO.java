package Dao;

import Modele.RendezVous;
import java.util.List;

public interface RendezVousDAO {
    void addRendezVous(RendezVous rendezVous);
    RendezVous getRendezVousById(int id);
    List<RendezVous> getAllRendezVous();
    void updateRendezVous(RendezVous rendezVous);
    void deleteRendezVous(int id);
}
