package hometask.ht_botscrew.service.lector;

import hometask.ht_botscrew.domain.DEGREE;
import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.repository.LectorsRepository;
import hometask.ht_botscrew.service.AbstractBotsCrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorService extends AbstractBotsCrewService<Lector, Long, LectorsRepository> {

    public Optional<Lector> findByFio(String fio){
        return repository.findByFio(fio);
    }

    public Lector addDepartmentForLector(Lector lector, Department department) {
        lector.addDepartment(department);
        return repository.save(lector);
    }

    public List<Lector> findAllByDepartmentId(Long departmentId) {
        return repository.findAllByDepartmentId(departmentId);
    }
}
