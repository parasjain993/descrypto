package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Orderbook;
import deshaw.dae.descrypto.domain.OrderbookEntry;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderbookService {

    Orderbook allOpenOrdersbyPair(String orderPair);

    List<OrderbookEntry> allBuyOrdersbyPair(String orderPair);
    List<OrderbookEntry> allSellOrdersbyPair(String orderPair);

}
