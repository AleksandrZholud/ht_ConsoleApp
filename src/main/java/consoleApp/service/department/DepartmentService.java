package consoleApp.service.department;

import consoleApp.domain.DEGREE;
import consoleApp.domain.Department;
import consoleApp.repository.DepartmentRepository;
import consoleApp.service.AbstractMainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DepartmentService extends AbstractMainService<Department, Long, DepartmentRepository> {

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

    public int getCountOfLectorsByDepartmentIdAndDegree(Long id, DEGREE degree){
        return repository.getCountOfLectorsByDepartmentIdAndDegree(id, degree.toString());
    }

    public BigDecimal getAverageSalaryByDepartmentId(Long id) {
        return repository.getAverageSalaryByDepartmentId(id);
    }

    public List<String> getAllNames() {
        return repository.getAllNames();
    }
}
