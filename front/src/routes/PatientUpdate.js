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
  const [errors, setErrors] = useState({});


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
                            className={`form-control mb-3 ${errors.lastName ? 'is-invalid' : ''}`}
                            />
                            {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}

                        <label>Prénom</label>
                        <input
                            type="text"
                            name="firstName"
                            value={patient.firstName}
                            onChange={handleInputChange}
                            className={`form-control mb-3 ${errors.firstName ? 'is-invalid' : ''}`}
                            />
                            {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}

                        <label>Date de Naissance</label>
                        <input
                            type="date"
                            name="dateOfBirth"
                            value={patient.dateOfBirth}
                            onChange={handleInputChange}
                            className={`form-control mb-3 ${errors.dateOfBirth ? 'is-invalid' : ''}`}
                            />
                            {errors.dateOfBirth && <div className="invalid-feedback">{errors.dateOfBirth}</div>}

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
                            className={`form-control mb-4 ${errors.gender ? 'is-invalid' : ''}`}
                            >
                            <option value="">Sélectionnez le genre</option>
                            <option value="Male">Homme</option>
                            <option value="Female">Femme</option>
                        </select>
                        {errors.gender && <div className="invalid-feedback">{errors.gender}</div>}

                        <button type="submit" className="btn btn-primary btn-block">Mettre à jour</button>
                    </form>
                </div>
            </div>
        </div>
    );
};
export default PatientUpdate;