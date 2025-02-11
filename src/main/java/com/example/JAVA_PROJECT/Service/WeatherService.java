package com.example.JAVA_PROJECT.Service;

import com.example.JAVA_PROJECT.Api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String Apikey = "c8b8e70c6a3858b8edb9870d4213de39";
    private static final String Api = "http://api.weatherstack.com/current?access_key=ApiKey&query=City";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String FinalApi = Api.replace("City",city).replace("ApiKey",Apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(FinalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
