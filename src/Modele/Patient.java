package Modele;

public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String typePatient;

    public Patient(int idPatient, String nom, String prenom, String email, String motDePasse, String typePatient) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typePatient = typePatient;
    }

    // Getters et Setters
    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTypePatient() {
        return typePatient;
    }

    public void setTypePatient(String typePatient) {
        this.typePatient = typePatient;
    }
}
