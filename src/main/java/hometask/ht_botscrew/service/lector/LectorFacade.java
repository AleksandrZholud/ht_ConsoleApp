package hometask.ht_botscrew.service.lector;

import hometask.ht_botscrew.domain.DEGREE;
import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.service.department.DepartmentFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LectorFacade {

    private final LectorService lectorService;
    private final DepartmentFacade departmentFacade;

    public LectorFacade(DepartmentFacade departmentFacade, LectorService lectorService) {
        this.departmentFacade = departmentFacade;
        this.lectorService = lectorService;
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(LectorService.class);

    public Optional<Lector> findByFIO(String lectorsFIO) {
        return lectorService.findByFio(lectorsFIO);
    }

    public Lector addDepartmentForLector(Lector lector, Department department) {
        return lectorService.findByFio(lector.getFio())
                .map(v -> lectorService.addDepartmentForLector(lector, department)).orElse(null);
    }

    public void fillDbLectors(String fiosSeparatedByComa) {
        if (!Objects.equals(fiosSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(fiosSeparatedByComa.split(",")));
            fios.forEach(lectorsName -> {
                String[] names = lectorsName.split(" ");
                Lector tmp = new Lector(names[0], names[1], DEGREE.ASSISTANT, BigDecimal.valueOf(1000));
                lectorService.save(tmp);
                LOG.info(String.format("Lector: %s %s saved.", names[0], names[1]));
            });
        }
    }

    public List<Lector> findAllLectorsByDepartmentId(Long departmentId) {
        return lectorService.findAllByDepartmentId(departmentId);
    }

/*  public List<Lector> findAllLectorsByDepartmentId(Long departmentId) {
        List<Long> lectorsIds = lectorService.findAllByDepartmentId(departmentId);
        List<Lector> lectors = new ArrayList<>();
        lectorsIds.forEach(id->{
            lectors.add(lectorService.findById(id).orElse(null));
        });
        return lectors.stream().filter(Objects::isNull).collect(Collectors.toList());
    }*/

}
