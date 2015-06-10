package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepositoryCustom{

    @Autowired
    private EntityManager em;

    @Override
    public List<Film> search(String titre, Long anneeMin, Long anneeMax, String pays, String langue, String genre, String realisateur, String acteur) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Film> q = builder.createQuery(Film.class);
        Root<Film> rootFilm = q.from(Film.class);

        //Construct predicates
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(!titre.isEmpty())
            predicates.add(builder.like(builder.lower(rootFilm.<String>get("titre")), "%" +titre.toLowerCase()+ "%"));
        
        if(anneeMin != -1){
            predicates.add(builder.ge(rootFilm.<Integer>get("anneeSortie"), anneeMin));
        } 
        if(anneeMax != -1){
            predicates.add(builder.le(rootFilm.<Integer>get("anneeSortie"), anneeMax));
        }
        
        if(!pays.isEmpty()) {
            Join<Film, Pays> paysJoin = rootFilm.join("pays");
            predicates.add(builder.like(builder.lower(paysJoin.<String>get("nom")), "%" + pays.toLowerCase() + "%"));
        }
        
        if(!langue.isEmpty())
            predicates.add(builder.equal(rootFilm.get("langue"), langue));
        
        if(!genre.isEmpty()) {
            Join<Film, Genre> genresJoin = rootFilm.join("genres");
            predicates.add(builder.like(builder.lower(genresJoin.<String>get("titre")), "%" + genre.toLowerCase() + "%"));
        }

        if(!realisateur.isEmpty()) {
            Join<Film, Participant> realisateurJoin = rootFilm.join("realisateur");
            predicates.add(builder.like(builder.lower(realisateurJoin.<String>get("nomComplet")), "%" + realisateur.toLowerCase() + "%"));
        }

        if(!acteur.isEmpty()) {
            Join<Film, Role> roleJoin = rootFilm.join("roles");
            Join<Participant, Role> acteurJoin = roleJoin.join("participant");

            predicates.add(builder.like(builder.lower(acteurJoin.<String>get("nomComplet")), "%" + acteur.toLowerCase() + "%"));
        }
        
        //Construct query
        q.select(rootFilm)
         .where(predicates.toArray(new Predicate[]{}));
        
        TypedQuery<Film> query = em.createQuery(q);
        
        return query.getResultList();        
    }

    @Override
    public List<String> getLanguesDisponibles() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> q = cb.createQuery(String.class);
        Root<Film> m = q.from(Film.class);
        q.select(m.<String>get("langue"));
        q.groupBy(m.get("langue"));
        q.orderBy(cb.asc(m.get("langue")));
        TypedQuery<String> query = em.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Film> getFilmRecommendations(Long filmId) {
        return null;
    }
}
