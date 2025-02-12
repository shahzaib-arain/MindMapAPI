
package com.example.JAVA_PROJECT.Repository;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface JournalEntryRepository extends MongoRepository<JournalEntity, ObjectId> {
}
