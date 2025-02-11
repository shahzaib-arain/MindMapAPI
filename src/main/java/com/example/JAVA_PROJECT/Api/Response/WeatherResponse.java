package com.example.JAVA_PROJECT.Api.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
private Current  current;
@Getter
@Setter
public class Current{
    private int temperature;
    @JsonProperty("weather_descriptions")
    private List<String> weatherDescriptions;
    private int feelslike;

}
}
