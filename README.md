#Imperial College Physics - Comprehensive Exam Question Navigator#
Welcome to the Comprehensive Exam Question Navigator project! This tool is designed for Imperial College London 3rd year physics students preparing for the Comprehensives Exam. The Comprehensives is known for testing core problem-solving skills and drawing on material covered over the first three years of study. With over 300 past exam questions spread across a wide range of topics, this tool aims to simplify the exam preparation process, making it easier for students to navigate, track, and practice specific topics.

##Overview##
The Comprehensive Exam Question Navigator extracts individual questions from past exam papers and uses a Large Language Model (LLM) to automatically categorize each question by topic. The result is an interactive web platform that allows students to:

Easily browse through questions categorized by topic.
Search for specific problem types.
Track completed questions.
Stay organized during their exam preparation.
The questions are extracted from a Google Drive repository, passed through a LLama 3b model via the Groq API to generate question descriptions and determine the topic, and then saved into a PostgreSQL database. The platform's front end is built with React, and the back end runs on Java Spring Boot.

##Tech Stack##
###Backend###
Java Spring Boot: Manages API requests and handles the logic for retrieving questions and user data.
PostgreSQL: Stores questions, topics, and student progress.
Groq API (LLama 3b Model): Used to automatically generate question descriptions and topics.
###Frontend###
React: Provides a responsive and interactive UI for students to browse and track questions.
