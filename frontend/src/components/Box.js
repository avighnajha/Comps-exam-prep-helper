import React from "react"
import "../css/Box.css"

const Box =({year, number, description, onClick}) => {
    return (
        <div className="box" onClick={ onClick}>
            <h2>{year}</h2>
            <h3>Q{number}</h3>
            <p>{description}</p>
        </div>
    )
}

export default Box;