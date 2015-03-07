package ca.etsmtl.log660.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Clients")
@PrimaryKeyJoinColumn(name="IDCLIENT")
public class Client extends User {

    @Column(name="CCNUMERO", nullable = false)
    private String ccNumero;

    @Column(name="CCTYPE", nullable = false)
    private String ccType;

    @Column(name="CCCVV", nullable = false)
    private Long ccCvv;

    @Column(name="CCEXPIRATION", nullable = false)
    private Date ccExpiration;

    @OneToOne(mappedBy="idCompound.client", cascade=CascadeType.ALL)
    private Abonnement abonnement;

    public String getCcNumero() {
        return ccNumero;
    }

    public void setCcNumero(String ccNumero) {
        this.ccNumero = ccNumero;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public Long getCcCvv() {
        return ccCvv;
    }

    public void setCcCvv(Long ccCvv) {
        this.ccCvv = ccCvv;
    }

    public Date getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(Date ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
