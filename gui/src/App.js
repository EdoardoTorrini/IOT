import React from "react"
import {useState, useEffect} from "react";
import axios from "axios";

function App() {

    const [data, setData] = useState(null);
    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get("http://localhost:8080/log");
            setData(response.data);
        };

        fetchData();
    }, []);

    return (
        <div id="div_table" className="container text-center">
            {data ? (
                <table className="table table-striped">
                    <tbody>
                        <tr>
                            <th>CATEGORIA</th>
                            <th>DESCRIZIONE</th>
                            <th>DATA</th>
                        </tr>
                        {data.map(item => (
                            <tr className="log" key={item.code}>
                                <td>{item.category}</td>
                                <td>{item.description}</td>
                                <td>{item.data}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <div>Loading...</div>
            )}
        </div>
    )
}

export default App;