package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;
import org.json.simple.JSONObject;

import java.util.List;

public interface OrderService {
    String placeLimitOrder(Order newLimitOrder);
    String placeMarketOrder(Order newMarketOrder);
    String placeStopLossMarketOrder(Order newSLMarketOrder);
    String placeStopLossLimitOrder(Order newSLLimitOrder);
//    List <Order> openOrders(int userId);

    List<Order> orderHistory(JSONObject data,int userId);
    List <Order> openOrders(String side,String pair);
    void updateOrder(Order order);
    int cancelOrder(int orderId);
    Order getOrder(int orderId);

}
