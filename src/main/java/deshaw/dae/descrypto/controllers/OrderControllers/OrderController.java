package deshaw.dae.descrypto.controllers.OrderControllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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


    @ApiOperation(value = "Endpoint for placing limit orders for spot and margin trading")
    @PostMapping("/place/limit")
    public EntityModel<?> placeLimitOrder(@RequestBody Order newLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newLimitOrder.setUserId(userId);
        String status=service.placeLimitOrder(newLimitOrder);
        if(status.equals("ok")) {
            Timestamp instant= Timestamp.from(Instant.now());
            //System.out.println(instant);
            newLimitOrder.setTimestamp(instant);
            JSONObject obj1=new JSONObject();
            obj1.put("orderPair",null); obj1.put("startTime",null); obj1.put("endTime",null);
            obj1.put("orderId",newLimitOrder.getOrderId());
            JSONObject obj2=new JSONObject();
            obj2.put("orderPair",null); obj2.put("startTime",null); obj2.put("endTime",null);
            obj2.put("orderId",newLimitOrder.getOrderId());
            obj2.put("orderStatus","open");
            return EntityModel.of(newLimitOrder,
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj1)).withRel("Order History"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj2)).
                            withRel("Open Orders"),
                    linkTo(methodOn(OrderController.class).placeMarketOrder(null)).withRel("Market Order"));
        }
        else {
            JSONObject obj=new JSONObject();
            obj.put("Error",status);
            return EntityModel.of(obj);
        }
    }
    @ApiOperation(value = "Endpoint for placing market orders for spot and margin trading")
    @PostMapping("/place/market")
    EntityModel<?> placeMarketOrder(@RequestBody Order newMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        //System.out.println(userId);
        newMarketOrder.setUserId(userId);
        String status=service.placeMarketOrder(newMarketOrder);
        if(status.equals("ok")) {
            Timestamp instant= Timestamp.from(Instant.now());
            newMarketOrder.setTimestamp(instant);
            JSONObject obj1=new JSONObject();
            JSONObject obj2=new JSONObject();
            obj2.put("orderStatus","open");
            return EntityModel.of(newMarketOrder,
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj1)).withRel("Order History"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj2)).
                            withRel("Open Orders"),linkTo(methodOn(OrderController.class).placeLimitOrder(null)).withRel("Limit Order"));
        }
        else {
            JSONObject obj=new JSONObject();
            obj.put("Error",status);
            return EntityModel.of(obj);
        }
    }
    @ApiOperation(value = "Endpoint for placing stop-loss market orders")
    @PostMapping("/place/stop-loss/market")
    EntityModel<?> placeStopLossMarketOrder(@RequestBody Order newSLMarketOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLMarketOrder.setUserId(userId);
        String status=service.placeStopLossMarketOrder(newSLMarketOrder);
        if(status.equals("ok")){
            Timestamp instant= Timestamp.from(Instant.now());
            newSLMarketOrder.setTimestamp(instant);
            JSONObject obj1=new JSONObject();
            JSONObject obj2=new JSONObject();
            obj2.put("orderStatus","open");
            return EntityModel.of(newSLMarketOrder,
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj1)).withRel("Order History"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj2)).
                            withRel("Open Orders"),linkTo(methodOn(OrderController.class).placeLimitOrder(null)).withRel("Limit Order"),
                    linkTo(methodOn(OrderController.class).placeMarketOrder(null)).withRel("Market Order"));

        }
           else{
            JSONObject obj=new JSONObject();
            obj.put("Error",status);
            return EntityModel.of(obj);
        }
    }
    @ApiOperation(value = "Endpoint for placing stop-loss limit orders")
    @PostMapping("/place/stop-loss/limit")
    EntityModel<?> placeStopLossLimitOrder(@RequestBody Order newSLLimitOrder){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        newSLLimitOrder.setUserId(userId);
        String status=service.placeStopLossLimitOrder(newSLLimitOrder);
        if(status.equals("ok")) {
            Timestamp instant= Timestamp.from(Instant.now());
            newSLLimitOrder.setTimestamp(instant);
            JSONObject obj1=new JSONObject();
            JSONObject obj2=new JSONObject();
            obj2.put("orderStatus","open");
            return EntityModel.of(newSLLimitOrder,
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj1)).withRel("Order History"),
                    linkTo(methodOn(OrderController.class).showOrderHistory(obj2)).
                            withRel("Open Orders"),linkTo(methodOn(OrderController.class).placeLimitOrder(null)).withRel("Limit Order"),
                    linkTo(methodOn(OrderController.class).placeMarketOrder(null)).withRel("Market Order")
            );
        }
        else{
            JSONObject obj=new JSONObject();
            obj.put("Error",status);
            return EntityModel.of(obj);
        }
    }

    @ApiOperation(value = "Endpoint for getting orders from order creation engine")
 @GetMapping("/getDummy/{pair}")
 List<HashMap<String,String>> getOrders(@PathVariable("pair") String pair) {return Market.getOrders(pair);}

    @ApiOperation(value = "Endpoint for checking order history using different filters", tags = { "Order History" })
    @PostMapping("/orderHistory")
    public CollectionModel<EntityModel<Order>> showOrderHistory (@RequestBody JSONObject data){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();

        List<EntityModel<Order>> orders = service.orderHistory(data,userId).stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(TradeController.class).showTradeHistory(data)).withRel("tradeHistory"),
                        linkTo(methodOn(OrderController.class).showOrderHistory(data)).withRel("OrderHistory")))
                .collect(Collectors.toList());



        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).showOrderHistory(data)).withSelfRel());

    }

    @ApiOperation(value = "Endpoint for getting all current open orders", tags = { "Open Orders" })
    @PostMapping("/openOrders")
    List<Order> showOpenOrders(String side,String pair) {
        return service.openOrders(side,pair);
    }


    @ApiOperation(value = "Endpoint for canceling orders", tags = { "Cancel Order" })
    @PostMapping("/cancel/{orderId}")
    EntityModel<?> cancelOrder(@PathVariable("orderId") int orderId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();
        int status=0;

        Order order=service.getOrder(orderId);
        JSONObject obj=new JSONObject();

        if(order.getUserId()!=userId) {
            obj.put("Error","Failed to cancel order");
            return EntityModel.of(obj);
        }
        else if(order.getOrderStatus().compareTo("filled")==0) {
            obj.put("Error","Completed orders cannot be cancelled");
            return EntityModel.of(obj);
        }
        else if(order.getOrderStatus().compareTo("cancelled")==0) {
            obj.put("Error","Order already cancelled");
            return EntityModel.of(obj);
        }

        status=service.cancelOrder(orderId);
        if(status==1)
        obj.put("Success","Order cancelled");
        else obj.put("Error","Failed to cancel order");
        return EntityModel.of(obj);

    }

}

