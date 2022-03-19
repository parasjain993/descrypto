package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userService.findByUserName(user.getUserName());
        JSONObject obj = new JSONObject();
        String message;
        if (foundUser != null) {
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                //return EntityModel.of(user, linkTo(methodOn(totalWorthController.class).totalWorth(user.getUserName())).withRel("totalworth"));
                message = "Login Successful!";
                obj.put("success_message", message);
                return new ResponseEntity<>(obj, HttpStatus.OK);
            }
            else {
                message = "Password incorrect";
                obj.put("failure_message", message);
                return new ResponseEntity<>(obj, HttpStatus.UNAUTHORIZED);

            }
        }
        message = "No such user with " + user.getUserName() + " exists. Try registering the user first and then login";
        obj.put("failure-message", message);
        return new ResponseEntity<>(obj,HttpStatus.NOT_FOUND);

    }
}
