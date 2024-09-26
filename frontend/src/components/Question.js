import React, {useEffect, useState} from "react"
import Box from "./Box"
import "../css/Question.css"

const Question = ({topic, isDone, introPage, doneQuestions, handleMarkDone}) => {
    const [questions, setQuestions] = useState([]);
    // const [doneQuestions, setDoneQuestions] = useState(JSON.parse(localStorage.getItem('doneQuestions') || '[]'));
    
    useEffect(() => {
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

    // const handleMarkDone = () =>{
    //     setDoneQuestions(JSON.parse(localStorage.getItem('doneQuestions') || '[]'));
    // }
    // const updateQuestions = () => {
    //     console.log("Rerendering")
    //     setQuestions([...questions]); // Trigger a re-render by updating the state
    // };
    // const doneQuestions = JSON.parse(localStorage.getItem('doneQuestions') || '[]');
    const filteredQuestions = questions.filter((question)=> !doneQuestions.includes(question.id));
    const filteredDone = questions.filter((question)=> doneQuestions.includes(question.id));
    console.log(filteredDone, filteredQuestions)
    return introPage ? (
        <div className="disclaimer">
            <h1>Welcome to the Comps prep page.</h1>
            <h2>Choose a topic from the dropdown menu to get started!</h2>
            <h2>Total questions done: {doneQuestions.length}/206</h2>
            <p>After selecting a topic above, click on question boxes to be redirected to the question pdf. You can mark questions as done and find them at the bottom of the topic pages.</p>
        </div>
    ) : !isDone ? (
    <div className="notDone">
    {topic !== "" && (<h3>Questions done: {filteredDone.length}/{questions.length}</h3>)}
    <div className="grid-container">
        {filteredQuestions.map((question)=>(
            <Box 
             key = {question.id}
             id = {question.id}
             year={question.year}
             number={question.question_num}
             description={question.description}
             link={question.question_link}
             checked={doneQuestions.includes(question.id)}
             onMarkDone={handleMarkDone} />
             //updateQuestions={updateQuestions}/>
        ))}
    </div>
    </div>
    )
    :
    (<div className="grid-container">
        {filteredDone.map((question)=>(
            <Box 
             key = {question.id}
             id = {question.id}
             year={question.year}
             number={question.question_num}
             description={question.description}
             link={question.question_link}
             checked={doneQuestions.includes(question.id)}
             onMarkDone={handleMarkDone}/>
             //updateQuestions={updateQuestions}/>
        ))}
    </div>)
}
export default Question