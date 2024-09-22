import {React, useEffect, useState} from "react"
import "../css/Box.css"

const Box =({id, year, number, description, link, checked, onMarkDone}) => {
    const [isChecked, setIsChecked] = useState(false);

    useEffect(() => {
        setIsChecked(checked);
    }, [checked]);
    useEffect(() => {
        const doneQuestions = JSON.parse(localStorage.getItem('doneQuestions') || '[]');
        setIsChecked(doneQuestions.includes(id));
    }, [id]);
    const handleCheckboxChange=(e)=>{
        if(e.target.checked){
            markDone(id);
        } else{
            unmarkDone(id);
        }
        setIsChecked(e.target.checked);
        onMarkDone();
    }
    const markDone = (questionId) =>{
        let doneQuestions = JSON.parse(localStorage.getItem('doneQuestions') || '[]');
        if (!doneQuestions.includes(questionId)){
            doneQuestions.push(questionId);
            console.log("QUESTION DONE! : ", questionId)
            localStorage.setItem('doneQuestions', JSON.stringify(doneQuestions));
        }
    }
    const unmarkDone = (questionId) =>{
        let doneQuestions = JSON.parse(localStorage.getItem('doneQuestions') || '[]');
        doneQuestions = doneQuestions.filter((id) => id !== questionId);
        localStorage.setItem('doneQuestions', JSON.stringify(doneQuestions));
    }
    

    return (
        <div className="box">
            <div onClick={()=> window.open(link, "_blank")}>
            <h2>{year}</h2>
            <h3>Q{number}</h3>
            <p>{description}</p>
            </div>
            <div>
                <span className="checkbox-text">Done? </span>
                <input className = "checkbox" type="checkbox" checked = {isChecked} onChange={handleCheckboxChange}/>
            </div>
        </div>
    )
}

export default Box;