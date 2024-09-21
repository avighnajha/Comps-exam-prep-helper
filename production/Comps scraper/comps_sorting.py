import requests
import fitz
import os
import api_keys
from openai import OpenAI
from groq import Groq
from tempfile import gettempdir

openAIapi_key = api_keys.openAI_key
client = Groq(api_key=api_keys.groq_key)
def question_continues(text):
    #phrase = "[This question continues on the"
    phrase = "[Total 20 marks]"
    phrase2 = "Go to the next "

    len_phrase = len(phrase)

    for i in range(len(text)-1-len_phrase, -1, -1):
        if text[i:i+len_phrase] == phrase or text[i:i+len(phrase2)] == phrase2:
            return False
        if len(text)-i>500:
            return True

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
        if page_num == 0:# or page_num == 1:
            continue
        page = doc[page_num]
        page_text += page.get_text()
        page_numbers.append(page_num)
        print(page_numbers, page_text[0:10])
        if question_continues(page_text):
            question = page_text[0] if page_text[1] == "." else page_text[0:2]
            continues = True
            continue
        if not continues:
            question = page_text[0] if page_text[1] == "." else page_text[0:2]

        questions[question] = [page_numbers, page_text]
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
        "content": "You are a physics professor and are helping me sort questions by topic. I will provide you with a question and you will provide me with the topic of the question out of the following topics: Mechanics, Electromagnetism, Waves and Optics, Thermal Physics (Thermodynamics), Thermal Physics (Ideal Gas an pressure), Quantum Mechanics, Relativity, Atomic Physics, Statistics, Gravitation and Other. Please only give me the topic and do not answer the question, I repeat your response should only be 1 topic out of the provided options please. I REPEAT ONLY SAY THE NAME OF THE TOPIC DO NOT SAY ANYTHING ELSE OR YOU LOSE YOUR JOB."
        #or 2 topics out of the provided options, if you think the question has 2 main topics begin your answer with the number 2 as the first character and the topics seperated by commas. If single topic only reply with the name of the topic and nothing else."
        },
        {
        "role": "user",
        "content": question
        }
    ]
    )
    ans = completion.choices[0].message.content
    print("reponse:", ans)
    if ans and ans[0] == "2":
        print("Splitting", ans[1:].split(","))
        return ans[1:].split(",")
    return [ans]
def sort_questions(questions):
    sorted_questions = {}
    
    for q in questions:
        if q==0:
            continue
        q_text = questions[q][1]
        topic = get_topic(q_text)
        sorted_questions[q] = topic
    return sorted_questions

'''

'''
def save_topics(questions, questions_topics, pdf_path, save_path, year):
    for q in questions:
        if q=="Im":
            continue
        print(q, questions_topics[q])
        q_topics = questions_topics[q]
        for topic in q_topics:
            pdf = fitz.open(pdf_path)
            new_pdf = fitz.open()
            new_save_path = os.path.join(save_path, topic)
            if not os.path.exists(new_save_path):
                os.makedirs(new_save_path)
            new_pdf.insert_pdf(pdf, from_page=questions[q][0][0], to_page=questions[q][0][-1], rotate=0)
            new_pdf_filename = os.path.join(new_save_path,f"{year}_{q}.pdf" )
            new_pdf.save(new_pdf_filename)
            new_pdf.close()
            pdf.close()


        

def split_files_sort_questions_save():
    gen_folder = r"C:\Users\avigh\Desktop\Comps_downloads"
    folder = os.path.join(gen_folder, r"COMPS")
    print(folder)
    for year in os.listdir(folder):
        #year = year[:4]
        if int(year[:4])<2022:
            continue
        new_path = os.path.join(folder, year)
        for pdf in os.listdir(new_path):
            print(year,":", pdf)
            pdf_path = os.path.join(new_path, pdf)
            questions = extract_text(pdf_path)
            sorted_questions = sort_questions(questions)
            save_path = os.path.join(gen_folder, "Sorted")
            save_topics(questions, sorted_questions, pdf_path, save_path, year)


# questions = extract_text(r"C:\Users\avigh\Desktop\Comps_downloads\COMPS\2016\Comprehensive I.pdf")
# sorted_questions = sort_questions(questions)
# pdf_path = r"C:\Users\avigh\Desktop\Comps_downloads\COMPS\2016\Comprehensive I.pdf"
# save_path = r"C:\Users\avigh\Desktop\Comps_downloads"
# save_topics(questions, sorted_questions, pdf_path,save_path, 2016)

split_files_sort_questions_save()

# path = r"C:\Users\avigh\Desktop\Comps_downloads\COMPS\2009\Physics I - Comprehensive Paper.pdf"
# extract_text(path)
