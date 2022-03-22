package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.Orderbook;
import deshaw.dae.descrypto.domain.OrderbookEntry;
import deshaw.dae.descrypto.mappers.OrderbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderbookServiceImpl implements OrderbookService{

    @Autowired
    private OrderbookMapper mapper;


    @Override
    public Orderbook allOpenOrdersbyPair(String orderPair) {
        Orderbook AllOpenOrder = new Orderbook();
        List<OrderbookEntry> UserBuyOrders = allBuyOrdersbyPair(orderPair);
        List<OrderbookEntry> UserSellOrders = allSellOrdersbyPair(orderPair);
        // List<OrderbookEntry> DummyBuyOrders =
        Collections.sort(UserBuyOrders, new Comparator<OrderbookEntry>() {
            @Override
            public int compare(OrderbookEntry a1, OrderbookEntry a2) {
                return (int) (a1.limitPrice()- a2.limitPrice());
            }
        });
        Collections.sort(UserSellOrders, new Comparator<OrderbookEntry>() {
            @Override
            public int compare(OrderbookEntry a1, OrderbookEntry a2) {
                return (int) (a1.limitPrice()- a2.limitPrice());
            }
        });
        AllOpenOrder.setAsks(UserSellOrders);
        AllOpenOrder.setBids(UserBuyOrders);




        return AllOpenOrder;
    }

    @Override
    public List<OrderbookEntry> allBuyOrdersbyPair(String orderPair) {
        return mapper.allUserBuyOrdersbyPair(orderPair);
    }

    @Override
    public List<OrderbookEntry> allSellOrdersbyPair(String orderPair) {
        return mapper.allUserSellOrdersbyPair(orderPair);
    }


}
