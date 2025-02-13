package com.example.JAVA_PROJECT.Entity;

import com.example.JAVA_PROJECT.Enum.Sentiment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntity {

    @Id
    private ObjectId id;
    private String title;
    private String content;

    private LocalDateTime date;
    private Sentiment sentiment;



}