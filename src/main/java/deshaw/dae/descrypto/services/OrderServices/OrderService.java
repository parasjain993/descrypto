package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;

import java.util.List;

public interface OrderService {
    String placeLimitOrder(Order newLimitOrder);
    String placeMarketOrder(Order newMarketOrder);
    String placeStopLossMarketOrder(Order newSLMarketOrder);
    String placeStopLossLimitOrder(Order newSLLimitOrder);
    List<Order> orderHistory(int userId);
    List <Order> openOrders(int userId);

    void cancelOrder(int orderId);

}
