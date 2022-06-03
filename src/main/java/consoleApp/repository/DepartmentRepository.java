package consoleApp.repository;

import consoleApp.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    @Query(value = "select count (*) from lector_department where department_id = " +
            "(select id from departments where name = :departmentName)", nativeQuery = true)
    int getCountOfLectorsByDepartmentName(String departmentName);

    @Query(value = "select COUNT (*) from lectors as a " +
            "inner join lector_department b on a.id = b.lector_id " +
            "where b.department_id = :id and a.degree = :degree", nativeQuery = true)
    int getCountOfLectorsByDepartmentIdAndDegree(Long id, String degree);

    @Query(value = "SELECT AVG(salary), COUNT(*) from lectors as a " +
            "inner join lector_department b on a.id = b.lector_id " +
            "where b.department_id = :id", nativeQuery = true)
    Long getAvarageSalaryByDepartmentId(Long id);

    @Query(value = "select name from departments", nativeQuery = true)
    List<String> getAllNames();
}