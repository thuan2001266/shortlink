package com.nauht.shortlink.user;

import com.nauht.shortlink.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isUserActive(String username) {
        return userRepository.findByEmail(username).get().isActive();
    }

    public ResponseEntity<HttpStatus> deactivateUser(String username) {
        try {
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isPresent()) {
                User updateUser = user.get();
                updateUser.setActive(false);
                userRepository.save(updateUser);
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> activateUser(String username) {
        try {
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isPresent()) {
                User updateUser = user.get();
                updateUser.setActive(true);
                userRepository.save(updateUser);
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
