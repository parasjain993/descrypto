package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper mapper;
    public int placeLimitOrder(Order newLimitOrder){

        newLimitOrder.setFilled(0.0);
        double total = newLimitOrder.getLimitPrice()*newLimitOrder.getAmount();
        newLimitOrder.setTotal(total);
       // if(newLimitOrder.getOrderType().equals("buy")) {
           //check for validity through wallet api
        //}
        return mapper.placeLimitOrder(newLimitOrder);
    }
    public int placeMarketOrder(Order newMarketOrder){
        newMarketOrder.setFilled(0.0);
       // double total = currentPrice*newMarketOrder.getAmount();//get currentPrice from api
        //newMarketOrder.setTotal(total);
        int status=mapper.placeMarketOrder(newMarketOrder);// save to db
        //call api for immediate execution of market order
        return status;
    }

    @Override
    public List<Order> orderHistory(int userId) {
        return mapper.orderHistory(userId);
    }

    @Override
    public List<Order> openOrders(int userId) {
        return mapper.openOrders(userId);
    }

    public void cancelOrder(int orderId) {
        mapper.cancelOrder(orderId);
    }

}
