package com.example.JAVA_PROJECT.Repository;

import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Disabled
    @Test
    public void testSaveNewUser(){
    Assertions.assertNotNull(userRepository.getUserForSa());
    }
}
