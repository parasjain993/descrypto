package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int placeOrder(Order Order);
    List<Order> orderHistory(int userId);
    List<Order> openOrders(int userId);
    void cancelOrder(int orderId);

}
