package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import java.util.List;


public interface MyService {

    List<User> findAllUsers();

    Order placeLimitOrder(Order newLimitOrder);
    double placeMarketOrder(Order newMarketOrder);



}
