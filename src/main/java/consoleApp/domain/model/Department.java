package consoleApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 40)
    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "lector_department",
            joinColumns = @JoinColumn(name = "department_id"))
    @Column(name = "lector_id")
    private Set<Long> lectors = new HashSet<>();

    public void addLector(Long lectorId) {
        lectors.add(lectorId);
    }

    public void removeLector(Long lectorId) {
        lectors.removeIf(existId -> existId.equals(lectorId));
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "headLector_id", referencedColumnName = "id")
    private Lector headLector;

    public Department(String name) {
        this.name = name;
    }
}
