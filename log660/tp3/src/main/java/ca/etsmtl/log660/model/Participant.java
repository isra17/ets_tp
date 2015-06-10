package ca.etsmtl.log660.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;


@Entity
@Table(name="Participants")
public class Participant {

    @Id
    @Column(name="IDPARTICIPANT")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idParticipant;
    
    @Column(name="NOM")
    private String nom;
    
    @Column(name="PRENOM")
    private String prenom;
    
    @Column(name="LIEUNAISSANCE")
    private String lieuNaissance;

    @OneToOne(mappedBy="participant", fetch=FetchType.EAGER)
    private Biographie biographie;

    @Formula("prenom||' '||nom")
    private String nomComplet;


    public Long getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomComplet() { return this.prenom + " " + this.nom; }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Biographie getBiographie() {
        return biographie;
    }

    public void setBiographie(Biographie biographie) {
        this.biographie = biographie;
    }
}
