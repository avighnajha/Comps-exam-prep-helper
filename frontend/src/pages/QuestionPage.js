import { useState, useEffect, useRef } from 'react';
import '../css/QuestionPage.css';
import Question from "../components/Question";

function QuestionPage() {
  const [headerVisible, setHeaderVisible] = useState(()=>{
    const headerVisible = localStorage.getItem('headerVisible');
    return headerVisible ? JSON.parse(headerVisible) : true;
  });

  const [topic, setTopic] = useState("");
  const selectRef = useRef(null);
  const spanRef = useRef(null);

  const handleCloseHeader = () => {
    localStorage.setItem('headerVisible', JSON.stringify(false));
    setHeaderVisible(false);}
  const handleChangeTopic = (event) => {
    setTopic(event.target.value)
  }
  useEffect(() => {
    const selectElement = selectRef.current;
    const spanElement = spanRef.current;
    if (selectElement && spanElement){
      spanElement.textContent = selectElement.options[selectElement.selectedIndex].text;
      const textWidth = spanElement.offsetWidth;
      const basePadding = 10; // Base padding in pixels
      const dynamicPadding = textWidth * 1.4; // Adjust this factor as needed
      selectElement.style.width = `${textWidth + basePadding + dynamicPadding}px`;
    }
    
  }, [topic]);
  
  return (
    <div className='question-page'>
      {headerVisible && (
        <div className="header">
          <h3>Done questions are at the bottom!</h3>
          <button onClick={handleCloseHeader} className="close-button">X</button>
        </div>
      )}
      <h1>I want to do a 
        <select ref={selectRef} value={topic} onChange = {handleChangeTopic} className = "underline">
          <option value = "Physics">Physics</option>
          <option value = "Quantum Mechanics">Quantum Mechanics</option>
          <option value = "Waves and Optics">Waves and Optics</option>
          <option value = "Mechanics">Mechanics</option>
          <option value = "Thermal Physics (Ideal Gas and Pressure)">Thermal Physics (Ideal Gas and Pressure)</option>
          <option value = "Thermal Physics (Thermodynamics)">Thermal Physics (Thermodynamics)</option>
          <option value = "Statistics">Statistics</option>
          <option value = "Electromagnetism">Electromagnetism</option>
          <option value = "Atomic Physics">Atomic Physics</option>
          <option value = "Relativity">Relativity</option>
          <option value = "Gravitation">Gravitation</option>
        </select>
        question!
       </h1>
        <span ref={spanRef} style={{ visibility: 'hidden', whiteSpace: 'nowrap', position: 'absolute' }}></span>
        <Question topic = {topic} isDone={false}/>
        <div className='doneQ'>
          <h2>{topic===""? "Please select a topic" : "Done Questions" }</h2>
          <Question topic = {topic} isDone={true}/>
        </div>
    </div>

  );
}

export default QuestionPage;