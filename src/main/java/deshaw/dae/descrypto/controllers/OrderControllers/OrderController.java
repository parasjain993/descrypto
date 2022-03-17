package deshaw.dae.descrypto.controllers.OrderControllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import deshaw.dae.descrypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private UserService userService;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @PostMapping("/place/limit")
    EntityModel<Order> placeLimitOrder(@RequestBody Order newLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newLimitOrder.setUserId(userId);
        int status=service.placeLimitOrder(newLimitOrder);
        if(status==1) {
            LocalDateTime now = LocalDateTime.now();
            newLimitOrder.setDateTime(dtf.format(now)+"");
            return EntityModel.of(newLimitOrder,
                    linkTo(methodOn(OrderController.class).showOpenOrders(newLimitOrder.getUserId())).withRel("openOrders"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(newLimitOrder.getUserId())).
                            withRel("orderHistory")
                    );
        }
        else {
            return null;
        }
    }
    @PostMapping("/place/market")
    EntityModel<Order> placeMarketOrder(@RequestBody Order newMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newMarketOrder.setUserId(userId);
        int status=service.placeMarketOrder(newMarketOrder);
        if(status==1) {
            LocalDateTime now = LocalDateTime.now();
            newMarketOrder.setDateTime(dtf.format(now)+"");
            return EntityModel.of(newMarketOrder, linkTo(methodOn(OrderController.class).
                            showOpenOrders(newMarketOrder.getUserId())).withRel("openOrders"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(newMarketOrder.getUserId())).
                            withRel("orderHistory"));
        }
        else
            return null;
    }
    @PostMapping("/place/stop-loss/market")
    EntityModel<Order> placeStopLossMarketOrder(@RequestBody Order newSLMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLMarketOrder.setUserId(userId);
        int status=service.placeStopLossMarketOrder(newSLMarketOrder);
        if(status==1){
            LocalDateTime now = LocalDateTime.now();
            newSLMarketOrder.setDateTime(dtf.format(now)+"");
            return EntityModel.of(newSLMarketOrder,linkTo(methodOn(OrderController.class).showOpenOrders(newSLMarketOrder.getUserId())).withRel("openOrders"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(newSLMarketOrder.getUserId())).
                            withRel("orderHistory"));
        }
           else

            return null;
    }
    @PostMapping("/place/stop-loss/limit")
    EntityModel<Order> placeStopLossLimitOrder(@RequestBody Order newSLLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLLimitOrder.setUserId(userId);
        int status=service.placeStopLossMarketOrder(newSLLimitOrder);
        if(status==1) {
            LocalDateTime now = LocalDateTime.now();
            newSLLimitOrder.setDateTime(dtf.format(now)+"");
            return EntityModel.of(newSLLimitOrder,linkTo(methodOn(OrderController.class).showOpenOrders(newSLLimitOrder.getUserId())).withRel("openOrders"),
            linkTo(methodOn(OrderController.class).showOrderHistory(newSLLimitOrder.getUserId())).
                    withRel("orderHistory"));
        }
        else
            return null;
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
    @GetMapping("/getDummy/{pair}")
    List<HashMap<String,String>> getOrders(@PathVariable("pair") String pair) {
        return Market.getOrders(pair);
    }

}
