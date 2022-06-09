package consoleApp.service.department;

import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Department;
import consoleApp.repository.DepartmentRepository;
import consoleApp.service.AbstractMainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DepartmentService extends AbstractMainService<Department, DepartmentRepository> {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
