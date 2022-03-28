package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.controllers.OrderControllers.Market;
import deshaw.dae.descrypto.domain.Orderbook;
import deshaw.dae.descrypto.domain.OrderbookEntry;
import deshaw.dae.descrypto.mappers.OrderbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@EnableScheduling
public class OrderbookServiceImpl implements OrderbookService{

    @Autowired
    private OrderbookMapper mapper;


    @Override
    public Orderbook allOpenOrdersbyPair(String orderPair) {
        Orderbook AllOpenOrder = new Orderbook();

        List<OrderbookEntry> BuyOrders = allBuyOrdersbyPair(orderPair);
        List<OrderbookEntry> SellOrders = allSellOrdersbyPair(orderPair);



        List<HashMap<String, String>> dummyorders = Market.getOrders(orderPair);

        System.out.println(dummyorders);

        for (HashMap<String, String> hs: dummyorders) {

            if(Objects.equals(hs.get("Side"), "Buy") && isNotEmpty(hs.get("Amount"))){
                OrderbookEntry tmp = new OrderbookEntry(Double.parseDouble(hs.get("Amount")),Double.parseDouble(hs.get("limitPrice")));
                BuyOrders.add(tmp);
            }else if(Objects.equals(hs.get("Side"), "Sell") && isNotEmpty(hs.get("Amount"))){
                OrderbookEntry tmp = new OrderbookEntry(Double.parseDouble(hs.get("Amount")),Double.parseDouble(hs.get("limitPrice")));
                SellOrders.add(tmp);
            }


        }
        Collections.sort(BuyOrders, new Comparator<OrderbookEntry>() {
            @Override
            public int compare(OrderbookEntry a1, OrderbookEntry a2) {
                return (int) (a1.limitPrice()- a2.limitPrice());
            }
        });
        Collections.sort(SellOrders, new Comparator<OrderbookEntry>() {
            @Override
            public int compare(OrderbookEntry a1, OrderbookEntry a2) {
                return (int) (a1.limitPrice()- a2.limitPrice());
            }
        });
        AllOpenOrder.setAsks(SellOrders);
        AllOpenOrder.setBids(BuyOrders);




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
