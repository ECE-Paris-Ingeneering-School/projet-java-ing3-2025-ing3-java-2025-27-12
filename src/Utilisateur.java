
/**
 * Représente un utilisateur de l'application.
 * Peut être un patient, un spécialiste ou un administrateur.
 * Contient des informations personnelles et professionnelles.
 *  * @author Arthur
 *  * @version 1.0
*/
public class Utilisateur {
    /**
     * Constructeur principal.
     *
     * @param id             Identifiant de l'utilisateur
     * @param nom            Nom de l'utilisateur
     * @param prenom         Prénom de l'utilisateur
     * @param email          Email de l'utilisateur
     * @param motDePasse     Mot de passe (haché ou clair selon usage)
     * @param rue            Rue de résidence
     * @param ville          Ville de résidence
     * @param specialisation Spécialité (si c'est un spécialiste)
     * @param role           Rôle : patient, specialiste, ou admin
     */
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
    /**
     * Retourne une représentation textuelle de l'utilisateur.
     * @return une chaîne lisible contenant le nom et l'email
     */
    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ")";
    }
}
