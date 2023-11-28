import axios from "axios";

const URL_API_PATIENT = "http://localhost:9000/api/";

class PatientService {
    
    getAllPatients() {
        return axios.get(URL_API_PATIENT + "patients");
    }

    getPatientById(patientId) {
        return axios.get(URL_API_PATIENT + "patient/" + patientId);
    }

    getPatientByLastNameAndFirstName(lastName, firstName) {
        return axios.get(URL_API_PATIENT + "patient/by-name", { params: { lastname: lastName, firstname: firstName } });
    }
    

    createPatient(patient) {
        return axios.post(URL_API_PATIENT + "patient", patient);
    }

    updatePatient(patient, patientId) {
        return axios.put(URL_API_PATIENT + "update/" + patientId, patient);
    }

    deletePatient(patientId) {
        return axios.delete(URL_API_PATIENT + 'patient/' + patientId);
    }
}

export default new PatientService();