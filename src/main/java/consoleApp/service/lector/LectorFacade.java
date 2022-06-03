package consoleApp.service.lector;

import consoleApp.domain.DEGREE;
import consoleApp.domain.Lector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorFacade {

    private final LectorService lectorService;

    public LectorFacade(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(LectorService.class);


    public Lector findByFio(String lectorsFIO) {
        return lectorService.findByFio(lectorsFIO).orElse(null);
    }

    public void fillDbLectors(String fiosSeparatedByComa) {
        if (!Objects.equals(fiosSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(fiosSeparatedByComa.split(",")));
            fios.forEach(lectorsName -> {
                String[] names = lectorsName.split(" ");
                Lector tmp = new Lector(names[0], names[1], DEGREE.ASSISTANT, BigDecimal.valueOf(1000));
                lectorService.save(tmp);
                System.out.println(String.format("Lector: %s %s saved.", names[0], names[1]));
            });
        }
    }

    public List<Lector> findAllLectorsByDepartmentId(Long departmentId) {
        return lectorService.findAllByDepartmentId(departmentId);
    }

    public Lector findById(Long lectorId) {
        return lectorService.findById(lectorId).orElse(null);
    }

    public List<String> getAllFios() {
        return lectorService.getAllFios();
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
