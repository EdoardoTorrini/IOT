import React from "react";

class Environments extends React.Component {

    constructor(props) {

        super(props);
        this.state = { environment: props.data };
        
    }

    render() {
        
        return (
            <div className="row border">
                
                <div className="row"><b>Environment Status</b></div>
                <div className="row">
                    <div className="col">
                        Temp:&nbsp;
                        {this.state.environment.temperature.toFixed(2)}
                    </div>
                    <div className="col">
                        Humidity:&nbsp;
                        {this.state.environment.humidity.toFixed(2)}
                    </div>
                    <div className="col">
                        UV Index:&nbsp;
                        {this.state.environment.uvIndex.toFixed(2)}
                    </div>
                    <div className="col">
                        Smoke Lvl:&nbsp;
                        {this.state.environment.smokeLevel.toFixed(2)}
                    </div>
                </div>
                
            </div>
        )
    }

}

export default Environments;