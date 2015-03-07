package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.Film;
import java.util.List;

public interface MovieRepositoryCustom {
    public List<Film> search(String title, Long yearMin, Long yearMax, String country, String language, String genre, String director, String actor);
    public List<String> getLanguesDisponibles();
}
