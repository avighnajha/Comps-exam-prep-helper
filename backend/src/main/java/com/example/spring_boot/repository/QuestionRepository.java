package com.example.spring_boot.repository;

import com.example.spring_boot.models.Question;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository

@EnableScan
@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {

    @Query(
            value = "SELECT * " +
                    "FROM QUESTION " +
                    "WHERE QUESTION.topic = ?1 ",
            nativeQuery = true
    )
    List<Question> getByTopic(String topic);
}
