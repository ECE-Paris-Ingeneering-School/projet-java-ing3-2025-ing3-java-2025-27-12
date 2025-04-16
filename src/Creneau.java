import java.time.LocalDateTime;

public class Creneau {
    private int id;
    private int idSpecialiste;
    private LocalDateTime dateHeure;
    private boolean disponible;
    private String nomSpecialiste;
    private String prenomSpecialiste;
    private String specialisation;


    public Creneau(int id, int idSpecialiste, LocalDateTime dateHeure, boolean disponible,
                   String nom, String prenom, String specialisation) {
        this.id = id;
        this.idSpecialiste = idSpecialiste;
        this.dateHeure = dateHeure;
        this.disponible = disponible;
        this.nomSpecialiste = nom;
        this.prenomSpecialiste = prenom;
        this.specialisation = specialisation;
    }
    public String getNomSpecialiste() { return nomSpecialiste; }
    public String getPrenomSpecialiste() { return prenomSpecialiste; }
    public String getSpecialisation() { return specialisation; }


    public int getId() { return id; }
    public int getIdSpecialiste() { return idSpecialiste; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public boolean isDisponible() { return disponible; }

    @Override
    public String toString() {
        return dateHeure.toString();
    }
}
