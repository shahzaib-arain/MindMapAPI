
package com.example.JAVA_PROJECT.Repository;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUserName(String userName);
}
