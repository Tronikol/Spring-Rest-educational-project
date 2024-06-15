package tronikol.projects.Project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tronikol.projects.Project3.dto.MeasurementsDTO;
import tronikol.projects.Project3.models.Measurements;
import tronikol.projects.Project3.services.MeasurementsService;
import tronikol.projects.Project3.util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;

    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }


    @GetMapping
    public List<MeasurementsDTO> index() {
        List<MeasurementsDTO> measurementsDTOS = new LinkedList<>();
        List<Measurements> measurements = measurementsService.findAll();
        for(Measurements measurement : measurements) {
            measurementsDTOS.add(convertToMeasurementsDTO(measurement));
        }
        return measurementsDTOS;
    }

    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurement) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurement, MeasurementsDTO.class);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult result){
        if (result.hasErrors()) {
            StringBuilder allErrors = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                allErrors.append(error.getField()).append(" : ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotAddException(allErrors.toString());
        }
        measurementsService.add(convertToMeasurements(measurementsDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurementsDTO, Measurements.class);
    }
    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotExistException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(MeasurementNotAddException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
