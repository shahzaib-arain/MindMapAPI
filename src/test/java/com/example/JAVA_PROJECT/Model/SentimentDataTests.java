package com.example.JAVA_PROJECT.Model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SentimentDataTests {

    @Autowired
    private SentimentData sentimentData;

    @Test
    @Disabled
    public void checkData(){
        System.out.println(sentimentData.getSentiment());
        System.out.println(sentimentData.getEmail());
    }

}
