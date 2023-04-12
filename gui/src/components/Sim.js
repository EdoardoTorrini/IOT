import React from "react";
import BiometricTokenSend from "./BiometricToken";

class Sim extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            flooding: false,
            fire: false,
            acceleration: false,
        };
        this.onInputChange = this.onInputChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    

    onInputChange(event) {
        this.setState(prevState => ({
            [event.target.name]: event.target.checked
        }));
    }

    onSubmit() {
        // TODO: add the alert
        if ((!this.state.flooding) && (!this.state.fire) && (!this.state.acceleration))
            console.log("select at least one of the possible simulations")
        
        if (
            (this.state.fire && this.acceleration) ||
            (this.state.flooding && this.acceleration) ||
            (this.state.flooding && this.state.fire)
        )
            console.log("select only one of the simulation")

        console.log(this.state)
    }

    onChange() {
        this.setState({
            flooding: false,
            fire: false,
            acceleration: false,
        });
    }

    render() {
        return(
            <div className="container">
    
                <div className="row">
                    
                    <div className="col">

                        <div className="form-check">
                            <input 
                                className="form-check-input" 
                                type="checkbox"
                                name="flooding"
                                checked={this.state.flooding}
                                onChange={this.onInputChange}
                                id="flooding_sim"
                            />
                            <label className="form-check-label" htmlFor="flooding_sim">
                                Flooding Simulation
                            </label>
                        </div>

                        <div className="form-check">
                            <input 
                                className="form-check-input" 
                                type="checkbox" 
                                name="fire"
                                checked={this.state.fire}
                                onChange={this.onInputChange}
                                id="fire_sim"
                            />
                            <label className="form-check-label" htmlFor="fire_sim">
                                Fire Simulation
                            </label>
                        </div>

                        <div className="form-check">
                            <input 
                                className="form-check-input" 
                                type="checkbox" 
                                name="acceleration"
                                checked={this.state.acceleration}
                                onChange={this.onInputChange}
                                id="acceleration_sim"
                            />
                            <label className="form-check-label" htmlFor="acceleration_sim">
                                Forcing Acceleration Door
                            </label>
                        </div>

                    </div>
                    
                    <div className="col"><BiometricTokenSend/></div>
    
                </div>
    
                <div>
                    <center>
                        <div className="btn-group">
                            <div className="row">
                                <div className="col p-2">
                                     <button onClick={this.onSubmit} type="submit" className="btn btn-primary">Simulate</button>
                                 </div>
                                <div className="col p-2">
                                    <button onClick={this.onChange} type="submit" className="btn btn-primary">Reset</button>
                                </div>
                            </div>
                        </div>
                    </center>
                </div>
    
            </div>
        )
    }
}

export default Sim;