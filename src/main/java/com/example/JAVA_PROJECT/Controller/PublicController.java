package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("abc")
    public String HealthChecked(){
        return "AGAIN START JAVA SHAME ON YOU ";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
        try {
            userService.SaveNewUser(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

}
