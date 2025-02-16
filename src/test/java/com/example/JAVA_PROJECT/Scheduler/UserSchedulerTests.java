package com.example.JAVA_PROJECT.Scheduler;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTests {

    @Autowired
    private UserScheduler userScheduler;


    @Disabled
    @Test
    public void testFetchUserAndSendSaMail() {
        userScheduler.fetchUsersAndSendEmail();
    }

}
