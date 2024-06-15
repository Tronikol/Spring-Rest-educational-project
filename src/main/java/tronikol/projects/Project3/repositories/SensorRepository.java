package tronikol.projects.Project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tronikol.projects.Project3.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    public Sensor findByName(String name);
}
