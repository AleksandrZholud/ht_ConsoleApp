package consoleApp.service.lector;

import consoleApp.domain.Lector;
import consoleApp.repository.LectorsRepository;
import consoleApp.service.AbstractMainService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LectorService extends AbstractMainService<Lector, Long, LectorsRepository> {

    public Optional<Lector> findByFio(String fio){
        return repository.findByFio(fio);
    }

    public Optional<Lector> findById(Long id){
        return repository.findById(id);
    }

    public List<Lector> findAllByDepartmentId(Long departmentId) {
        return repository.findAllByDepartmentId(departmentId);
    }

    public List<String> getAllFios() {
        return repository.getAllFios();
    }
}
