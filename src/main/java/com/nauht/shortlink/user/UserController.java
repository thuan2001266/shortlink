package com.nauht.shortlink.user;

import com.nauht.shortlink.statistic.ClickDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/activate")
    public ResponseEntity<HttpStatus> activateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ActiveRequest username) {
        return userService.activateUser(username.getUsername());
    }

    @PostMapping("/deactivate")
    public ResponseEntity<HttpStatus> deactivateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ActiveRequest username) {
        return userService.deactivateUser(username.getUsername());
    }
}
