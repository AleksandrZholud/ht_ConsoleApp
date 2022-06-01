package hometask.ht_botscrew.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
