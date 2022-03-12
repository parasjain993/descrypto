package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userService.findByUserName(user.getUserName());
        if (foundUser != null) {
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                //return EntityModel.of(user, linkTo(methodOn(totalWorthController.class).totalWorth(user.getUserName())).withRel("totalworth"));

                return new ResponseEntity<>("Login Successful!", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Password incorrect", HttpStatus.UNAUTHORIZED);

            }
        }
        return new ResponseEntity<>("No such user with " + user.getUserName() + " exists. Try registering the user first and then login",HttpStatus.NOT_FOUND);

    }
}
