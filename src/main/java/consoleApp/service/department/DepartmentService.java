package consoleApp.service.department;

import consoleApp.domain.DEGREE;
import consoleApp.domain.Department;
import consoleApp.repository.DepartmentRepository;
import consoleApp.service.AbstractBotsCrewService;
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

    public int getCountOfLectorsByDepartmentIdAndDegree(Long id, DEGREE degree){
        return repository.getCountOfLectorsByDepartmentIdAndDegree(id, degree.toString());
    }

    public long getAvarageSalaryByDepartmentId(Long id) {
        return repository.getAvarageSalaryByDepartmentId(id);
    }

    public List<String> getAllNames() {
        return repository.getAllNames();
    }
}
