
package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.RuntimeMBeanException;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    public JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntity> GetAllEntries(){
        return journalEntryRepository.findAll();

    }
    @Transactional
    public void SaveEntry (JournalEntity journalEntity, String userName){
        try{
            UserEntity userEntity = userService.findByUserName(userName);
            JournalEntity saved = journalEntryRepository.save(journalEntity);
            userEntity.getJournalEntities().add(saved);
            userService.SaveEntry(userEntity);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("AN error occurred while saving the entry",e);
        }

    }
    public void SaveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }


    public Boolean UpdateEntry(ObjectId myId, JournalEntity updatedEntry) {
        Optional<JournalEntity> existingEntry = journalEntryRepository.findById(myId);

        if (existingEntry.isPresent()) {
            JournalEntity entryToUpdate = existingEntry.get();
            // Update the fields of the existing entry with the values from updatedEntry
            entryToUpdate.setTitle(updatedEntry.getTitle());
            entryToUpdate.setContent(updatedEntry.getContent());
            // Save the updated entry
            journalEntryRepository.save(entryToUpdate);
            return true;
        }

        return false;
    }

    public boolean DeleteEntries(ObjectId id, String userName) {
        UserEntity userEntity = userService.findByUserName(userName);
        userEntity.getJournalEntities().removeIf(x -> x.getId().equals(id));
        userService.SaveEntry(userEntity);
        if (journalEntryRepository.existsById(id)) {
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public JournalEntity GetEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId).orElse(null);
    }




}
