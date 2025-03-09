package com.example.JAVA_PROJECT.cache;

import com.example.JAVA_PROJECT.Entity.ConfigJournalAppEntity;
import com.example.JAVA_PROJECT.Repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum Keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity :all) {
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }

}
