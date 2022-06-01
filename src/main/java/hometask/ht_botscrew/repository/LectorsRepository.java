package hometask.ht_botscrew.repository;


import hometask.ht_botscrew.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectorsRepository extends JpaRepository<Lector, Long> {

    Optional<Lector> findByFio(String aLong);
}
