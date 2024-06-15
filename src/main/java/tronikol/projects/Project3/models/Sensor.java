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

    public Sensor(String name, List<Measurements> measurementsList) {
        this.name = name;
        this.measurementsList = measurementsList;
    }

    public Sensor() {
    }

    public List<Measurements> getMeasurementsList() {
        return measurementsList;
    }

    public void setMeasurementsList(List<Measurements> measurementsList) {
        this.measurementsList = measurementsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "sensor")
    private List<Measurements> measurementsList;


}
