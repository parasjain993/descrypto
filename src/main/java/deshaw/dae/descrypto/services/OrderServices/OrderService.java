package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;

import java.util.List;

public interface OrderService {
    int placeLimitOrder(Order newLimitOrder);
    int placeMarketOrder(Order newMarketOrder);
    int placeStopLossMarketOrder(Order newSTMarketOrder);
    int placeStopLossLimitOrder(Order newSLLimitOrder);
    List<Order> orderHistory(int userId);
    List <Order> openOrders(int userId);

    void cancelOrder(int orderId);

}
