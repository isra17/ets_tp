package ca.etsmtl.log660.model;

import javax.persistence.*;

@Entity
@Table(name="Biographies")
public class Biographie {
    @Id
    @Column(name="IDPARTICIPANT")
    private Long idParticipant;

    @OneToOne
    @JoinColumn(name="IDPARTICIPANT", nullable = false)
    private Participant participant;

    @Column(name="BIOGRAPHIE", nullable = false)
    private String contenu;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
