import React from "react";
import red from '../../img/red-circle-svgrepo-com.svg'
import green from '../../img/green-circle-svgrepo-com.svg'

class SmartDoor extends React.Component {

    constructor(props) {

        super(props);
        this.state = { smart_door: props.data };

    }

    render() {
        return (
            <div className="row border">

                <div className="row"><b>Smart Door</b></div>
                <div className="row">
                    <div className="col">
                        Acceleration:&nbsp;
                        {this.state.smart_door.acceleration.toFixed(2)}
                    </div>
                    <div className="col">
                        LOCK: {
                            this.state.smart_door.lock ? (
                                <img src={green}/>
                            ) : (<img src={red}/>)
                        }
                    </div>
                    <div className="col">
                        OPEN: {
                            this.state.smart_door.open ? (
                                <img src={green}/>
                            ) : (<img src={red}/>)
                        }
                    </div>
                </div>

            </div>
        )
    }

}

export default SmartDoor;