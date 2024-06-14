package tronikol.projects.Project3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "measurements")
public class Measurements {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="value")
    private String value;
    @Column(name = "raining")
    private Boolean raining;
    @ManyToOne
    @JoinColumn(name="sensor_id")
    private Sensor sensor;


}
