package Modele;

public class Specialiste {
    private int idSpecialiste;
    private String nom;
    private String prenom;
    private String specialisation;
    private String qualification;

    public Specialiste(int idSpecialiste, String nom, String prenom, String specialisation, String qualification) {
        this.idSpecialiste = idSpecialiste;
        this.nom = nom;
        this.prenom = prenom;
        this.specialisation = specialisation;
        this.qualification = qualification;
    }

    // Getters et Setters
    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
