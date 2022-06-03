package consoleApp.repository;


import consoleApp.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectorsRepository extends JpaRepository<Lector, Long> {

    Optional<Lector> findByFio(String aLong);

    @Query(value = "select id, degree, fio, last_name, name, salary " +
            "from lectors as a inner join lector_department b on a.id = b.lector_id where b.department_id = :departmentId", nativeQuery = true)
    List<Lector> findAllByDepartmentId(Long departmentId);

    @Query(value = "select fio from lectors", nativeQuery = true)
    List<String> getAllFios();
}
