
package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Api.Response.WeatherResponse;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Repository.UserEntryRepository;
import com.example.JAVA_PROJECT.Service.UserService;
import com.example.JAVA_PROJECT.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> GetAllUserEntries() {
        List<UserEntity> userEntities = userService.GetAllEntries();
        if (userEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userEntities);
        }
        return ResponseEntity.ok(userEntities);
    }


    @PutMapping
    public ResponseEntity<?> updateEntry(@RequestBody UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userInDb = userService.findByUserName(userName);

        // Update the user details
        userInDb.setUserName(userEntity.getUserName());
        userInDb.setPassword(userEntity.getPassword());
        userInDb.setEmail(userEntity.getEmail());
        userInDb.setSentimentAnalysis(userEntity.getSentimentAnalysis());
        userService.SaveNewUser(userInDb);

        // Return a success response
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "User updated successfully",
                        "updatedUser", userInDb
                ));
    }

    @DeleteMapping
    public ResponseEntity<?> updateEntry() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @GetMapping("/greetings")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Karachi");
        String greeting="";
        if (weatherResponse != null){
            greeting= "  Weather feels like " + weatherResponse.getCurrent().getFeelslike();
            return new ResponseEntity<>("Hi " + authentication.getName() + greeting,HttpStatus.OK);
        }
        return new ResponseEntity<>("Hi "+authentication.getName(),HttpStatus.OK);



    }


}


