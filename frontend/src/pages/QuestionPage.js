import { useState, useEffect, useRef } from 'react';
import '../css/QuestionPage.css';

function QuestionPage() {
  const [topic, setTopic] = useState("");
  const selectRef = useRef(null);
  const spanRef = useRef(null);

  const handleChangeTopic = (event) => {
    setTopic(event.target.value)
  }
  useEffect(() => {
    const selectElement = selectRef.current;
    const spanElement = spanRef.current;
    if (selectElement && spanElement){
      spanElement.textContent = selectElement.options[selectElement.selectedIndex].text;
      const textWidth = spanElement.offsetWidth;
      const basePadding = 20; // Base padding in pixels
      const dynamicPadding = textWidth * 1.7; // Adjust this factor as needed
      selectElement.style.width = `${textWidth + basePadding + dynamicPadding}px`;
    }

    fetchTopicQuestions(topic)
    
  }, [topic]);
  
  return (
    <div className='question-page'>
      <h1>I want to do a 
        <select ref={selectRef} value={topic} onChange = {handleChangeTopic} class = "underline">
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
    </div>
  );
}

export default QuestionPage;