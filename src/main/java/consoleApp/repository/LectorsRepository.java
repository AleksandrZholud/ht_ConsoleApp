package consoleApp.repository;


import consoleApp.domain.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectorsRepository extends JpaRepository<Lector, Long> {

    Optional<Lector> findByFullName(String fullName);

    @Query(value = "select id, degree, full_name, last_name, name, salary " +
            "from lectors as a inner join lector_department b on a.id = b.lector_id where b.department_id = :departmentId", nativeQuery = true)
    List<Lector> findAllByDepartmentId(Long departmentId);

    @Query(value = "select full_name from lectors", nativeQuery = true)
    List<String> getAllFullNames();
}
