package consoleApp.service.lector;

import consoleApp.aspects.CC;
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
                try {
                    String[] names = lectorsName.split(" ");

                    Lector tmp = new Lector();
                    if (names.length == 1) {
                        if (names[0].equals("")) names[0] = "EmptyFIO";
                        tmp.setName(names[0]);
                        tmp.setLastName(names[0]);
                    } else {
                        if (names[0].equals("")) names[0] = "EmptyName";
                        if (names[1].equals("")) names[1] = "EmptyLastName";
                        tmp.setName(names[0]);
                        tmp.setLastName(names[1]);
                    }
                    tmp.setFio(tmp.getName() + " " + tmp.getLastName());

                    switch (new Random().nextInt(3)) {
                        case 0:
                            tmp.setDegree(DEGREE.ASSISTANT);
                            tmp.setSalary(BigDecimal.valueOf(new Random().nextInt(10000)));
                            break;
                        case 1:
                            tmp.setDegree(DEGREE.ASSOCIATE_PROFESSOR);
                            tmp.setSalary(BigDecimal.valueOf(new Random().nextInt(10000)));
                            break;
                        default:
                            tmp.setDegree(DEGREE.PROFESSOR);
                            tmp.setSalary(BigDecimal.valueOf(new Random().nextInt(10000)));
                            break;
                    }
                    try {
                        lectorService.save(tmp);
                        System.out.println(String.format(CC.GREEN + "Lector: {inputted = %s} \"%s %s\" saved." + CC.RESET, lectorsName, tmp.getName(), tmp.getLastName()));
                    } catch (Exception e) {
                        System.out.printf(CC.YELLOW + "\t\t\t\t\t\t\t\t\tLector %s exist in DB\n" + CC.RESET, tmp.getFio());
                    }
                } catch (Exception e) {
                    System.out.printf(CC.RED + "\t\t\t\t\t\t\t\t\tSomething wrong with FIO input: \"%s\"\n" + CC.RESET, lectorsName);
                }
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
