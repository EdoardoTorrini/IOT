import React from "react";

class BiometricTokenSend extends React.Component {

    constructor(props) {
        super(props);

        this.state = {token: ""};
        this.onInputChange = this.onInputChange.bind(this);
        this.onSubmitForm = this.onSubmitForm.bind(this);
    }

    onInputChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    onSubmitForm() {
        // TODO: send to SIM server
        console.log(this.state);
        this.setState({
            token: ""
        })
    }

    render() {
        const { items } = this.state;

        return (
            <div className="gap-3">
                <div className="form-group">
                    <label htmlFor="token_biometric">Biometric Token</label>
                    <input 
                        type="text"
                        name="token"
                        value={this.state.token}
                        onChange={this.onInputChange}
                        className="form-control" 
                        id="token_biometric" 
                        aria-describedby="emailHelp" 
                        placeholder="edoardo_torrini"
                    />
                </div>
                <div className="p-1">
                    <button onClick={this.onSubmitForm} type="submit" className="btn btn-primary">Submit</button>
                </div>
            </div>
        )
    }

}

export default BiometricTokenSend;