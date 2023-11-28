import { Route, Routes } from "react-router-dom"
import Patient from "./routes/Patient";
import PatientCreate from "./routes/PatientCreate";
import PatientUpdate from "./routes/PatientUpdate";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/patient" element={<Patient />} />
        <Route path="/patient/:id" element={<PatientUpdate />} />
        <Route path="/patient/create" element={<PatientCreate />} />
      </Routes>
    </div>
  );
}

export default App;
