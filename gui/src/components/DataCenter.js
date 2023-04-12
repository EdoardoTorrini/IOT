import React from "react";
import Environments from "./status/Environment";
import Conditioning from "./status/Conditioning";
import Alarm from "./status/Alarm";
import Light from "./status/Light";
import PCounter from "./status/PCounter";
import SmartDoor from "./status/SmartDoor";
import Biometric from "./status/Biometric";

class DataCenter extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            datacenter: [],
            isLoad: false,
            timeout: 10000
        };

        this.getStatus = this.getStatus.bind(this);

    }

    async getStatus() {
        
        const response = await fetch("http://127.0.0.1:7070/status", { method: "GET" });
        if (!response.ok) throw new Error(response.status);

        const data = await response.json();
        return data;
    }

    async componentDidMount() {

        const data = await this.getStatus();
        this.setState({ datacenter: data, isLoad: true });

    }

    render() {
        
        return (
            <div>
                {this.state.isLoad ? (
                    <div className="container">
                        <Environments data={this.state.datacenter.environmentalModel}/>
                        <Conditioning data={this.state.datacenter.conditionerModel} />
                        <div className="row border">
                            <div className="col">
                                <b>Alarm</b> <Alarm data={this.state.datacenter.alarmModel}/>
                            </div>
                            <div className="col">
                                <b>Light</b> <Light data={this.state.datacenter.lightModel}/>
                            </div>
                            
                        </div>
                        <PCounter data={this.state.datacenter.pCounterModel}/>
                        <SmartDoor data={this.state.datacenter.smartDoorModel}/>
                        <Biometric data={this.state.datacenter.biometricModel}/>
                    </div>
                    ) : (<p>Loading ... </p>)}
            </div>
        );
    }
}

export default DataCenter;