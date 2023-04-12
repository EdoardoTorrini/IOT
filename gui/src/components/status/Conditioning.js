import React from 'react';
import red from '../../img/red-circle-svgrepo-com.svg'
import green from '../../img/green-circle-svgrepo-com.svg'

class Conditioning extends React.Component {

    constructor(props) {

        super(props);
        this.state = { conditioning: props.data };

    }

    render() {
        return (
            <div className="row border">

                <div className="row"><b>Conditioning Status</b></div>
                <div className="row">
                    <div className="col">
                        Dehumidifier: {
                            this.state.conditioning.dehumidifier ? (
                                <img src={green}/>
                            ) : (<img src={red}/>)
                        }
                    </div>
                    <div className='col'>
                        Level Air Conditioning:&nbsp;
                        {this.state.conditioning.airConditioningLevel}
                    </div>
                </div>

            </div>
        )
    }

}

export default Conditioning;