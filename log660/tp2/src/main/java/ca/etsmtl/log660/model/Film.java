package ca.etsmtl.log660.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name="Films")
public class Film {

    @Id
    @Column(name="IDFILM")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idFilm;
    
    @Column(name="TITRE")
    private String titre;
    
    @Column(name="ANNEESORTIE")
    private Long anneeSortie;

    @Column(name="LANGUE")
    private String langue;

    @Column(name="DUREE")
    private String duree;

    @Column(name="RESUME")
    private String resume;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name="FILMPAYS",
      joinColumns={@JoinColumn(name="IDFILM", referencedColumnName="IDFILM")},
      inverseJoinColumns={@JoinColumn(name="IDPAYS", referencedColumnName="IDPAYS")}
    )
    private List<Pays> pays;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name="FILMGENRES",
      joinColumns={@JoinColumn(name="IDFILM", referencedColumnName="IDFILM")},
      inverseJoinColumns={@JoinColumn(name="IDGENRE", referencedColumnName="IDGENRE")}
    )
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="FILMSCENARISTES",
            joinColumns={@JoinColumn(name="IDFILM", referencedColumnName="IDFILM")},
            inverseJoinColumns={@JoinColumn(name="IDSCENARISTE", referencedColumnName="IDSCENARISTE")}
    )
    private List<Scenariste> scenaristes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="IDREALISATEUR")
    Participant realisateur;

    @OneToMany(mappedBy="film")
    private Set<CopieFilm> copies;

    @OneToMany(mappedBy="idCompound.film")
    private Set<Role> roles;

    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(Long anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    
    public List<Pays> getPays(){
        return pays;
    }

    public List<Genre> getGenres(){
        return genres;
    }

    public List<Scenariste> getScenaristes(){
        return scenaristes;
    }

    public Set<Role> getRoles(){
        return roles;
    }

    public Participant getRealisateur(){
        return realisateur;
    }

    public void setRealisateur(Participant realisateur){
        this.realisateur = realisateur;
    }
}
