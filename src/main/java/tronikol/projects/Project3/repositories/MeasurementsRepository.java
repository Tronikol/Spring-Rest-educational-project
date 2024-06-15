package tronikol.projects.Project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tronikol.projects.Project3.models.Measurements;

public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
    public long countByRaining(Boolean raining);
}
