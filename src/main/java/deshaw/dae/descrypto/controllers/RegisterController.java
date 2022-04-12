package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.controllers.OrderControllers.OrderController;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(description = "Endpoint for registering the user",tags = {"Register"})
@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Register", tags = { "Register" })
    @RequestMapping(value = "/register", method= RequestMethod.POST)
    EntityModel<?> register(@RequestBody User userObject)  {
        JSONObject obj = new JSONObject();
        String message;
        if(userObject.getPassword().length() >= 8 && userObject.getPassword().matches("(?=.*[0-9]).*") && userObject.getPassword().matches("(?=.*[a-z]).*") && userObject.getPassword().matches("(?=.*[A-Z]).*")  && userObject.getPassword().matches("(?=.*[~!@#$%^&*()_-]).*")) {
            User foundUser = userService.findByUserName(userObject.getUserName());

            if(foundUser != null) {
                message = "User with same username already exists";
                obj.put("failure-message", message);
                obj.put("Status", HttpStatus.BAD_REQUEST);
                return
                        EntityModel.of(obj,
                                linkTo(methodOn(RegisterController.class).register(userObject)).withRel("Try Registering with different username again")
                        );
            }
            else {
                userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
                userService.addUser(userObject);
                message = "Registration done successfully";
                obj.put("success-message", message);
                obj.put("Status", HttpStatus.OK);
                return
                        EntityModel.of(obj,
                                linkTo(methodOn(LoginController.class).login(null)).withRel("Login")
                        );
            }
        }
        else {
            message = "Password should be of atleast 8 characters and should contain atleast one lower, one upper letter, one special character and one digit";
            obj.put("failure-message", message);
            obj.put("Status", HttpStatus.BAD_REQUEST);
            return
                    EntityModel.of(obj,
                            linkTo(methodOn(RegisterController.class).register(userObject)).withRel("Try Registering with correct format password again")
                    );
        }
    }
}





