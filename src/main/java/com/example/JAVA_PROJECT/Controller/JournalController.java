
package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.JournalService;
import com.example.JAVA_PROJECT.Service.UserService;
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

    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntity>> getJournalEntries(@PathVariable String userName) {
        UserEntity userEntity = userService.findByUserName(userName);
        List<JournalEntity> entries = userEntity.getJournalEntities();
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


    @PostMapping("/{userName}")
    public ResponseEntity<String> createEntries(@RequestBody JournalEntity myEntry ,@PathVariable String userName) {
        try {
            journalService.SaveEntry(myEntry,userName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @PathVariable String userName ,@RequestBody JournalEntity updatedEntry) {
   JournalEntity old = journalService.GetEntryById(myId);

   if (old != null){
       old.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : old.getTitle());
       old.setContent(updatedEntry.getContent() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getContent() : old.getContent());
       journalService.SaveEntry(old);
       return  new ResponseEntity<>(old ,HttpStatus.OK);

   }
   return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<String> deleteEntries(@PathVariable String myId,@PathVariable String userName) {
        if (!ObjectId.isValid(myId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format.");
        }
        ObjectId id = new ObjectId(myId);
        boolean isDeleted = journalService.DeleteEntries(id,userName);
        if (isDeleted) {
            return ResponseEntity.ok("Journal entry deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry with the given ID not found.");
    }






}