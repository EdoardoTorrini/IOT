import React from "react";

class Biometric extends React.Component {

    constructor(props) {

        super(props);
        this.state = { biometric: props.data };

    }

    render() {
        return (
            <div className="row border">

                <div className="row"><b>Biometric Sensor</b></div>
                <div className="row">
                    <div className="col">
                        Token:&nbsp;
                        {this.state.biometric.token}
                    </div>
                </div>

            </div>
        )
    }

}

export default Biometric;