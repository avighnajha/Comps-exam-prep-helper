package com.example.spring_boot.models;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Getter
@Setter
@Entity
@Table()
public class Question {

    @Id
    @SequenceGenerator(
            name = "Question_sequence",
            sequenceName = "Question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Question_sequence"
    )
    private long id;

    @Column
    private String topic;

    @Column
    private String year;

    @Column
    private String question_num;

    @Column(length = 2000)
    private String description;

    @Column
    private String question_link;
}
//@Getter
//@Setter
//@DynamoDBTable(tableName="CompsQuestions")
//public class Question {
//    private String topic;
//    private String composite_key;
//    private String description;
//
//    public Question(){}
//
//    public Question(String topic, String composite_key, String description) {
//        this.topic = topic;
//        this.composite_key = composite_key;
//        this.description = description;
//    }
//
//    @DynamoDBHashKey(attributeName = "QuestionTopic")
//    public String getTopic(){
//        return topic;
//    }
//
//    @DynamoDBRangeKey(attributeName = "CompositeKey")
//    public String getComposite_key(){
//        return composite_key;
//    }
//
//    @DynamoDBAttribute(attributeName = "Description")
//    public String getDescription(){
//        return description;
//    }
//}
