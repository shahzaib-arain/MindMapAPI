package com.example.JAVA_PROJECT.Service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;
@Disabled
@Test
    public void SendEmailsToUser(){
    emailService.SendEmail("zaibyaraib@gmail.com","Parhlo Bhai Jaan","Springboot se shuru karo aur Aaag laga do Aaag,,,,,");
}
}
