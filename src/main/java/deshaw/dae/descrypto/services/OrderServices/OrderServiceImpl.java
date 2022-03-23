package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.OrderMapper;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper mapper;
    private UserService userService;
    @Autowired
    private WalletService walletservice;

    private DashboardCache cache=DashboardCache.getDashboardCache();
    Map<String, AssetDetails> tokens;
    public String placeLimitOrder(Order newLimitOrder){
        if(newLimitOrder.getLimitPrice()==0.0)
            return "Specify limit price";
       String coins[]=newLimitOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
        newLimitOrder.setFilled(0.0);
        newLimitOrder.setOrderStatus("open");
        double total = newLimitOrder.getLimitPrice()*newLimitOrder.getAmount();
        newLimitOrder.setTotal(0.0);
        newLimitOrder.setAverage(0.0);
        newLimitOrder.setOrderType("limit");
       if(newLimitOrder.getTradingType().equals("spot")){
       if(!ValidateWorth(newLimitOrder,total))
           return "Insufficient balance";}
       newLimitOrder.setOrderPair(pair);
       int status=mapper.placeOrder(newLimitOrder);
       if(status==1)
           return "ok";
       else
           return "failed to save";

    }
    public String placeMarketOrder(Order newMarketOrder){
        String coins[]=newMarketOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
        newMarketOrder.setAverage(0.0);
        newMarketOrder.setFilled(0.0);
        newMarketOrder.setOrderStatus("open");
        newMarketOrder.setOrderType("market");
        tokens=cache.TokenCache();
        double total = tokens.get(pair).getPrice()*newMarketOrder.getAmount();
        newMarketOrder.setTotal(0.0);
        if(newMarketOrder.getTradingType().equals("spot")){
        if(!ValidateWorth(newMarketOrder,total))
              return "Insufficient balance";}
        newMarketOrder.setOrderPair(pair);
        int status=mapper.placeOrder(newMarketOrder);
        if(status==1)
            return "ok";
        else
            return "failed to save";

     }

    public String placeStopLossMarketOrder(Order newSLMarketOrder){
        if(newSLMarketOrder.getTriggerPrice()==0.0){
            return "Specify trigger price";
        }
        String coins[]=newSLMarketOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
            newSLMarketOrder.setAverage(tokens.get(pair).getPrice());
        newSLMarketOrder.setFilled(0.0);
        newSLMarketOrder.setOrderStatus("open");
        newSLMarketOrder.setOrderType("SLmarket");
        newSLMarketOrder.setTotal(0.0);
        double total=newSLMarketOrder.getAmount()*newSLMarketOrder.getTriggerPrice();
        if(newSLMarketOrder.getTradingType().equals("spot")){
        if(!ValidateWorth(newSLMarketOrder,total))
            return "Insufficient balance";}
        newSLMarketOrder.setOrderPair(pair);
        int status=mapper.placeOrder(newSLMarketOrder);
        if(status==1)
            return "ok";
        else
            return "failed to save";
    }
    public String placeStopLossLimitOrder(Order newSLLimitOrder){
        if(newSLLimitOrder.getTriggerPrice()==0.0||newSLLimitOrder.getLimitPrice()==0.0){
            return "Specify trigger price and limit price";
        }
        String coins[]=newSLLimitOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
        newSLLimitOrder.setAverage(0.0);
        newSLLimitOrder.setFilled(0.0);
        newSLLimitOrder.setOrderStatus("open");
        double total = newSLLimitOrder.getLimitPrice()*newSLLimitOrder.getAmount();
        newSLLimitOrder.setTotal(0.0);
        newSLLimitOrder.setOrderType("SLlimit");
        if(newSLLimitOrder.getTradingType().equals("spot")){
        if(!ValidateWorth(newSLLimitOrder,total))
            return "Insufficient balance";}
        newSLLimitOrder.setOrderPair(pair);
        int status=mapper.placeOrder(newSLLimitOrder);
        if(status==1)
            return "ok";
        else
            return "failed to save";
    }
    @Override
    public List<Order> orderHistory(JSONObject data,int userId) {
        return mapper.orderHistory(data,userId);
    }

    @Override
    public List<Order> openOrders(String side,String pair) {
        return mapper.openOrders(side,pair);
    }

    @Override
    public void updateOrder(Order order) { mapper.updateOrder(order); }

    public void cancelOrder(int orderId) {
        mapper.cancelOrder(orderId);
    }



    public boolean ValidateWorth(Order order,double total){

        String coins[]=order.getOrderPair().split("-");//btc-cad
        int userId=order.getUserId();
        String pair=coins[0]+coins[1];
        try {
            tokens=cache.TokenCache();
            if(tokens.get(pair)!=null){
            double coin1 = walletservice.getAssetCoins(userId,coins[0]);
            double coin2 = walletservice.getAssetCoins(userId,coins[1]);
        //    System.out.println(coin1+" "+coin2);
            if(order.getSide().equals("buy")){
                if(total>coin2*tokens.get(pair).getPrice())
                return false;
            }
            else{

                if(order.getAmount()>coin1)
                    return false;
            }}
            return true;
        }catch (Exception e)
        {
            System.out.println("no vali "+e.getMessage());
            return false;
        }
    }}
//[ethcad, usdtusd, btcusd, btccad]