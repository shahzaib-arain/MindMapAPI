package com.example.JAVA_PROJECT.Controller;

import com.example.JAVA_PROJECT.Entity.UserEntity;
import com.example.JAVA_PROJECT.Service.UserService;
import com.example.JAVA_PROJECT.cache.AppCache;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppCache appCache;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> all = userService.GetAllEntries();
        if(all != null && !all .isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUsers(@RequestBody UserEntity userEntity) {
        userService.SaveAdmin(userEntity);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}

