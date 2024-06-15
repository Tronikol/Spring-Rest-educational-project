package tronikol.projects.Project3.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tronikol.projects.Project3.models.Measurements;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.repositories.MeasurementsRepository;
import tronikol.projects.Project3.repositories.SensorRepository;
import tronikol.projects.Project3.util.SensorNotExistException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorRepository sensorRepository;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorRepository sensorRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorRepository = sensorRepository;
    }
    public Long rainyDaysCount() {
        return measurementsRepository.countByRaining(true);
    }
    @Transactional
    public void add(Measurements measurements) {
        Sensor sensor = sensorRepository.findByName(measurements.getSensor().getName());
        if(sensor==null) {
            throw new SensorNotExistException("Sensor with this name not exist");
        }
        // TODO может можно переделать, проблема в том что сенсор который как бы есть был в несохраненном состояни
        measurements.setSensor(sensor);
        measurementsRepository.save(measurements);
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }
}
