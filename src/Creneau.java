import java.time.LocalDateTime;
/**
 * Représente un créneau de rendez-vous.
 * Chaque créneau est associé à un spécialiste et a une date, une disponibilité, et des informations d'affichage.
 *
 * @author Mathis
 * @version 1.0
 */
public class Creneau {
    /**
     * Constructeur de la classe Creneau.
     *
     * @param id identifiant unique du créneau
     * @param idSpecialiste identifiant du spécialiste associé
     * @param dateHeure date et heure du créneau
     * @param disponible indique si le créneau est disponible
     * @param nom nom du spécialiste
     * @param prenom prénom du spécialiste
     * @param specialisation spécialisation du spécialiste
     */
    private int id;
    private int idSpecialiste;
    private LocalDateTime dateHeure;
    private boolean disponible;
    private String nomSpecialiste;
    private String prenomSpecialiste;


    private String specialisation;
    private String ville; // ✅ Nouveau champ


    public Creneau(int id, int idSpecialiste, LocalDateTime dateHeure, boolean disponible,
                   String nom, String prenom, String specialisation) {
        this.id = id;

        this.idSpecialiste
                = idSpecialiste;
        this.dateHeure
                = dateHeure;
        this.disponible
                = disponible;
        this.nomSpecialiste
                = nom;
        this.prenomSpecialiste
                = prenom;
        this.specialisation
                = specialisation;
    }

    /**
     * @return identifiant du créneau
     */
    public int getId() { return id; }
   // public int getIdSpecialiste() { return idSpecialiste; }
    /**
     * @return une Date
     */
    public LocalDateTime getDateHeure() { return dateHeure; }
    /**
     * @return si disponible
     */
    public boolean isDisponible() { return disponible; }
    /**
     * @return un nom de spécialiste
     */
    public String getNomSpecialiste() { return nomSpecialiste; }
    /**
     * @return un prénomde spécialiste
     */
    public String getPrenomSpecialiste() { return prenomSpecialiste; }
    /**
     * @return une spécialisation
     */
    public String getSpecialisation() { return specialisation; }
    /**
     * @return une ville
     */
    public String getVille() { return ville; }


    public void setVille(String ville) {
        /**
         * Définit la ville du spécialiste pour ce créneau.
         *
         * @param ville nom de la ville
         */

        this.ville = ville;
    }

            @Override
            public String toString() {
                return dateHeure.toString();
            }
}

