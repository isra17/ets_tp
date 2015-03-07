package ca.etsmtl.log660.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public final class RolePK implements Serializable {
    @ManyToOne
    @JoinColumn(name="IDPARTICIPANT", nullable = false)
    private Participant participant;

    @ManyToOne
    @JoinColumn(name="IDFILM", nullable = false)
    private Film film;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
