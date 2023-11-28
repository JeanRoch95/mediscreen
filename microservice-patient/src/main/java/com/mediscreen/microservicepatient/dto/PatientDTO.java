package com.mediscreen.microservicepatient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class PatientDTO {

    private long id;

    @NotBlank(message = "Vous devez renseigner votre Nom")
    @NotNull
    private String lastName;

    @NotBlank(message = "Vous devez renseigner votre Prénom")
    @NotNull
    private String firstName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Veuillez renseigner votre date de naissance")
    private Date dateOfBirth;

    @NotNull
    private String address;
    private String phoneNumber;

    @NotBlank(message = "Vous devez renseigner votre genre")
    private String gender;

    public PatientDTO(long id, String lastName, String firstName, Date dateOfBirth, String address, String phoneNumber, String gender) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public PatientDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
