package Controleur;

import Dao.DaoFactory;
import Dao.UtilisateurDAO;
import Dao.UtilisateurDAOImpl;
import Modele.Utilisateur;

public class LoginControleur {

    private static final UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(new DaoFactory());

    public static Utilisateur seConnecter(String email, String motDePasse) {
        return utilisateurDAO.getByEmailAndPassword(email, motDePasse);
    }
}
