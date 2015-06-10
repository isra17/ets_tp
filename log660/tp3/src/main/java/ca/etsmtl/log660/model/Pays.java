package ca.etsmtl.log660.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Pays")
public class Pays {
    @Id
    @Column(name="IDPAYS")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idPays;
    
    @Column(name="NOM", unique = true, nullable = false)
    private String nom;

    public Long getIdPays() {
        return idPays;
    }

    public void setIdPays(Long idPays) {
        this.idPays = idPays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
