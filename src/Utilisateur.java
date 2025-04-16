public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ")";
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

}
