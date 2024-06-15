package tronikol.projects.Project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import tronikol.projects.Project3.dto.SensorDTO;
import tronikol.projects.Project3.models.Sensor;
import tronikol.projects.Project3.services.SensorService;
import tronikol.projects.Project3.util.SensorAlreadyExistException;
import tronikol.projects.Project3.util.SensorErrorResponse;
import tronikol.projects.Project3.util.SensorNotCreatedException;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registrarion")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder allErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                allErrors.append(error.getField()).append(" : ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorNotCreatedException(allErrors.toString());
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

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorAlreadyExistException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
