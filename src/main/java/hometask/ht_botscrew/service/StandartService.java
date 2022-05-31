package hometask.ht_botscrew.service;

import hometask.ht_botscrew.domain.Lector;

import java.math.BigDecimal;
import java.util.List;

public interface StandartService {

    Lector getHeadOfDepartment(String departmentName);

    void showStat(String departmentName);

    BigDecimal getAverageSalaryByDepartment(String departmentName);

    int getCountOfLectors(String departmentName);

    List<Lector> findAllByTemplate(String template);
}
