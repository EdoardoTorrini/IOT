import React from "react";

class LogList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            logs: [],
        };
        this.getLog = this.getLog.bind(this);
    }

    async getLog() {

        const response = await fetch("http://127.0.0.1:8081/log", { method: "GET" });
        if (!response.ok) throw new Error(response.status);

        const data = await response.json();
        return data;
    }

    async componentDidMount()  {
        const logs = await this.getLog();
        this.setState({ logs })
    }

    render() {
        return (

        <table className="table table-hover table-bordered">
            <thead className="table-dark">
            <tr>
                <th scope="col">Code</th>
                <th scope="col">Data</th>
                <th scope="col">Category</th>
                <th scope="col">Description</th>
            </tr>
            </thead>
            <tbody>
                {this.state.logs.reverse().map((log) => (
                <tr key={log.code}>
                    <th scope="row">{log.code}</th>
                    <td>{log.data}</td>
                    <td>{log.category}</td>
                    <td>{log.description}</td>
                </tr>
                ))}
            </tbody>
        </table>
        )
    }

}

export default LogList;