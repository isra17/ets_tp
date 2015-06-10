package ca.etsmtl.log660.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Copiefilms")
public class CopieFilm {
    @Id
    @Column(name="CODE", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name="IDFILM", nullable = false)
    private Film film;

    @OneToMany(mappedBy="copie")
    private Set<Location> locations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}