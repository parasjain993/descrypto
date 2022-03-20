package deshaw.dae.descrypto.controllers.OrderControllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(description = "Endpoints for placing different types of orders",tags = {"Order Placing"})
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private UserService userService;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @ApiOperation(value = "Endpoint for placing limit orders for spot and margin trading", tags = { "Limit Order" })
    @PostMapping("/place/limit")
    EntityModel<Order> placeLimitOrder(@RequestBody Order newLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newLimitOrder.setUserId(userId);
        String status=service.placeLimitOrder(newLimitOrder);
        if(status.equals("ok")) {
            LocalDateTime now = LocalDateTime.now();
            newLimitOrder.setDateTime(dtf.format(now)+"");

            return EntityModel.of(newLimitOrder,
                    linkTo(methodOn(OrderController.class).showOpenOrders(newLimitOrder.getUserId())).withRel("openOrders"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(newLimitOrder.getUserId())).
                            withRel("orderHistory"));
        }
        else {
            return null;
        }
    }
    @ApiOperation(value = "Endpoint for placing market orders for spot and margin trading", tags = { "Market Order" })
    @PostMapping("/place/market")
    EntityModel<Order> placeMarketOrder(@RequestBody Order newMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newMarketOrder.setUserId(userId);
        String status=service.placeMarketOrder(newMarketOrder);
        if(status.equals("ok")) {
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
    @ApiOperation(value = "Endpoint for placing stop-loss market orders", tags = { "Stop-loss Market Order" })
    @PostMapping("/place/stop-loss/market")
    EntityModel<Order> placeStopLossMarketOrder(@RequestBody Order newSLMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLMarketOrder.setUserId(userId);
        String status=service.placeStopLossMarketOrder(newSLMarketOrder);
        if(status.equals("ok")){
            LocalDateTime now = LocalDateTime.now();
            newSLMarketOrder.setDateTime(dtf.format(now)+"");
            return EntityModel.of(newSLMarketOrder,linkTo(methodOn(OrderController.class).showOpenOrders(newSLMarketOrder.getUserId())).withRel("openOrders"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(newSLMarketOrder.getUserId())).
                            withRel("orderHistory"));
        }
           else

            return null;
    }
    @ApiOperation(value = "Endpoint for placing stop-loss limit orders", tags = { "Stop-loss Limit Order" })
    @PostMapping("/place/stop-loss/limit")
    EntityModel<Order> placeStopLossLimitOrder(@RequestBody Order newSLLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLLimitOrder.setUserId(userId);
        String status=service.placeStopLossLimitOrder(newSLLimitOrder);
        if(status.equals("ok")) {
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
    @ApiOperation(value = "Endpoint for getting orders from order creation engine", tags = { "Orders from Markets" })
    @GetMapping("/getDummy/{pair}")
    List<HashMap<String,String>> getOrders(@PathVariable("pair") String pair) {
        return Market.getOrders(pair);
    }

}

