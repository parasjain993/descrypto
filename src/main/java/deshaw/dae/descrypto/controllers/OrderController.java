package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/place/limit")
    int placeLimitOrder(@RequestBody Order newLimitOrder){

        return service.placeLimitOrder(newLimitOrder);
    }
    @PostMapping("/place/market")
    int placeMarketOrder(@RequestBody Order newMarketOrder){
        return service.placeMarketOrder(newMarketOrder);
    }
}
