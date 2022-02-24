package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyServiceImpl implements MyService {

    int a = 1;

    // Cache to store coin details

    @Autowired
    private MyMapper mapper;

    @Override
    public List<User> findAllUsers() {
        return mapper.findAllUsers();
    }


    public Order placeLimitOrder(Order newLimitOrder){
        //add validation to amount of its buy order
        Order newOrder=new Order();
        newOrder.setOrderId(newLimitOrder.getOrderId());
        newOrder.setAmount(newLimitOrder.getAmount());
        newOrder.setOrderType(newLimitOrder.getOrderType());
        return newOrder;//return order that is saved...
        //return mapper.placeLimitOrder();
    }
    public double placeMarketOrder(Order newMarketOrder){
        //execute immediately if the orderbook is not empty

        return executeMarketOrder(newMarketOrder);
    }
    public double executeMarketOrder(Order placed) {
        //check the internal cache and match the best
        //return amount incurred/spent
        return 00.0;
    }

}
