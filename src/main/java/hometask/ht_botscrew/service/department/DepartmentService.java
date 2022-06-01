package hometask.ht_botscrew.service.department;

import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
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

   /* public List<Long> getLectorsIdsByDepartmentId(Long id){
        repository.findAllLectorsIdByDepartmentId(id);
    }*/

    public Department setHeadOfDepartment(Lector lector, String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> {
                    department.setHeadLector(lector);
                    System.out.printf("\ndepartment{%s}.setHeadLector = OK\n\n", departmentName);
                    return departmentRepository.save(department);
                }).orElse(null);
    }

    public Lector getHeadOfDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElse(null);
        return department.getHeadLector();
    }



    public BigDecimal getAverageSalaryByDepartment(String departmentName) {
        return null;
    }

    public int getCountOfLectors(String departmentName) {
        return repository.getCountOfLectorsByDepartmentName(departmentName);
    }

    public void fillDbDepartments(String namesSeparatedByComa) {
        if (!Objects.equals(namesSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(namesSeparatedByComa.split(",")));
            fios.forEach(departmentName -> {
                departmentRepository.save(new Department(departmentName));
                System.out.println(String.format("Department: %s saved.", departmentName));
            });
        }
    }

    public Department findByName(String departmentName) {
        return departmentRepository.findByName(departmentName).orElse(null);
    }
}
