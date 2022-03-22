package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONObject;

import java.util.List;

@Mapper
public interface OrderMapper {
    int placeOrder(Order Order);

//    List<Order> openOrders(int userId);

    List<Order> orderHistory(JSONObject data,int userId);
    List<Order> openOrders(String side, String pair);
    void updateOrder(Order order);
    void cancelOrder(int orderId);

}
