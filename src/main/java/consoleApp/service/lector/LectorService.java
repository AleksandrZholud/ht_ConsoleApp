package consoleApp.service.lector;

import consoleApp.domain.model.Lector;
import consoleApp.repository.LectorsRepository;
import consoleApp.service.AbstractMainService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LectorService extends AbstractMainService<Lector, Long, LectorsRepository> {

    public Optional<Lector> findByFullName(String fio){
        return repository.findByFullName(fio);
    }

    public Optional<Lector> findById(Long id){
        return repository.findById(id);
    }

    public List<Lector> findAllByDepartmentId(Long departmentId) {
        return repository.findAllByDepartmentId(departmentId);
    }

    public List<String> getAllFullNames() {
        return repository.getAllFullNames();
    }
}
