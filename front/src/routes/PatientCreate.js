import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PatientService from '../services/PatientService';
import 'bootstrap/dist/css/bootstrap.min.css';

const PatientCreate = () => {
    const navigate = useNavigate();
    const [patient, setPatient] = useState({
        lastName: '',
        firstName: '',
        dateOfBirth: '',
        address: '',
        phoneNumber: '',
        gender: ''
    });
    const [errors, setErrors] = useState({});


    const handleSubmit = (event) => {
        event.preventDefault();
        PatientService.createPatient(patient)
            .then(response => {
                navigate('/patient'); 
            })
            .catch(error => {
                console.log("Réponse d'erreur du serveur:", error.response.data);
                if (error.response && error.response.data) {
                    setErrors(error.response.data);
                    console.log(errors);
                } else {
                    console.log("Erreur lors de la création du patient:", error);
                }
            });
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        console.log(name, value); 
        setPatient({ ...patient, [name]: value });
    };

    return (
        <div className="container mt-4">
    <div className="row justify-content-center">
        <div className="col-lg-6">
            <h1 className="text-center mb-4">Création d'un Patient</h1>
            <form onSubmit={handleSubmit} className="form-group">
                <label>Nom de famille</label>
                <input
                    type="text"
                    name="lastName"
                    onChange={handleInputChange}
                    className={`form-control mb-3 ${errors.lastName ? 'is-invalid' : ''}`}
                />
                {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}

                <label>Prénom</label>
                <input
                    type="text"
                    name="firstName"
                    onChange={handleInputChange}
                    className={`form-control mb-3 ${errors.firstName ? 'is-invalid' : ''}`}
                />
                {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}

                <label>Date de Naissance</label>
                <input
                    type="date"
                    name="dateOfBirth"
                    onChange={handleInputChange}
                    className={`form-control mb-3 ${errors.dateOfBirth ? 'is-invalid' : ''}`}
                />
                {errors.dateOfBirth && <div className="invalid-feedback">{errors.dateOfBirth}</div>}

                <label>Adresse</label>
                <input
                    type="text"
                    name="address"
                    onChange={handleInputChange}
                    className={`form-control mb-3 ${errors.address ? 'is-invalid' : ''}`}
                />
                {errors.address && <div className="invalid-feedback">{errors.address}</div>}

                <label>Numéro de Téléphone</label>
                <input
                    type="text"
                    name="phoneNumber"
                    onChange={handleInputChange}
                    className={`form-control mb-3 ${errors.phoneNumber ? 'is-invalid' : ''}`}
                />
                {errors.phoneNumber && <div className="invalid-feedback">{errors.phoneNumber}</div>}

                <label>Genre</label>
                <select
                    name="gender"
                    onChange={handleInputChange}
                    className={`form-control mb-4 ${errors.gender ? 'is-invalid' : ''}`}
                >
                    <option value="">Sélectionnez le genre</option>
                    <option value="H">Homme</option>
                    <option value="F">Femme</option>
                </select>
                {errors.gender && <div className="invalid-feedback">{errors.gender}</div>}

                <button type="submit" className="btn btn-primary btn-block">Créer</button>
            </form>
        </div>
    </div>
</div>
);
    };

export default PatientCreate;