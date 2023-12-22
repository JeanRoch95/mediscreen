package com.mediscreen.microservicepatient.mapper;

import com.mediscreen.microservicepatient.dto.PatientDTO;
import com.mediscreen.microservicepatient.model.Patient;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO ToDTO(Patient patient);


}
