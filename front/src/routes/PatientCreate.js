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

    const handleSubmit = (event) => {
        event.preventDefault();
        PatientService.createPatient(patient)
            .then(response => {
                navigate('/patient'); // Redirection après la création réussie
            })
            .catch(error => {
                console.log("Erreur lors de la création du patient:", error);
            });
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        console.log(name, value); // Ajoutez cette ligne pour vérifier le format de la date
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
                    className="form-control mb-3"
                />
                <label>Prénom</label>
                <input
                    type="text"
                    name="firstName"
                    onChange={handleInputChange}
                    className="form-control mb-3"
                />
                <label>Date de Naissance</label>
                <input
                    type="date"
                    name="dateOfBirth"
                    onChange={handleInputChange}
                    className="form-control mb-3"
                />
                <label>Adresse</label>
                <input
                    type="text"
                    name="address"
                    onChange={handleInputChange}
                    className="form-control mb-3"
                />
                <label>Numéro de Téléphone</label>
                <input
                    type="text"
                    name="phoneNumber"
                    onChange={handleInputChange}
                    className="form-control mb-3"
                />
                <label>Genre</label>
                <select
                    name="gender"
                    onChange={handleInputChange}
                    className="form-control mb-4"
                >
                    <option value="">Sélectionnez le genre</option>
                    <option value="Male">Homme</option>
                    <option value="Female">Femme</option>
                    <option value="Other">Autre</option>
                </select>
                <button type="submit" className="btn btn-primary btn-block">Créer</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default PatientCreate;