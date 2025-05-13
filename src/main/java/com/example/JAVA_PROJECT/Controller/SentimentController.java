package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Enum.Sentiment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/sentiments")
public class SentimentController {

        @GetMapping
        public List<String> getAllSentiments() {
            return Arrays.stream(Sentiment.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }
}


