package tronikol.projects.Project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tronikol.projects.Project3.models.Measurements;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.services.SensorService;

@Component
public class MeasurementsValidator implements Validator {
    private final SensorService sensorService;
    @Autowired
    public MeasurementsValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurements.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurements measurements = (Measurements) target;
        Sensor sensor = sensorService.findByName(measurements.getSensor().getName());
        if(sensor==null) {
            errors.rejectValue("sensor","", "Sensor with this name not exist");
        }
    }
}
