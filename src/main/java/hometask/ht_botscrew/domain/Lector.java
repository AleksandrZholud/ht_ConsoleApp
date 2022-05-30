package hometask.ht_botscrew.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "lectors")
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lectors_name")
    private String name;

    @Column(name = "lectors_lastName")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private DEGREE degree;

    private BigDecimal salary;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Department> departments = new HashSet<>();
}
