package Modele;

public class Avis {
    private int idRendezVous;
    private int note;
    private String commentaire;

    public Avis(int idRendezVous, int note, String commentaire) {
        this.idRendezVous = idRendezVous;
        this.note = note;
        this.commentaire = commentaire;
    }

    public int getIdRendezVous() {
        return idRendezVous;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
