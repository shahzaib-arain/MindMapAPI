package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Repository.UserEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserEntryRepository userEntryRepository;
    @Test
    public void testFindByUserName(){
        assertNotNull(userEntryRepository.findByUserName("Bladder"));
    }


//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "10,15,20",
//            "5,6,11"
//    })
//    public void test(int a , int b , int expected){
//        assertequals(expected,a+b);
//    }
}
