import React from "react";
import red from '../../img/red-circle-svgrepo-com.svg'
import green from '../../img/green-circle-svgrepo-com.svg'

class Alarm extends React.Component {

    constructor(props) {

        super(props);
        this.state = { switch_state: props.data };

    }

    render() {
        return (
            <span>
                ON: {
                    this.state.switch_state.on ? (
                        <img src={green}/>
                    ) : (<img src={red}/>)
                }
            </span>
        )
    }

}

export default Alarm;