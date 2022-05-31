package hometask.ht_botscrew.service;

import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.repository.DepartmentRepository;
import hometask.ht_botscrew.repository.LectorsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SimpleServiceImpl implements StandartService {

    private final LectorsRepository lectorsRepository;
    private final DepartmentRepository departmentRepository;

    public SimpleServiceImpl(LectorsRepository lectorsRepository, DepartmentRepository departmentRepository) {
        this.lectorsRepository = lectorsRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Lector getHeadOfDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElse(null);
        return department.getHeadLector();
    }

    @Override
    public void showStat(String departmentName) {

    }

    @Override
    public BigDecimal getAverageSalaryByDepartment(String departmentName) {
        return null;
    }

    @Override
    public int getCountOfLectors(String departmentName) {
        return 0;
    }

    @Override
    public List<Lector> findAllByTemplate(String template) {
        return null;
    }
}
