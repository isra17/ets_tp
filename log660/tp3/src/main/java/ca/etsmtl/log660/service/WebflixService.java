package ca.etsmtl.log660.service;

import ca.etsmtl.log660.dao.CopieFilmRepository;
import ca.etsmtl.log660.dao.LocationRepository;
import ca.etsmtl.log660.dao.MovieRepository;
import ca.etsmtl.log660.dao.ParticipantRepository;
import ca.etsmtl.log660.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WebflixService {

    @Autowired
    private ParticipantRepository participantRepo;

    @Autowired
    private MovieRepository filmRepo;

    @Autowired
    private CopieFilmRepository copieRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private EntityManager em;

    public List<Film> getFilms(){
        return filmRepo.findAll();
    }
    
    public List<Film> searchFilms(String titre, Long anneeMin, Long anneeMax, String pays, String langue, String genre, String realisateur, String acteur) {
        return filmRepo.search(titre, anneeMin, anneeMax, pays, langue, genre, realisateur, acteur);
    }

    public List<Participant> getParticipants(){
        return participantRepo.findAll();
    }

    public List<String> getLangues() { return filmRepo.getLanguesDisponibles(); }

    public void rentFilm(Client client, Long filmId) {
        Film film = filmRepo.getOne(filmId);
        Pageable first = new PageRequest(0, 1);
        List<CopieFilm> copies = copieRepo.findAvailableByFilm(film, first);
        if (copies.size() == 0) {
            // Raise error: No copy left
            throw new WebflixException("Aucune copie du film n'est disponible");
        }

        Forfait forfait = client.getAbonnement().getForfait();
        if (forfait.getLocationMax() <= locationRepo.countActiveLocationByClient(client)) {
            // Raise error: Max location hit
            throw new WebflixException("Vous avez atteint la limite de location de votre forfait");
        }

        Location location = new Location();
        location.setClient(client);
        location.setCopie(copies.get(0));
        location.setDateSortie(new Date());

        locationRepo.createLocation(location);
    }

    public Object findFilm(Long filmId) {
        return filmRepo.getOne(filmId);
    }

    public Object findFilmRecommendations(Long filmId) {
        return filmRepo.getFilmRecommendations(filmId);
    }

    public Object findParticipant(Long id) {
        return participantRepo.getOne(id);
    }
}
