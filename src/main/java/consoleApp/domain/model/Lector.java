package consoleApp.domain.model;

import consoleApp.domain.enums.DEGREE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty(message = "Field NAME cannot be Empty")
    @NotNull(message = "Field NAME cannot be NULL")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Field LASTNAME cannot be Empty")
    @NotNull(message = "Field LASTNAME cannot be NULL")
    @Column(name = "lastName")
    private String lastName;

    @NotEmpty(message = "Field FULLNAME cannot be Empty")
    @NotNull(message = "Field FULLNAME cannot be NULL")
    @Column(name = "full_name", unique = true)
    private String fullName;

    @NotEmpty(message = "Field DEGREE cannot be Empty")
    @NotNull(message = "Field DEGREE cannot be NULL")
    @Enumerated(value = EnumType.STRING)
    private DEGREE degree;

    //    @DecimalMin("1")
    @NotEmpty(message = "Field SALARY cannot be Empty")
    @NotNull(message = "Field SALARY cannot be NULL")
    private BigDecimal salary;
    
    public Lector(String name, String lastName, DEGREE degree, BigDecimal salary) {
        this.name = name;
        this.lastName = lastName;
        this.degree = degree;
        this.salary = salary;
        fullName = name + " " + lastName;
    }
}
