import React from "react";
import yellow from '../../img/circle-yellow-filled.png'
import gray from '../../img/circle-gray-filled.png'

class Light extends React.Component {

    constructor(props) {

        super(props);
        this.state = { switch_state: props.data };

    }

    render() {
        return (
            <span>
                ON: {
                    this.state.switch_state.on ? (
                        <img src={yellow}/>
                    ) : (<img src={gray}/>)
                }
            </span>
        )
    }

}

export default Light;