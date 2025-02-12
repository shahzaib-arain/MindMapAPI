
package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.JournalService;
import com.example.JAVA_PROJECT.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Journal")
public class JournalController {

    @Autowired
    public JournalService journalService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.findByUserName(userName);
        List<JournalEntity> entries = userEntity.getJournalEntities();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.findByUserName(userName);
        List<JournalEntity> collect = userEntity.getJournalEntities().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntity = journalService.findById(myId);
            if(journalEntity.isPresent()){
                return new ResponseEntity<>(journalEntity.get(),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<String> createEntries(@RequestBody JournalEntity myEntry ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalService.SaveEntry(myEntry,userName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntity updatedEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.findByUserName(userName);

        // Check if the entry belongs to the authenticated user
        boolean isOwner = userEntity.getJournalEntities().stream()
                .anyMatch(entry -> entry.getId().equals(myId));

        if (!isOwner) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Prevent unauthorized updates
        }

        return journalService.findById(myId)
                .map(existingEntry -> {
                    existingEntry.setTitle(Optional.ofNullable(updatedEntry.getTitle())
                            .filter(title -> !title.isEmpty())
                            .orElse(existingEntry.getTitle()));

                    existingEntry.setContent(Optional.ofNullable(updatedEntry.getContent())
                            .filter(content -> !content.isEmpty())
                            .orElse(existingEntry.getContent()));

                    journalService.SaveEntry(existingEntry);
                    return new ResponseEntity<>(existingEntry, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("id/{myId}")
    public ResponseEntity<String> deleteEntries(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalService.DeleteEntries(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }






}