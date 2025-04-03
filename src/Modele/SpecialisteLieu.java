package Modele;

public class SpecialisteLieu {
    private int idSpecialiste;
    private int idLieu;

    public SpecialisteLieu(int idSpecialiste, int idLieu) {
        this.idSpecialiste = idSpecialiste;
        this.idLieu = idLieu;
    }

    // Getters et Setters
    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }
}
