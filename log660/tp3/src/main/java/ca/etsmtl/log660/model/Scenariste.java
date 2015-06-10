package ca.etsmtl.log660.model;

import javax.persistence.*;

@Entity
@Table(name="Scenaristes")
public class Scenariste {
    @Id
    @Column(name="IDSCENARISTE")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idScenariste;

    @Column(name="NOM", unique = true, nullable = false)
    private String nom;

    public Long getIdScenariste() {
        return idScenariste;
    }

    public void setIdScenariste(Long idScenariste) {
        this.idScenariste = idScenariste;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
