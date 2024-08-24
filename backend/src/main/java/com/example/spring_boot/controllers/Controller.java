package com.example.spring_boot.controllers;

import com.example.spring_boot.models.Question;
import com.example.spring_boot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comps")
public class Controller {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public String hello(){
        return "Hello";
    }

    @GetMapping("/save-all")
    public ResponseEntity<HttpStatus> postData() throws IOException, InterruptedException {
        questionService.iterateQuestions();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all-questions")
    public Iterable<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/question-by-topic/{topic}")
    public Iterable<Question> getQuestionsByTopic(@PathVariable String topic) {
        return questionService.getQuestionByTopic(topic);
    }
}
