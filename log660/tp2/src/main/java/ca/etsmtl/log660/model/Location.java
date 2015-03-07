package ca.etsmtl.log660.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Locations")
public class Location {
    @Id
    @Column(name="IDLOCATION")
    @GeneratedValue(generator="Identity12c")
    @GenericGenerator(name="Identity12c", strategy="org.hibernate.id.Ora12IdentityGenerator")
    private Long idLocation;

    @Column(name="DATESORTIE", nullable = false)
    private Date dateSortie;

    @Column(name="DATERETOUR")
    private Date dateRetour;

    @ManyToOne
    @JoinColumn(name="IDCLIENT", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name="CODE", nullable = false)
    private CopieFilm copie;

    public CopieFilm getCopie() {
        return copie;
    }

    public void setCopie(CopieFilm copie) {
        this.copie = copie;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    private void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
