package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
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

@Api(description = "Endpoint for logging in user",tags = {"Login"})
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Login", tags = { "Login" })
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public EntityModel<?> login(@RequestBody User user) {
        User foundUser = userService.findByUserName(user.getUserName());
        JSONObject obj = new JSONObject();
        String message;
        if (foundUser != null) {
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                //return EntityModel.of(user, linkTo(methodOn(totalWorthController.class).totalWorth(user.getUserName())).withRel("totalworth"));
                message = "Login Successful!";
                obj.put("success_message", message);
                obj.put("Status", HttpStatus.OK);
                return EntityModel.of(obj,
                        linkTo(methodOn(totalWorthController.class).totalWorth()).withRel("TotalWorth")
                );
            }
            else {
                message = "Password incorrect";
                obj.put("failure_message", message);
                obj.put("Status", HttpStatus.UNAUTHORIZED);
                return EntityModel.of(obj,
                        linkTo(methodOn(LoginController.class).login(user)).withRel("Try logging again with correct credentials")
                );

            }
        }
        message = "No such user with " + user.getUserName() + " exists. Try registering the user first and then login";
        obj.put("failure-message", message);
        obj.put("Status", HttpStatus.NOT_FOUND);
        return EntityModel.of(obj,
                linkTo(methodOn(LoginController.class).login(user)).withRel("Try logging again with correct credentials"),
                linkTo(methodOn(RegisterController.class).register(user)).withRel("First register and then login")
        );

    }
}
