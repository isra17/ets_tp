package ca.etsmtl.log660.model;

import javax.persistence.*;

@Entity
@Table(name="Roles")
public class Role {
    @EmbeddedId
    private RolePK idCompound;

    @ManyToOne
    @JoinColumn(name = "IDFILM", insertable = false, updatable = false)
    private Film film;
    @ManyToOne
    @JoinColumn(name = "IDPARTICIPANT", insertable = false, updatable = false)
    private Participant participant;

    @Column(name="Personnage", nullable = false)
    public String personnage;

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }

    public Participant getParticipant() {
        return idCompound.getParticipant();
    }

    public Film getFilm() {
        return idCompound.getFilm();
    }
}
