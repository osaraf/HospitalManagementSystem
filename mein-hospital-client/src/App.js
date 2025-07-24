import React from 'react';

import './App.css';

import Hospitals from './Hospitals'; // Stellen Sie sicher, dass der Pfad korrekt ist

function App() {

  return (

      <div className="App">

        <header className="App-header">

          {/* Sie können hier weiterhin das Logo und andere Kopfzeilenelemente behalten, wenn Sie möchten */}

          <h1>Hospital System</h1>

          <Hospitals />

        </header>

      </div>

  );

}

export default App;
