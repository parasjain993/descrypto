package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.controllers.OrderControllers.OrderController;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
   EntityModel<User> register(@RequestBody User userObject) throws Exception {
        User foundUser = userService.findByUserName(userObject.getUserName());
        if(foundUser != null) {
            return null;
        }
        else {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            userService.addUser(userObject);
            return EntityModel.of(userObject, linkTo(methodOn(LoginController.class).login(userObject)).withRel("login"));
        }

    }

}





