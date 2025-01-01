package com.example.JAVA_PROJECT.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntity {

    @Id
    private ObjectId id;
    private String title;
    private String content;




}