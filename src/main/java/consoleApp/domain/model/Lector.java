package consoleApp.domain.model;

import consoleApp.domain.enums.DEGREE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "lectors")
@AllArgsConstructor
@NoArgsConstructor
public class Lector implements IdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "full_name", unique = true)
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    private DEGREE degree;

    private BigDecimal salary;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(name = "lector_department",
//            joinColumns = @JoinColumn(name = "lector_id"),
//            inverseJoinColumns = @JoinColumn(name = "department_id"))
//    private Set<Department> departments = new HashSet<>();

    public Lector(String name, String lastName, DEGREE degree, BigDecimal salary) {
        this.name = name;
        this.lastName = lastName;
        this.degree = degree;
        this.salary = salary;
        fullName = name + " " + lastName;
    }
}