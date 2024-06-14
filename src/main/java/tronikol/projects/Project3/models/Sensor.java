package tronikol.projects.Project3.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="sensor")
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "sensor")
    private List<Measurements> measurementsList;


}
