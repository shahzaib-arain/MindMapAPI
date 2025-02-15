package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Api.Response.WeatherResponse;
import com.example.JAVA_PROJECT.cache.AppCache;
import com.example.JAVA_PROJECT.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String Apikey ;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){

        WeatherResponse weatherResponse = redisService.get("weather_of_" + city ,WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String FinalApi = appCache.appCache.get(AppCache.Keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,Apikey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(FinalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city ,body, 300l);
            }
            return body;
        }
    }
}
