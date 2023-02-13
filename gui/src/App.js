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
        <div>
            {data ? (
                <table>
                    {data.map(item => (
                        <tr id={item.code}>
                            <th>{item.category}</th>
                            <th>{item.description}</th>
                            <th>{item.data}</th>
                        </tr>
                    ))}
                </table>
            ) : (
                <div>Loading...</div>
            )}
            <h2>Hello World!</h2>
        </div>
    )
}

export default App;