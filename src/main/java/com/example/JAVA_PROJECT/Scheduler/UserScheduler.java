package com.example.JAVA_PROJECT.Scheduler;

import com.example.JAVA_PROJECT.Entity.JournalEntity;
import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Enum.Sentiment;
import com.example.JAVA_PROJECT.Repository.UserRepositoryImpl;
import com.example.JAVA_PROJECT.Service.EmailService;
import com.example.JAVA_PROJECT.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;


    @Autowired
    private AppCache appCache;

    //@Scheduled(cron = "0 0 9 ? * SUN,FRI")
    //@Scheduled(cron = "*/5 * * * * *")

    public void fetchUsersAndSendEmail(){

        List<UserEntity> users = userRepository.getUserForSa();
        for (UserEntity user:users) {
            List<JournalEntity> journalEntities = user.getJournalEntities();
            List<Sentiment> sentiments = journalEntities.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment:
                 sentiments) {
                if(sentiment != null)
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0) +1);

                }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment,Integer> entry :sentimentCounts.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }

            }
            if(mostFrequentSentiment != null) {
                emailService.SendEmail(user.getEmail(), "Sentiment for last 7 days ",mostFrequentSentiment.toString());
            }


                
            }
        }


    @Scheduled(cron = "0 */5 * * * *")
    public void clearAppCache(){
        appCache.init();
    }
}
