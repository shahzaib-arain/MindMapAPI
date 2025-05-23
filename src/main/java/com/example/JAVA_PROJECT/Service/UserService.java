
package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Repository.JournalEntryRepository;
import com.example.JAVA_PROJECT.Repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    public UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserEntity> GetAllEntries(){
        return userEntryRepository.findAll();

    }
    public void SaveEntry (@RequestBody UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userEntity);
    }
    public void SaveUserEntry (@RequestBody UserEntity userEntity){

        userEntryRepository.save(userEntity);
    }
    public Boolean UpdateEntry(ObjectId myId, UserEntity updatedEntry) {
        Optional<UserEntity> existingEntry = userEntryRepository.findById(myId);

        if (existingEntry.isPresent()) {
            UserEntity entryToUpdate = existingEntry.get();
            // Update the fields of the existing entry with the values from updatedEntry
            entryToUpdate.setUserName(updatedEntry.getUserName());
            entryToUpdate.setPassword(updatedEntry.getPassword());
            // Save the updated entry
            userEntryRepository.save(entryToUpdate);
            return true;
        }

        return false;
    }

    public boolean DeleteEntries(ObjectId id) {
        if (userEntryRepository.existsById(id)) {
            userEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public UserEntity GetEntryById(ObjectId myId) {
        return userEntryRepository.findById(myId).orElse(null);
    }

  public UserEntity findByUserName(String userName){
       return userEntryRepository.findByUserName(userName);
  }


}
