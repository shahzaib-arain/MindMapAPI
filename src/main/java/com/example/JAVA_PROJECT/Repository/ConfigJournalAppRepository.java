
package com.example.JAVA_PROJECT.Repository;

import com.example.JAVA_PROJECT.Entity.ConfigJournalAppEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
