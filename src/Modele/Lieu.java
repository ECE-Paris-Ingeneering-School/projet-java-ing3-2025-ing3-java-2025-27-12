package Modele;

public class Lieu {
    private int idLieu;
    private String nomLieu;
    private String adresse;

    public Lieu(int idLieu, String nomLieu, String adresse) {
        this.idLieu = idLieu;
        this.nomLieu = nomLieu;
        this.adresse = adresse;
    }

    // Getters et Setters
    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
