package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/orderHistory/{userId}")
    List<Order> showOrderHistory(@PathVariable("userId") int userId) {
        return service.orderHistory(userId);
    }


    @GetMapping("/openOrders/{userId}")
    List<Order> showOpenOrders(@PathVariable("userId") int userId) {
        return service.openOrders(userId);
    }

    @PostMapping("/cancel/{orderId}")
    void cancelOrder(@PathVariable("orderId") int orderId){
        service.cancelOrder(orderId);
    }
}
