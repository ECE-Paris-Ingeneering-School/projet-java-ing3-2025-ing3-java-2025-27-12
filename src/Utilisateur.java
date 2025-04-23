

// public classe utilisateur
public class Utilisateur {
    private int id;
    private String nom;


    private String prenom;
    private String email;
    private String motDePasse;
    private String role;

    private String specialisation;
    private String ville;
    private String rue;

    public Utilisateur(int id, String nom, String prenom, String email,
                       String role, String specialisation, String ville, String rue) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.specialisation = specialisation;
        this.ville = ville;
        this.rue = rue;
    }
    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse,
                       String rue, String ville, String specialisation, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.rue = rue;
        this.ville = ville;
        this.specialisation = specialisation;
        this.role = role;
    }


    // tous les Getter possibles
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }

    public String getMotDePasse() { return motDePasse; }
    public String getRole() { return role; }

    public String getSpecialisation() { return specialisation; }
    public String getVille() { return ville; }

    public String getRue() { return rue; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ")";
    }
}
