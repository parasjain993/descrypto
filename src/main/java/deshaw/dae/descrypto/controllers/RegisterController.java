package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.controllers.OrderControllers.OrderController;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.json.simple.JSONObject;
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

    @RequestMapping(value = "/register", method= RequestMethod.POST)
   ResponseEntity<?> register(@RequestBody User userObject)  {
        JSONObject obj = new JSONObject();
        String message;
        User foundUser = userService.findByUserName(userObject.getUserName());
        if(foundUser != null) {
            message = "User with same userName already exists";
            obj.put("failure-message", message);
            return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
        }
        else {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            userService.addUser(userObject);
            message = "Registration done successfully";
            obj.put("success-message", message);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }

    }

}





