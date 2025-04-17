public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String specialisation;
    private String ville;
    private String rue;

    // === Constructeur de base (ancien)
    public Utilisateur(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // === Constructeur complet (optionnel selon besoins)
    public Utilisateur(int id, String nom, String prenom, String email, String role, String specialisation, String ville, String rue) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.specialisation = specialisation;
        this.ville = ville;
        this.rue = rue;
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public String getVille() {
        return ville;
    }

    public String getRue() {
        return rue;
    }

    // === toString() pour affichage dans JComboBox ===
    @Override
    public String toString() {
        return prenom + " " + nom + (specialisation != null ? " - " + specialisation : "");
    }
}
