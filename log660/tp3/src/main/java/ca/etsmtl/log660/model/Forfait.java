package ca.etsmtl.log660.model;

import javax.persistence.*;

@Entity
@Table(name="Forfaits")
public class Forfait {
    @Id
    @Column(name="IDFORFAIT")
    @GeneratedValue
    private Long idForfait;

    @Column(name="LOCATIONMAX", nullable = false)
    private Long locationMax;

    @Column(name="DUREEMAX", nullable = false)
    private Long dureeMax;

    @Column(name="NOM", nullable = false)
    private String nom;

    @Column(name="COUT", nullable = false)
    private Long cout;

    public Long getIdForfait() {
        return idForfait;
    }

    public void setIdForfait(Long idForfait) {
        this.idForfait = idForfait;
    }

    public Long getLocationMax() {
        return locationMax;
    }

    public void setLocationMax(Long locationMax) {
        this.locationMax = locationMax;
    }

    public Long getDureeMax() {
        return dureeMax;
    }

    public void setDureeMax(Long dureeMax) {
        this.dureeMax = dureeMax;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getCout() {
        return cout;
    }

    public void setCout(Long cout) {
        this.cout = cout;
    }
}
