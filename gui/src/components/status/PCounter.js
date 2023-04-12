import React from "react";

class PCounter extends React.Component {

    constructor(props) {

        super(props);
        this.state = { pcounter: props.data };

    }

    render() {
        return (
            <div className="row border">

                <div className="row"><b>People Counter</b></div>
                <div className="col">
                    Number of people:&nbsp;
                    {this.state.pcounter.peopleIn}
                </div>

            </div>
        )
    }

}

export default PCounter;