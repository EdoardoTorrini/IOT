import logo from './logo.svg';
import React from 'react';

import LogList from './components/LogList'
import Sim from './components/Sim'


function App() {

  return (
    
    <div className='container gap-3 p-4'>
      <div className='container'>
        <h1 className="display-4 fw-normal">Intrusion Detection System & Disaster Recovery</h1>
      </div>
      <div className="container gap-3 p-4">
        <div className="row">
          <div className="col border p-4">1 di 2</div>
          <div className="col border p-4" style={{height: '250px'}}><Sim/></div>
        </div>
        <div className="row border p-4">
          <div className='overflow-auto' style={{height: '400px'}}><LogList/></div>
        </div>
      </div>

    </div>


  );

}

export default App;
