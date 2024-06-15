package tronikol.projects.Project3.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tronikol.projects.Project3.dto.SensorDTO;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.repositories.SensorRepository;
import tronikol.projects.Project3.util.SensorAlreadyExistException;

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
        if(sensorRepository.findByName(sensor.getName())!=null) {
            throw new SensorAlreadyExistException("Sensor with this name already exist");
        }
        sensorRepository.save(sensor);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
}
