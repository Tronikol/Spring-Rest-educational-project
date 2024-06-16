package tronikol.projects.Project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tronikol.projects.Project3.dto.SensorDTO;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.services.SensorService;
import tronikol.projects.Project3.util.SensorErrorResponse;
import tronikol.projects.Project3.util.SensorNotCreatedException;
import tronikol.projects.Project3.util.SensorValidator;

import java.util.LinkedList;
import java.util.List;

import static tronikol.projects.Project3.util.ErrorsUtil.mapErrors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new SensorNotCreatedException(mapErrors(bindingResult));
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<SensorDTO> all() {
        List<Sensor> sensors = sensorService.findAll();
        List<SensorDTO> sensorDTOS = new LinkedList<>();
        for (Sensor sensor : sensors) {
            sensorDTOS.add(convertToSensorDTO(sensor));
        }
        return sensorDTOS;
    }


    private SensorDTO convertToSensorDTO(Sensor sensor) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensor, SensorDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
