package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private MyService service;

    @GetMapping("/get/users")
    List<User> all() {
        return service.findAllUsers();
    }
    @PostMapping("/place/limit")
    Order placeLimitOrder(@RequestBody Order newLimitOrder){
        return service.placeLimitOrder(newLimitOrder);
    }
    @PostMapping("/place/market")
    double placeMarketOrder(@RequestBody Order newMarketOrder){
        return service.placeMarketOrder(newMarketOrder);
    }
}
