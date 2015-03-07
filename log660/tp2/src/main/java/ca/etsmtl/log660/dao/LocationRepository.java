package ca.etsmtl.log660.dao;

import ca.etsmtl.log660.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Client, Long>, LocationRepositoryCustom {
    @Query("SELECT COUNT(*) FROM Location l WHERE l.dateRetour IS NULL AND l.client = :client")
    int countActiveLocationByClient(@Param("client") Client client);
}
