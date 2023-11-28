import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import PatientService from '../services/PatientService';
import 'bootstrap/dist/css/bootstrap.min.css';


const PatientUpdate = () => {
    const { id } = useParams();
    const navigate = useNavigate();
  const [patient, setPatient] = useState({
    lastName: '',
    firstName: '',
    dateOfBirth: '',
    address: '',
    phoneNumber: '',
    gender: ''
  });

  useEffect(() => {
    PatientService.getPatientById(id)
      .then(response => {
        setPatient(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, [id]);

  const handleSubmit = (event) => {
    event.preventDefault();
    PatientService.updatePatient(patient, id)
      .then(response => {
          navigate('/patient')
      })
      .catch(error => {
        console.log("Erreur lors de la mise à jour du patient:", error);
      });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setPatient({ ...patient, [name]: value });
  };

  // Formulaire de mise à jour
  return (
    <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-lg-6">
                    <h1 className="text-center mb-4">Mise à jour du Patient {patient.firstName} {patient.lastName}</h1>
                    <form onSubmit={handleSubmit} className="form-group">
                        <label>Nom de famille</label>
                        <input
                            type="text"
                            name="lastName"
                            value={patient.lastName}
                            onChange={handleInputChange}
                            className="form-control mb-3"
                        />
                        <label>Prénom</label>
                        <input
                            type="text"
                            name="firstName"
                            value={patient.firstName}
                            onChange={handleInputChange}
                            className="form-control mb-3"
                        />
                        <label>Date de Naissance</label>
                        <input
                            type="date"
                            name="dateOfBirth"
                            value={patient.dateOfBirth}
                            onChange={handleInputChange}
                            className="form-control mb-3"
                        />
                        <label>Adresse</label>
                        <input
                            type="text"
                            name="address"
                            value={patient.address}
                            onChange={handleInputChange}
                            className="form-control mb-3"
                        />
                        <label>Numéro de Téléphone</label>
                        <input
                            type="text"
                            name="phoneNumber"
                            value={patient.phoneNumber}
                            onChange={handleInputChange}
                            className="form-control mb-3"
                        />
                        <label>Genre</label>
                        <select
                            name="gender"
                            value={patient.gender}
                            onChange={handleInputChange}
                            className="form-control mb-4"
                        >
                            <option value="">Sélectionnez le genre</option>
                            <option value="Male">Homme</option>
                            <option value="Female">Femme</option>
                            <option value="Other">Autre</option>
                        </select>
                        <button type="submit" className="btn btn-primary btn-block">Mettre à jour</button>
                    </form>
                </div>
            </div>
        </div>
    );
};
export default PatientUpdate;