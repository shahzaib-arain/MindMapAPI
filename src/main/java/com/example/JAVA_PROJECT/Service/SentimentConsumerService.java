package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "weekly-sentiments",groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }
    private void sendEmail(SentimentData sentimentData){
        emailService.SendEmail(sentimentData.getEmail(),"Sentiment for previous week ", sentimentData.getSentiment());
    }
}
