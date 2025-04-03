package Modele;

public class Utilisateur {
    private int idUser;
    private String email;
    private String motDePasse;
    private String role; // "admin" ou "patient"

    public Utilisateur(int idUser, String email, String motDePasse, String role) {
        this.idUser = idUser;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
