import React, {useEffect, useState} from "react"
import Box from "./Box"
import "../css/Question.css"

const Question = ({topic}) => {
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        // Ensure the URL is correct and encoded
        let baseUrl = 'http://localhost:8080/comps';
        fetch(baseUrl + `/question-by-topic?topic=${encodeURIComponent(topic)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setQuestions(data); // Store the data in state
                console.log("Questions:", data); // Debugging
            })
            .catch(error => console.error('Fetch error:', error));
    }, [topic]);

    return <div className="grid-container">
        {/* <p>{questions}</p> */}
        {questions.map((question)=>(
            <Box year={question.year} number={question.question_num} description={question.description}/>
        ))}
    </div>;

}
export default Question