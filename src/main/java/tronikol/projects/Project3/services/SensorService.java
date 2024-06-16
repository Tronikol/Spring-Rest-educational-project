package tronikol.projects.Project3.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.repositories.SensorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name);
    }
}
