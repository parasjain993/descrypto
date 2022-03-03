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
       // if(newLimitOrder.getSide().equals("buy")) {
           //check for validity through wallet api
        //}
        return mapper.placeLimitOrder(newLimitOrder);

    }
    public int placeMarketOrder(Order newMarketOrder){
        newMarketOrder.setFilled(0.0);
        double total = newMarketOrder.getAverage()*newMarketOrder.getAmount();//get currentPrice from api
        newMarketOrder.setTotal(total);
        int status=mapper.placeMarketOrder(newMarketOrder);// save
        return status;
    }

    public int placeStopLossMarketOrder(Order newSTMarketOrder){
        newSTMarketOrder.setFilled(0.0);
        newSTMarketOrder.setTotal(newSTMarketOrder.getAmount()*newSTMarketOrder.getTriggerPrice());
        int status=mapper.placeStopLossMarketOrder(newSTMarketOrder);
        return status;
    }
    public int placeStopLossLimitOrder(Order newSTLimitOrder){
        newSTLimitOrder.setFilled(0.0);
        double total = newSTLimitOrder.getLimitPrice()*newSTLimitOrder.getAmount();
        newSTLimitOrder.setTotal(total);
        int status=mapper.placeStopLossLimitOrder(newSTLimitOrder);
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
