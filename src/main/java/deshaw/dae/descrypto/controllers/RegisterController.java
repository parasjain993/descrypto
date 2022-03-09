package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.controllers.OrderControllers.OrderController;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
   ResponseEntity<?> register(@RequestBody User userObject)  {
        User foundUser = userService.findByUserName(userObject.getUserName());
        if(foundUser != null) {
            return new ResponseEntity<>("User with same userName already exists", HttpStatus.BAD_REQUEST);
        }
        else {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            userService.addUser(userObject);
            return new ResponseEntity<>("Registration done successfully", HttpStatus.OK);
        }

    }

}





