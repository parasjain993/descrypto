package deshaw.dae.descrypto.controllers.OrderControllers;

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
    Order placeLimitOrder(@RequestBody Order newLimitOrder){

        int status=service.placeLimitOrder(newLimitOrder);
        if(status==1)
            return newLimitOrder;
        else
            return null;
    }
    @PostMapping("/place/market")
    Order placeMarketOrder(@RequestBody Order newMarketOrder){

        int status=service.placeMarketOrder(newMarketOrder);
        if(status==1)
            return newMarketOrder;
        else
            return null;
    }
    @PostMapping("/place/stop-loss/market")
    Order placeStopLossMarketOrder(@RequestBody Order newSLMarketOrder){
        int status=service.placeStopLossMarketOrder(newSLMarketOrder);
        if(status==1)
            return newSLMarketOrder;
        else
            return null;
    }
    @PostMapping("/place/stop-loss/limit")
    Order placeStopLossLimitOrder(@RequestBody Order newSTLimitOrder){
        int status=service.placeStopLossMarketOrder(newSTLimitOrder);
        if(status==1)
            return newSTLimitOrder;
        else
            return null;
    }
}
