//package com.example.spring_boot.config;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
//import com.example.spring_boot.repository.QuestionRepository;
//import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//@EnableDynamoDBRepositories(basePackageClasses = QuestionRepository.class)
//public class DynamoDBConfig {
//
//    @Value("${aws.accesskey}")
//    private String amazonAWSAccessKey;
//
//    @Value("${aws.secretkey}")
//    private String amazonAWSSecretKey;
//
//    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
//        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
//    }
//
//    @Bean
//    public AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
//    }
//
//    @Bean
//    public DynamoDBMapperConfig dynamoDBMapperConfig() {
//        return DynamoDBMapperConfig.DEFAULT;
//    }
//
//    @Bean
//    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
//        return new DynamoDBMapper(amazonDynamoDB, config);
//    }
//
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
//                .withRegion(Regions.US_EAST_1).build();
//    }
//}
//@Configuration
//@EnableDynamoDBRepositories(basePackages = "com.example.spring_boot.repository")
//public class DynamoDBConfig {
//
//    @Value("${aws.dynamodb.endpoint}")
//    private String dynamoHost;
//
//    @Value("${aws.region}")
//    private String dynamoRegion;
//
//    @Value("${aws.accesskey}")
//    private String accessKey;
//
//    @Value("${aws.secretkey}")
//    private String secretKey;
//
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
//        return AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoHost, dynamoRegion))
//                .withCredentials(awsCredentialsProvider)
//                .build();
//    }
//    @Bean
//    public AWSCredentialsProvider amazonAWSCredentialProvider() {
//        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
//    }
//
//    @Bean
//    public AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(accessKey, secretKey);
//    }
//    @Bean
//    public DynamoDBMapper dynamoDBMapper() {
//        return new DynamoDBMapper(amazonDynamoDB(), DynamoDBMapperConfig.DEFAULT);
//    }
//}

//
//    @Bean
//    public DynamoDbClient getDynamoDbClient() {
//        var builder = DynamoDbClient
//                .builder()
//                .credentialsProvider(DefaultCredentialsProvider.create());
//
//        if (dynamoHost != null && !dynamoHost.isBlank()) {
//            builder.region(Region.of(dynamoRegion))
//                    .endpointOverride(URI.create(dynamoHost));
////            log.info("DynamoDB Client initialized in region " + dynamoRegion);
////            log.warn("DynamoDB Client ENDPOINT overridden to " + dynamoHost);
//        }
//        return builder.build();
//    }
//
//    @Bean
//    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient ddbc) {
//        return DynamoDbEnhancedClient
//                .builder()
//                .extensions(VersionedRecordExtension.builder().build())
//                .dynamoDbClient(ddbc)
//                .build();
//    }
//}
