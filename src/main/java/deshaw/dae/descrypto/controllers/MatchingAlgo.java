package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.*;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.DashboardServiceImpl;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import deshaw.dae.descrypto.services.TradeServices.TradeService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

@EnableScheduling
@RestController
@RequestMapping("/")
public class MatchingAlgo  {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserService userService;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private WalletService walletService;

    private DashboardCache TokenCache = DashboardCache.getDashboardCache();

    Comparator cmp=new Comparator() {

        @Override
        public int compare(Object a, Object b) {
            Order o1 = (Order) a;
            Order o2 = (Order) b;

            if(o1.getLimitPrice()==o2.getLimitPrice()) return o1.getTimestamp().compareTo(o2.getTimestamp());

            return Double.compare(o1.getLimitPrice(),o2.getLimitPrice());
        }
    };;

    void calAverageAndTotal(Order order){

        JSONObject data=new JSONObject();
        data.put("orderPair",null); data.put("timestamp1",null); data.put("timestamp2",null);
        data.put("orderId",order.getOrderId());
        List<Trade> trades=new ArrayList<Trade>();
        trades=tradeService.tradeHistory(data,order.getUserId());
        double average=0,total=0;
        int count=trades.size();

        for(int i=0;i<trades.size();i++){
            average+=trades.get(i).getPrice();
            total+=trades.get(i).getTotal();
        }

        average=average/count;

        order.setAverage(average);
        order.setTotal(total);

    }

    void createTrade(Order S, Order B) throws InterruptedException {

        Order s=S,b=B;


        Trade trade=new Trade();
        trade.setBuy_Id(b.getOrderId()); trade.setSell_Id(s.getOrderId());

        if((s.getAmount()-s.getFilled()) >= (b.getAmount()-b.getFilled())) {
            Order temp=s;
            s=b;
            b=temp;
        }

        double price=(s.getLimitPrice()+b.getLimitPrice())/2.00;

        if(s.getOrderType().compareTo("market")==0)  price=b.getLimitPrice();
        else if(b.getOrderType().compareTo("market")==0) price=s.getLimitPrice();

        trade.setFilled((s.getAmount()-s.getFilled()));
        trade.setPrice(price);
        trade.setTotal(trade.getPrice()*trade.getFilled());
        trade.setTimestamp(new Timestamp(System.currentTimeMillis()));
        tradeService.createTrade(trade);

        Double amountFilled=s.getAmount()-s.getFilled();
        s.setFilled(s.getAmount());
        s.setOrderStatus("filled");
        calAverageAndTotal(s);


        double newfilled=b.getFilled()+amountFilled;
        b.setFilled(newfilled);

        if(newfilled==b.getAmount())
            b.setOrderStatus("filled");
        else
            b.setOrderStatus("partially_filled");
        calAverageAndTotal(b);

        orderService.updateOrder(s);
        orderService.updateOrder(b);


    }

    boolean if_valid(Order order){

        /*TradingPairs tradingPair=dashboardService.getTradingPairbyId(order.getOrderPair());
        if(order.getSide().compareTo("sell")==0) {
            if( walletService.getAssetCoins(order.getUserId(),tradingPair.Asset1ID())>=(order.getAmount()-order.getFilled())){
                return true;
            }
            return false;
        }
        else {
            HashMap<String, Float> assetsOfUser =  walletService.findAssetsForUser(order.getUserId());
            if(TokenCache.TokenCache.get(tradingPair.Asset2ID() + "usdt").getPrice() * assetsOfUser.get(tradingPair.Asset1ID())>=order.getLimitPrice()) {
                return true;
            }
            return false;
        }*/
        return true;
    }

    int matchMarket(Order order,List<Order> orders,int start,boolean if_buy) throws InterruptedException {
        if(if_buy){
            Collections.reverse(orders);
        }

        while(start<(orders.size())&&order.getFilled()!=order.getAmount()) {

            if(orders.get(start).getOrderType().compareTo("market")==0||orders.get(start).getUserId()==order.getUserId()||orders.get(start).getOrderStatus().compareTo("filled")==0) {
                start++;
                continue;
            }
            if(order.getSide().compareTo("sell")==0) {
                createTrade(order,orders.get(start));
            }
            else createTrade(orders.get(start),order);
            start++;
        }

        if(order.getFilled()==0) order.setOrderStatus("cancelled");
        else order.setOrderStatus("filled");

        if(if_buy){
            Collections.reverse(orders);
        }
        return start;
    }

    void marketAlgorithm(List<Order> sell,List<Order> buy) throws InterruptedException {

        Collections.sort(buy, cmp);
        Collections.sort(sell, cmp);

        int p=0;
        for(int l = 0; l < buy.size(); l++) {
            if(buy.get(l).getOrderType().compareTo("market")==0) {
                p=matchMarket(buy.get(l),sell,p,true);
            }
            if(p>=sell.size()) break;
        }

        Collections.sort(buy, cmp);
        Collections.sort(sell, cmp);

        p=0;
        for(int l = 0; l < sell.size(); l++) {
            if(sell.get(l).getOrderType().compareTo("market")==0) {
                p=matchMarket(sell.get(l),buy,p,false);
            }
            if(p>=buy.size()) break;
        }

    }

    @Scheduled(fixedRate = 2000)
    public void run() throws InterruptedException {
        List<String> pairs = new ArrayList<String>();
        List<TradingPairs> tradingPairs = new ArrayList<TradingPairs>();
        tradingPairs=dashboardService.getAllTradingPairs();

        for (TradingPairs x : tradingPairs) {
            pairs.add(x.PairSymbol());
        }

        for (int k = 0; k < pairs.size(); k++) {
            List<Order> buy = new ArrayList<Order>();
            buy = orderService.openOrders("buy", pairs.get(k));
            List<Order> sell = new ArrayList<Order>();
            sell = orderService.openOrders("sell", pairs.get(k));

            marketAlgorithm(sell,buy);

            buy = orderService.openOrders("buy", pairs.get(k));
            sell = orderService.openOrders("sell", pairs.get(k));

            Collections.sort(buy, cmp);
            Collections.sort(sell, cmp);
            //System.out.println(buy+" "+sell);

            Integer j = sell.size() - 1;
            Integer i = buy.size() - 1;

            while (i >= 0 && j >= 0) {

                while (i>=0 && ( buy.get(i).getUserId()==sell.get(j).getUserId() || !if_valid(buy.get(i) ))) {
                    i--;
                }
                while (i >= 0 && j >= 0 && (sell.get(j).getLimitPrice() > buy.get(i).getLimitPrice() || !if_valid(sell.get(j)))) {
                    j--;
                }

                if (i < 0 || j < 0) break;

                try {
                    createTrade(sell.get(j), buy.get(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sell.get(j).getOrderStatus().compareTo("filled") == 0) j--;
                if (buy.get(i).getOrderStatus().compareTo("filled") == 0) i--;

            }


        }
    }

}
