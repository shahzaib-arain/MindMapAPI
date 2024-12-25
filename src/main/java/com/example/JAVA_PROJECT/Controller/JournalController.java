
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
    public List<JournalEntity> getJournalEntries() {
        return journalService.GetAllEntries();
    }

    @PostMapping
        public ResponseEntity<?> CreateEntries (@RequestBody JournalEntity MyEntry){
        journalService.SaveEntry(MyEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("id/{myId}")
    public Boolean UpdateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntity updatedEntry) {
        return journalService.UpdateEntry(myId, updatedEntry);
    }

    @DeleteMapping("id/{myId}")
    public Boolean  DeleteEntries (@PathVariable ObjectId myId){
         journalService.DeleteEntries(myId);
         return true;
    }








}
