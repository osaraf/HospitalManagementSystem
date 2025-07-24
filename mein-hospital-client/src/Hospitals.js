import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Hospitals.css'; // Ensure the path is correct

function Hospitals() {
    const [hospitals, setHospitals] = useState([]);
    const [timestamp, setTimestamp] = useState('');
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        fetchHospitals();
    }, []);


    const fetchHospitals = () => {
        //setIsLoading(true);
        axios.get('http://localhost:5000/getAllHospitals')
            .then(response => {
                setHospitals(response.data);
                //setHospitalResponse(response.data);
                console.log('Response data:', response.data); // Add this line to log the response
                return axios.get('http://localhost:5000/getTimestamp'); // Replace with your timestamp endpoint
            })
            .then(timestampResponse => {
                setTimestamp(timestampResponse.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Es gab einen Fehler!', error);
                setIsLoading(false);
            });
    };

    // Calculate totals
    const totalAvailableBeds = hospitals.reduce((sum, hospital) => sum + hospital.availableBeds, 0);
    const totalBeds = hospitals.reduce((sum, hospital) => sum + hospital.totalBeds, 0);

    return (
        <div className="hospitals-container">

            <h1>Hospitals</h1>
            <div>Last Updated: {timestamp}</div>
            <button onClick={fetchHospitals} className="refresh-button">Refresh</button>
            {isLoading ? (
                <p>Lädt Krankenhäuser...</p>
            ) : (
                <table className="hospitals-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Verfügbare Betten</th>
                        <th>Gesamtbetten</th>
                        {/* Weitere Spalten nach Bedarf */}
                    </tr>
                    </thead>
                    <tbody>
                    {hospitals.map(hospital => (
                        <tr key={hospital.id}>
                            <td>{hospital.id}</td>
                            <td>{hospital.name}</td>
                            <td>{hospital.availableBeds}</td>
                            <td>{hospital.totalBeds}</td>
                            {/* Weitere Zellen nach Bedarf */}
                        </tr>
                    ))}
                    {/* Totals row */}
                    <tr>
                        <td colSpan="2">Total</td>
                        <td>{totalAvailableBeds}</td>
                        <td>{totalBeds}</td>
                    </tr>
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default Hospitals;