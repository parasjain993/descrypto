package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.domain.Trade;
import deshaw.dae.descrypto.domain.TradingPairs;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.OrderServices.OrderService;
import deshaw.dae.descrypto.services.TradeServices.TradeService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("")
public class MatchingAlgo extends Thread {
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

    private static int tradeId=0;

     Comparator cmp=new Comparator() {

        @Override
        public int compare(Object a, Object b) {
            Order o1 = (Order) a;
            Order o2 = (Order) b;

//            if(o1.getLimitPrice()==o2.getLimitPrice()) return o1.getTimestamp().compareTo(o2.getTimestamp());

            return Double.compare(o2.getLimitPrice(),o1.getLimitPrice());
        }
    };

     void calAverageAndTotal(Order order){

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String userName = auth.getName();
         int userId=userService.findByUserName(userName).getUserId();

         JSONObject data=new JSONObject();
         //data.put("userId",order.getUserId());
         data.put("orderPair",null); data.put("timestamp1",null); data.put("timestamp2",null);
         data.put("orderId",order.getOrderId());
         List<Trade> trades=tradeService.tradeHistory(data,userId);
         double average=0,total=0;
         int count=trades.size();

         for(int i=0;i<trades.size();i++){
             average+=trades.get(i).getPrice();
             total+=trades.get(i).getTotal();
         }

         average=average/count;

         order.setAverage(average);
         order.setTotal(total);

         System.out.println(average+"\n"+data+"\n"+trades+"\n"+count+" "+total+"\ncalAverage:\n");

     }

     String createTrade(Order S, Order B, Integer i, Integer j) throws InterruptedException {

        Order s=S,b=B;

        System.out.println(s+"\n"+b+"\nafter:\n");

         Trade trade=new Trade();
         trade.setBuy_Id(b.getOrderId()); trade.setSell_Id(s.getOrderId());

        if((s.getAmount()-s.getFilled()) >= (b.getAmount()-b.getFilled())) {
            Order temp=s;
            s=b;
            b=temp;
            int p=j;
            j=i;
            i=p;
        }
         System.out.println(s+"\n"+b+"\nafter:\n");

        trade.setFilled((s.getAmount()-s.getFilled()));
        trade.setPrice((s.getLimitPrice()+b.getLimitPrice())/2.00);
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


        return s.getOrderId()+"\n"+b.getOrderId()+"\n"+"\n\n"+s.getAmount()+"\n"+b.getAmount()+"\n"+"\n\n"+s.getOrderStatus()+"\n"+b.getOrderStatus()+"\n"+"\n\n";

    }

    boolean if_valid(Order order){

          if(walletService.totalWorthCalc(order.getUserId()) >= order.getLimitPrice() ){
              return true;
         }
          return false;

    }

    //@PostMapping("/match")
    public void run() {

         while(true) {

                 List<String> pairs = new ArrayList<String>();
                 List<TradingPairs> tradingPairs = dashboardService.getAllTradingPairs();

                 for (TradingPairs x : tradingPairs) {
                     pairs.add(x.PairSymbol());
                 }

                 String ans = "";
                 for (int k = 0; k < pairs.size(); k++) {
                     List<Order> buy = new ArrayList<Order>();
                     buy = orderService.openOrders("buy", pairs.get(k));
                     List<Order> sell = new ArrayList<Order>();
                     sell = orderService.openOrders("sell", pairs.get(k));

                     // sort
                     Collections.sort(buy, cmp);
                     Collections.sort(sell, cmp);

                     Integer j = sell.size() - 1;
                     Integer i = buy.size() - 1;

                     while (i >= 0 && j >= 0) {
                         System.out.println(i + " " + j + " check0\n");

                         while (!if_valid(buy.get(i))) {
                             i--;
                         }
                         while (i >= 0 && j >= 0 && (sell.get(j).getLimitPrice() > buy.get(i).getLimitPrice() || !if_valid(sell.get(j)))) {
                             j--;
                         }

                         if (i < 0 || j < 0) break;

                         // System.out.println(i+" "+j+" check1\n");
                         try {
                             ans += createTrade(sell.get(j), buy.get(i), i, j);
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                         if (sell.get(j).getOrderStatus().compareTo("filled") == 0) j--;
                         if (buy.get(i).getOrderStatus().compareTo("filled") == 0) i--;

                         //System.out.println(i+" "+j+" check2\n");

                     }


                 }
                 // return ans;
             System.out.println(currentThread().getId());
         }
    }

}

