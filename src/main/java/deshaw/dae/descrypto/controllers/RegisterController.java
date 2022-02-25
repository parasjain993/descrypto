package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User userObject) {
        System.out.print("inside register controller");
        User foundUser = userService.findByUsername(userObject.getUsername());
        if(foundUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            userService.addUser(userObject);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

}





