package Modele;

import java.util.Date;

public class RendezVous {
    private int idRdv;
    private Date dateHeure;
    private int idPatient;
    private int idSpecialiste;
    private int idLieu;
    private String notePatient;

    public RendezVous(int idRdv, Date dateHeure, int idPatient, int idSpecialiste, int idLieu, String notePatient) {
        this.idRdv = idRdv;
        this.dateHeure = dateHeure;
        this.idPatient = idPatient;
        this.idSpecialiste = idSpecialiste;
        this.idLieu = idLieu;
        this.notePatient = notePatient;
    }

    // Getters et Setters
    public int getIdRdv() {
        return idRdv;
    }

    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }

    public Date getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Date dateHeure) {
        this.dateHeure = dateHeure;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

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

    public String getNotePatient() {
        return notePatient;
    }

    public void setNotePatient(String notePatient) {
        this.notePatient = notePatient;
    }
}
