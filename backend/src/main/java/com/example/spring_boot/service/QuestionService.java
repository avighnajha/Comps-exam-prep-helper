package com.example.spring_boot.service;

import com.example.spring_boot.models.Question;
import com.example.spring_boot.repository.QuestionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class QuestionService {

    private final ChatClient chatClient;
//    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final int MAX_RETRIES = 5; // Maximum number of retries
    private static final int WAIT_TIME_MS = 10000; //Wait 10s
    private static final String FOLDER_ID = "1K3t2Tm83aevVoLFCAPfPbAJVJdPUNidS";
    private static final String API_KEY = new Properties().getProperty("google.api.key");
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    public QuestionService(QuestionRepository questionRepository,
                           ChatClient.Builder builder) {
        this.questionRepository = questionRepository;
        this.chatClient = builder.build();
    }

    public Iterable<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Iterable<Question> getQuestionByTopic(String topic) {
        return questionRepository.getByTopic(topic);
    }

    public void iterateQuestions() throws IOException, InterruptedException {
        String basePath = "C:\\Users\\avigh\\Desktop\\Comps_downloads\\Sorted";
        File dir = new File(basePath);

        for (File file : dir.listFiles()) {
            if (file != null) {
                for (File pdf : file.listFiles()) {
                    System.out.println("Currently saving: " + pdf.getName());
                    String topic = file.getName();
                    String pdfName = pdf.getName();
                    saveQuestion(pdf.getPath(), topic, pdfName.substring(0, 4), pdfName.substring(5, pdfName.length()-4));
                }
            } else {throw new NoSuchFileException("No file found" + dir.getAbsolutePath());
            }
        }
    }

    private void saveQuestion(String path, String topic, String year, String num) throws IOException, InterruptedException {

        String qText = getQText(path);
        String desc = getDescription(qText);
        Question question = new Question();
        question.setQuestion_num(num);
        question.setTopic(topic);
        question.setYear(year);
        question.setDescription(desc);

        questionRepository.save(question);
    }

    private String getQText(String path) throws IOException{
        PDDocument document = PDDocument.load(new File(path));
        PDFTextStripper stripper = new PDFTextStripper();
        return stripper.getText(document);
    };

    public String getDescription(String question) throws InterruptedException{
        String message ="I will give you physics exam question give me 2 sentence description of the question." +
                " DO NOT SAY ANYTHING OTHER THAN THE DESCIRPTION. DO NOT SAY ANYTHING LIKE 'Here is the descirption...'";

        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            try {
                // Make the API call
                String response = chatClient.prompt().system(message).user(question).call().content();

                // If successful, return the response
                return response;

            } catch (NonTransientAiException e) {
                System.out.println(e.getMessage().substring(0, 3));
                if (e.getMessage().substring(0,3).equals("429")) {
                    // If rate limited, wait for the specified time and retry
                    retryCount++;
                    System.out.println("Rate limited, retrying in 10 seconds... (" + retryCount + "/" + MAX_RETRIES + ")");
                    Thread.sleep(WAIT_TIME_MS); // Wait before retrying
                } else {
                    // Handle other HTTP errors (if necessary)
                    throw e;
                }
            } catch (Exception e) {
                // Handle other exceptions (e.g., network issues)
                throw new RuntimeException("An error occurred during the API call", e);
            }
        }
    throw new RuntimeException("Max retries exceeded, API call failed.");

    };

    //Hl
    public void setLinks() throws IOException {
       // HashMap<String, HashMap<String, String>> links = new HashMap<>();
        File resource = new ClassPathResource("json/links.json").getFile();
        HashMap<String, HashMap<String, String>> links = jacksonObjectMapper.readValue(resource,
                new TypeReference<HashMap<String, HashMap<String, String>>>() {});


        for (String key : links.keySet()) {
            List<Question> questions = questionRepository.getByTopic(key);
            System.out.println("TOPIC: " + key);
            for (Question question : questions) {
                System.out.println(question.getYear() + question.getQuestion_num());
                String num = question.getQuestion_num();
                String year = question.getYear();
                question.setQuestion_link(links.get(key).get(year + "_" + num + ".pdf"));
                questionRepository.save(question);
            }

        }
    }

}
