
package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.JournalService;
import com.example.JAVA_PROJECT.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> GetAllUserEntries() {
        List<UserEntity> userEntities = userService.GetAllEntries();
        if (userEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userEntities);
        }
        return ResponseEntity.ok(userEntities);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
        try {
            userService.SaveEntry(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateEntry(@PathVariable String userName, @RequestBody UserEntity userEntity) {
        UserEntity userInDb = userService.findByUserName(userName);
        if (userInDb == null) {
            // Return a response indicating the user was not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found", "userName", userName));
        }

        // Update the user details
        userInDb.setUserName(userEntity.getUserName());
        userInDb.setPassword(userEntity.getPassword());
        userService.SaveEntry(userInDb);

        // Return a success response
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "User updated successfully",
                        "updatedUser", userInDb
                ));
    }

}


