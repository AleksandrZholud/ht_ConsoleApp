package consoleApp.repository;

import consoleApp.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    @Query(value = "select COUNT (*) from lectors as a " +
            "inner join lector_department b on a.id = b.lector_id " +
            "where b.department_id = :id and a.degree = :degree", nativeQuery = true)
    int getCountOfLectorsByDepartmentIdAndDegree(Long id, String degree);

    @Query(value = "SELECT AVG(salary) from lectors as a " +
            "inner join lector_department b on a.id = b.lector_id " +
            "where b.department_id = :id", nativeQuery = true)
    BigDecimal getAverageSalaryByDepartmentId(Long id);

    @Query(value = "select name from departments", nativeQuery = true)
    List<String> getAllNames();
}