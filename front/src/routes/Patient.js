import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'
import PatientService from '../services/PatientService';
import 'bootstrap/dist/css/bootstrap.min.css';

const Patient = () => {

    const [patients, setPatients] = useState([]);
    const [searchLastName, setSearchLastName] = useState('');
    const [searchFirstName, setSearchFirstName] = useState('');

    useEffect(() => {
        PatientService.getAllPatients()
            .then(response => {
                setPatients(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('fr-FR'); 
    };
    

    const handleDelete = (patientId) => {
        PatientService.deletePatient(patientId)
            .then(response => {
                setPatients(patients.filter(patient => patient.id !== patientId));
            })
            .catch(error => {
                console.log("Erreur lors de la suppression du patient:", error);
            });
    };

    const handleSearch = (event) => {
        event.preventDefault();
        PatientService.getPatientByLastNameAndFirstName(searchLastName, searchFirstName)
            .then(response => {
                setPatients([response.data]);
            })
            .catch(error => {
                console.log("Erreur lors de la recherche du patient:", error);
                if (error.response && error.response.status === 404) {
                    alert("Aucun patient trouvé"); // Affiche un message d'erreur
                }
            });
    };

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-5">Liste des Patients</h1>
            <div className="d-flex justify-content-between align-items-center">
            <Link to={`/patient/create`} className="btn btn-success mb-4">Nouveau patient</Link>
            <form onSubmit={handleSearch} className="mb-4">
    <div className="form-row">
        <div className="col">
            <input 
                type="text" 
                id="lastNameSearch" 
                className="form-control" 
                placeholder="Nom" 
                value={searchLastName} 
                onChange={(e) => setSearchLastName(e.target.value)} 
                style={{ width: '150px' }} 
            />
        </div>
        <div className="col">
            <input 
                type="text" 
                id="firstNameSearch" 
                className="form-control" 
                placeholder="Prénom" 
                value={searchFirstName} 
                onChange={(e) => setSearchFirstName(e.target.value)} 
                style={{ width: '150px' }} 
            />
        </div>
        <div className="col d-flex align-items-end">
            <button 
                type="submit" 
                className="btn btn-primary"
                disabled={!searchLastName || !searchFirstName} // Le bouton est désactivé si l'un des champs est vide
            >
                Rechercher
            </button>
        </div>
    </div>
</form>

            </div>
            <table className="table table-light table-hover">
                <thead className="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Prénom</th>
                        <th>Nom</th>
                        <th>Date de Naissance</th>
                        <th>Adresse</th>
                        <th>Numéro de Téléphone</th>
                        <th>Genre</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {patients.map(patient => (
                        <tr key={patient.id}>
                            <td>{patient.id}</td>
                            <td>{patient.firstName}</td>
                            <td>{patient.lastName}</td>
                            <td>{formatDate(patient.dateOfBirth)}</td>
                            <td>{patient.address}</td>
                            <td>{patient.phoneNumber}</td>
                            <td>{patient.gender}</td>
                            <td>
                                <button className="btn btn-danger pr-2" onClick={() => handleDelete(patient.id)}>Supprimer</button>
                                <Link to={`/patient/${patient.id}`} className="btn btn-warning">Modifier</Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Patient;