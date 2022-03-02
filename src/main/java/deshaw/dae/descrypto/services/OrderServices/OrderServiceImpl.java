package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
}
