package hometask.ht_botscrew.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "lectors")
@AllArgsConstructor
@NoArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "FIO", unique = true)
    private String fio;

    @Enumerated(value = EnumType.STRING)
    private DEGREE degree;

    private BigDecimal salary;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "lector_department",
            joinColumns = @JoinColumn(name = "lector_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> departments = new HashSet<>();

    public void addDepartment(Department department){
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.removeIf(cat -> cat.getId().equals(department.getId()));
    }

    public Lector(String name, String lastName, DEGREE degree, BigDecimal salary) {
        this.name = name;
        this.lastName = lastName;
        this.degree = degree;
        this.salary = salary;
        fio = name + " " + lastName;
    }
}
