import requests
import fitz
import os
import api_keys
from openai import OpenAI
from groq import Groq

openAIapi_key = api_keys.openAI_key
client = Groq(api_key=api_keys.groq_key)
def question_continues(text):
    phrase = "[This question continues on the"
    len_phrase = len(phrase)

    for i in range(len(text)-1-len_phrase, -1, -1):
        if text[i:i+len_phrase] == phrase:
            return True
        if len(text)-i>100:
            return False

'''
Takes in a pdf and retunrs a dictionary of questions and their corresponding page numbers and the text for that question.
'''
def extract_text(pdf_path):
    doc = fitz.Document(pdf_path)
    page_text = ""
    questions = {}
    continues = False
    page_numbers = []

    for page_num in range(len(doc)):
        page = doc[page_num]
        page_text += page.get_text()
        page_numbers.append(page_num)
        #print(page_numbers, page_text[0:2])
        if question_continues(page_text):
            question = page_text[0] if page_text[1] == "." else page_text[0:2]
            continues = True
            continue
        if not continues:
            question = page_text[0] if page_text[1] == "." else page_text[0:2]

        questions[question] =[page_numbers,page_text]
        page_text = ""
        page_numbers = []
        continues = False

    return questions

def get_topic(question):    
    completion = client.chat.completions.create(
    model = "llama3-8b-8192",
    messages=[
        {
        "role": "system",
        "content": "You are a physics professor and are helping me sort questions by topic. I will provide you with a question and you will provide me with the topic of the question out of the following topics: Mechanics, Electromagnetism, Waves and Optics, Thermal Physics (Thermodynamics), Thermal Physics (Ideal Gas an pressure), Quantum Mechanics, Relativity, Atomic Physics, Statistics, Gravitation and Other. Please only giveme the topic and do not answer the question."
        },
        {
        "role": "user",
        "content": question
        }
    ]
    )
    return completion.choices[0].message.content
#print(extract_text(r"C:\Users\avigh\Desktop\Comps_downloads\COMPS\2016\Comprehensive I.pdf"))

