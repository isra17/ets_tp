package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.CopieFilm;
import ca.etsmtl.log660.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Film, Long>, MovieRepositoryCustom {
}
