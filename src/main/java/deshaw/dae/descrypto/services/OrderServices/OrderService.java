package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.domain.Order;

public interface OrderService {
    int placeLimitOrder(Order newLimitOrder);
    int placeMarketOrder(Order newMarketOrder);
}
