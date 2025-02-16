package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.UserDetailsServiceImpl;
import com.example.JAVA_PROJECT.Service.UserService;
import com.example.JAVA_PROJECT.Utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("abc")
    public String HealthCheck(){
        return "AGAIN START JAVA SHAME ON YOU ";
    }

    @PostMapping("/SignUp")
    public ResponseEntity<?> SignUp(@RequestBody UserEntity userEntity) {
        try {
            userService.SaveNewUser(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create journal entry.");
        }
    }

    @PostMapping("/LogIn")
    public ResponseEntity<?> LogIn(@RequestBody UserEntity userEntity) {
        try {
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUserName(),userEntity.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while create AuthenticationToken ",e);
            return new ResponseEntity<>("In Correct UserName and Password",HttpStatus.BAD_REQUEST);
        }
    }

}
