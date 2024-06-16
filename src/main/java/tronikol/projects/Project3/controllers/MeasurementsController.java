package tronikol.projects.Project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tronikol.projects.Project3.dto.MeasurementsDTO;
import tronikol.projects.Project3.dto.RainyDaysCountDTO;
import tronikol.projects.Project3.models.Measurements;
import tronikol.projects.Project3.services.MeasurementsService;
import tronikol.projects.Project3.util.ErrorsUtil;
import tronikol.projects.Project3.util.MeasurementNotAddException;
import tronikol.projects.Project3.util.MeasurementsValidator;
import tronikol.projects.Project3.util.SensorErrorResponse;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementsValidator measurementsValidator) {
        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
    }


    @GetMapping("/rainyDaysCount")
    public RainyDaysCountDTO rainyDaysCount() {
        return new RainyDaysCountDTO(measurementsService.rainyDaysCount());
    }

    @GetMapping
    public List<MeasurementsDTO> index() {
        List<MeasurementsDTO> measurementsDTOS = new LinkedList<>();
        List<Measurements> measurements = measurementsService.findAll();
        for (Measurements measurement : measurements) {
            measurementsDTOS.add(convertToMeasurementsDTO(measurement));
        }
        return measurementsDTOS;
    }

    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurement) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurement, MeasurementsDTO.class);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult result) {
        Measurements measurements = convertToMeasurements(measurementsDTO);
        measurementsValidator.validate(measurements, result);
        if (result.hasErrors()) {
            throw new MeasurementNotAddException(ErrorsUtil.mapErrors(result));
        }
        measurementsService.add(convertToMeasurements(measurementsDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurementsDTO, Measurements.class);
    }


    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(MeasurementNotAddException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
