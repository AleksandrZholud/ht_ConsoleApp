package consoleApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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

    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "lector_department",
            joinColumns = @JoinColumn(name = "department_id"))
    @Column(name = "lector_id")
    //@UniqueElements(message = "Lector is exist")
    //@NotEmpty(message = "You can't create Department without any Lector")
    private Set<Long> lectors = new HashSet<>();

    public void addLector(Long lectorId){
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

    public Department(String name, Lector headLector) {
        this.name = name;
        this.headLector = headLector;
    }
}
