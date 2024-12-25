package com.example.JAVA_PROJECT.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("abc")
    public String HealthChecked(){
        return "AGAIN START JAVA SHAME ON YOU ";


    }
}
