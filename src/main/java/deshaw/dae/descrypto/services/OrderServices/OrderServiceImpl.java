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
        tokens=cache.TokenCache();
        newLimitOrder.setFilled(0.0);
        newLimitOrder.setOrderStatus("open");
        double total = newLimitOrder.getLimitPrice()*newLimitOrder.getAmount();
        newLimitOrder.setTotal(total);
        if(tokens!=null&&tokens.get(pair)!=null)
        newLimitOrder.setAverage(tokens.get(pair).getPrice());
        else
            return "bad request";
        newLimitOrder.setOrderType("limit");
       if(!ValidateWorth(newLimitOrder))
           return "Insufficient balance";
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
        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newMarketOrder.setAverage(tokens.get(pair).getPrice());
        else
            return "bad request";

        newMarketOrder.setFilled(0.0);
        newMarketOrder.setOrderStatus("open");
        newMarketOrder.setOrderType("market");
        double total = newMarketOrder.getAverage()*newMarketOrder.getAmount();
        newMarketOrder.setTotal(total);
        if(!ValidateWorth(newMarketOrder))
              return "Insufficient balance";
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
        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newSLMarketOrder.setAverage(tokens.get(pair).getPrice());
        else
            return "bad request";

        newSLMarketOrder.setFilled(0.0);
        newSLMarketOrder.setOrderStatus("open");
        newSLMarketOrder.setOrderType("SLmarket");
        newSLMarketOrder.setTotal(newSLMarketOrder.getAmount()*newSLMarketOrder.getTriggerPrice());
        if(!ValidateWorth(newSLMarketOrder))
            return "Insufficient balance";
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

        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newSLLimitOrder.setAverage(tokens.get(pair).getPrice());
        else
            return "bad request";
        newSLLimitOrder.setFilled(0.0);
        newSLLimitOrder.setOrderStatus("open");
        double total = newSLLimitOrder.getLimitPrice()*newSLLimitOrder.getAmount();
        newSLLimitOrder.setTotal(total);

        newSLLimitOrder.setOrderType("SLlimit");
        if(!ValidateWorth(newSLLimitOrder))
            return "Insufficient balance";
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

//    @Override
//    public List<Order> openOrders(int userId) {
//        return mapper.openOrders(userId);
//    }

    public boolean ValidateWorth(Order order){
        String coins[]=order.getOrderPair().split("-");//btc-cad
        int userId=order.getUserId();
        String pair=coins[0]+coins[1];
        try {
            double coin1 = walletservice.getAssetCoins(userId,coins[0]);
           double coin2 = walletservice.getAssetCoins(userId,coins[1]);

            if(order.getSide().equals("buy")){
                if(order.getTotal()>coin2*tokens.get(pair).getPrice())
                return false;
            }
            else{

                if(order.getAmount()>coin1)
                    return false;
            }
            return true;
        }catch (Exception e)
        {
            System.out.println("no vali "+e.getMessage());
            return false;
        }
    }}
//[ethcad, usdtusd, btcusd, btccad]