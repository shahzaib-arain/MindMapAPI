
package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Journal")
public class JournalController {

    @Autowired
    public JournalService journalService;


    @GetMapping
    public ResponseEntity<List<JournalEntity>> getJournalEntries() {
        List<JournalEntity> entries = journalService.GetAllEntries();
        if (entries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(entries);
        }
        return ResponseEntity.ok(entries);
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        try {
            JournalEntity entry = journalService.GetEntryById(myId);
            if (entry != null) {
                return ResponseEntity.ok(entry);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry with the given ID not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the journal entry.");
        }
    }


    @PostMapping
    public ResponseEntity<String> createEntries(@RequestBody JournalEntity myEntry) {
        try {
            journalService.SaveEntry(myEntry);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<String> updateEntry(@PathVariable String myId, @RequestBody JournalEntity updatedEntry) {
        if (!ObjectId.isValid(myId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format.");
        }
        ObjectId id = new ObjectId(myId);
        boolean isUpdated = journalService.UpdateEntry(id, updatedEntry);
        if (isUpdated) {
            return ResponseEntity.ok("Journal entry updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry with the given ID not found.");
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<String> deleteEntries(@PathVariable String myId) {
        if (!ObjectId.isValid(myId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format.");
        }
        ObjectId id = new ObjectId(myId);
        boolean isDeleted = journalService.DeleteEntries(id);
        if (isDeleted) {
            return ResponseEntity.ok("Journal entry deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry with the given ID not found.");
    }






}