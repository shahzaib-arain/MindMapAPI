package com.example.JAVA_PROJECT.Repository;

import com.example.JAVA_PROJECT.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImpl {
@Autowired
private MongoTemplate mongoTemplate;

public List<UserEntity> getUserForSa(){
    Query query = new Query();
    query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
    query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
    List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
    return users;



}

}
