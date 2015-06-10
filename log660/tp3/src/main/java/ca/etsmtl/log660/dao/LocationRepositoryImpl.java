package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.Client;
import ca.etsmtl.log660.model.CopieFilm;
import ca.etsmtl.log660.model.Film;
import ca.etsmtl.log660.model.Location;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Date;

public class LocationRepositoryImpl implements LocationRepositoryCustom {
    @Autowired
    private EntityManager em;

    @Override
    public void createLocation(Location location) {
        em.persist(location);
    }
}
