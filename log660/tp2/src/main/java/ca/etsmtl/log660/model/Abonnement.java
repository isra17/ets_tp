package ca.etsmtl.log660.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Abonnements")
public class Abonnement {

    @EmbeddedId
    private AbonnementPK idCompound;

    @Column(name="DATEDEBUT", nullable = false)
    private Date dateDebut;

    public Forfait getForfait() { return idCompound.getForfait(); }

    public Client getClient() { return idCompound.getClient(); }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
}
