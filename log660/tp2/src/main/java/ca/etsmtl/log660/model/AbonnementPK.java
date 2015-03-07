package ca.etsmtl.log660.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public final class AbonnementPK implements Serializable {
    @ManyToOne
    @JoinColumn(name="IDFORFAIT", nullable = false)
    private Forfait forfait;

    @ManyToOne
    @JoinColumn(name="IDCLIENT", nullable = false)
    private Client client;

    public Forfait getForfait() {
        return forfait;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
