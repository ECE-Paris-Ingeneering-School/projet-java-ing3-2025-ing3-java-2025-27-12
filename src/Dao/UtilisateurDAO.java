package Dao;

import Modele.Utilisateur;
import java.util.List;

public interface UtilisateurDAO {
    void addUtilisateur(Utilisateur utilisateur);
    Utilisateur getUtilisateurById(int id);
    List<Utilisateur> getAllUtilisateurs();
    void updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(int id);
    Utilisateur getByEmailAndPassword(String email, String motDePasse);

}
