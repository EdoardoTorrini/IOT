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
                <ul>
                    {data.map(item => (
                        <li>
                            {item.code}, {item.category}, {item.description}, {item.data}
                        </li>
                    ))}
                </ul>
            ) : (
                <div>Loading...</div>
            )}
            <h2>Hello World!</h2>
        </div>
    )
}

export default App;