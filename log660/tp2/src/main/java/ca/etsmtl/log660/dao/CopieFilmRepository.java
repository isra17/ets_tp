package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.CopieFilm;
import ca.etsmtl.log660.model.Film;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CopieFilmRepository extends JpaRepository<CopieFilm, String> {

        @Query("SELECT c FROM CopieFilm c WHERE c.film = :film AND " +
                "NOT EXISTS (SELECT l FROM Location l WHERE l.copie = c AND l.dateRetour IS NULL)")
        List<CopieFilm> findAvailableByFilm(@Param("film") Film film, Pageable pageable);
}
