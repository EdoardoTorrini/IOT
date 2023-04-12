import logo from './logo.svg';
import React from 'react';

import LogList from './components/LogList'
import Sim from './components/Sim'
import DataCenter from './components/DataCenter';


function App() {

  return (
    
    <div className='container gap-3 p-4'>
      <div className='container'>
        <h1 className="display-4 fw-normal">Intrusion Detection System & Disaster Recovery</h1>
      </div>
      <div className="container gap-3 p-4">
        <div className="row">
          <div className="col border p-2"><DataCenter/></div>
          <div className="col border p-2"><Sim/></div>
        </div>
        <div className="row border p-2">
          <div className='overflow-auto' style={{height: '375px'}}><LogList/></div>
        </div>
      </div>

    </div>

  );

}

export default App;
