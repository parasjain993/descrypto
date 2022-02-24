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
        return mapper.placeLimitOrder(newLimitOrder);
    }
    public int placeMarketOrder(Order newMarketOrder){

        int status=mapper.placeMarketOrder(newMarketOrder);
        executeMarketOrder(newMarketOrder);
        return status;
    }
    public int executeMarketOrder(Order placed) {
        //return status
        return 1;
    }
}
