package Modele;
/**
 * Représente un avis donné par un patient sur un rendez vous.
 * * @author Paul
 *  * @version 1.0
 */
public class Avis {
    /**
     * Identifiant du rendez vous concerné.
     */
    private int idRendezVous;
    /**
     * Note attribuée
     */
    private int note;
    /**
     * Commentaire associé à l’avis.
     */
    private String commentaire;
    /**
     * Crée un nouvel avis.
     * @param idRendezVous L'identifiant du rendez-vous.
     * @param note La note attribuée.
     * @param commentaire Le commentaire.
     */
    public Avis(int idRendezVous, int note, String commentaire) {
        this.idRendezVous = idRendezVous;
        this.note = note;
        this.commentaire = commentaire;
    }
/*

    public int getIdRendezVous() {
        return idRendezVous;
    }*/
    /**
     * Récupère la note de l'avis.
     * @return note entière (1 à 5)
     */
    public int getNote() {
        return note;
    }

    /**
     * Récupère le commentaire.
     * @return texte du commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }
}
