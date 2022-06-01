package hometask.ht_botscrew.repository;

import hometask.ht_botscrew.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    @Query(value = "select count (*) from lector_department where department_id = " +
            "(select id from department where name = :departmentName)", nativeQuery = true)
    int getCountOfLectorsByDepartmentName(String departmentName);
}