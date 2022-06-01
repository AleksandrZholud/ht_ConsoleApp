package hometask.ht_botscrew.service.department;

import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.repository.DepartmentRepository;
import hometask.ht_botscrew.service.AbstractBotsCrewService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DepartmentService extends AbstractBotsCrewService<Department, Long, DepartmentRepository> {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public BigDecimal getAverageSalaryByDepartment(String departmentName) {
        return null;
    }

    public int getCountOfLectors(String departmentName) {
        return repository.getCountOfLectorsByDepartmentName(departmentName);
    }

    public Optional<Department> findByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }
}
