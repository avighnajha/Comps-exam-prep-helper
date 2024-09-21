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
    const doneQuestions = JSON.parse(localStorage.getItem('doneQuestions') || '[]');
    const filteredQuestions = questions.filter((question)=> !doneQuestions.includes(question.id));

    return <div className="grid-container">
        {filteredQuestions.map((question)=>(
            <Box id = {question.id} year={question.year} number={question.question_num} description={question.description} link={question.question_link}/>
        ))}
    </div>;

}
export default Question