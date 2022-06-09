package consoleApp.domain.model;

import consoleApp.domain.enums.DEGREE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "lectors")
@AllArgsConstructor
@NoArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull(message = "Field NAME cannot be NULL")
    @Column(name = "name")
    private String name;

    @NotBlank
    @NotNull(message = "Field LASTNAME cannot be NULL")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank
    @NotNull(message = "Field FULLNAME cannot be NULL")
    @Column(name = "full_name", unique = true)
    private String fullName;


    @NotNull(message = "Field DEGREE cannot be NULL")
    @Enumerated(value = EnumType.STRING)
    private DEGREE degree;

    @DecimalMin(value = "300", message = "The minimum salary cannot be smaller than 300!")

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
