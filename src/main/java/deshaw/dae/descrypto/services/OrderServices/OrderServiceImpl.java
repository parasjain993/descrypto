package deshaw.dae.descrypto.services.OrderServices;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.OrderMapper;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper mapper;
    private UserService userService;
    private WalletService walletservice;
    private DashboardCache cache=DashboardCache.getDashboardCache();
    Map<String, AssetDetails> tokens;
    public int placeLimitOrder(Order newLimitOrder){
        if(newLimitOrder.getLimitPrice()==0.0)
            return -1;

        tokens=cache.TokenCache();
        newLimitOrder.setFilled(0.0);
        newLimitOrder.setOrderStatus("open");
        double total = newLimitOrder.getLimitPrice()*newLimitOrder.getAmount();
        newLimitOrder.setTotal(total);
        newLimitOrder.setAverage(tokens.get(newLimitOrder.getOrderPair()).getPrice());
        newLimitOrder.setOrderType("limit");
       // if(!ValidateWorth(newLimitOrder))
         //  return -1;
        return mapper.placeLimitOrder(newLimitOrder);

    }
    public int placeMarketOrder(Order newMarketOrder){

        tokens=cache.TokenCache();
        newMarketOrder.setAverage(tokens.get(newMarketOrder.getOrderPair()).getPrice());
        newMarketOrder.setFilled(0.0);
        newMarketOrder.setOrderStatus("open");
        newMarketOrder.setOrderType("market");
        double total = newMarketOrder.getAverage()*newMarketOrder.getAmount();
        newMarketOrder.setTotal(total);
        //if(!ValidateWorth(newMarketOrder))
          //  return -1;
        int status=mapper.placeMarketOrder(newMarketOrder);// save
        return status;
    }

    public int placeStopLossMarketOrder(Order newSLMarketOrder){
        if(newSLMarketOrder.getTriggerPrice()==0.0){
            return -1;
        }

        tokens=cache.TokenCache();
        newSLMarketOrder.setAverage(tokens.get(newSLMarketOrder.getOrderPair()).getPrice());
        newSLMarketOrder.setFilled(0.0);
        newSLMarketOrder.setOrderStatus("open");
        newSLMarketOrder.setOrderType("SLmarket");
        newSLMarketOrder.setTotal(newSLMarketOrder.getAmount()*newSLMarketOrder.getTriggerPrice());
        //if(!ValidateWorth(newSLMarketOrder))
          //  return -1;
        int status=mapper.placeStopLossMarketOrder(newSLMarketOrder);
        return status;
    }
    public int placeStopLossLimitOrder(Order newSLLimitOrder){
        if(newSLLimitOrder.getTriggerPrice()==0.0||newSLLimitOrder.getLimitPrice()==0.0){
            return -1;
        }

        tokens=cache.TokenCache();
        newSLLimitOrder.setFilled(0.0);
        newSLLimitOrder.setOrderStatus("open");
        double total = newSLLimitOrder.getLimitPrice()*newSLLimitOrder.getAmount();
        newSLLimitOrder.setTotal(total);
        newSLLimitOrder.setAverage(tokens.get(newSLLimitOrder.getOrderPair()).getPrice());
        newSLLimitOrder.setOrderType("SLlimit");
        //if(!ValidateWorth(newSLLimitOrder))
          //  return -1;
        int status=mapper.placeStopLossLimitOrder(newSLLimitOrder);
        return status;
    }


    @Override
    public List<Order> orderHistory(int userId) {
        return mapper.orderHistory(userId);
    }

    @Override
    public List<Order> openOrders(int userId) {
        return mapper.openOrders(userId);
    }

    public void cancelOrder(int orderId) {
        mapper.cancelOrder(orderId);
    }
    public boolean ValidateWorth(Order order){
        String coins[]=order.getOrderPair().split("-");//btc-usdt
        String walletId=userService.getWalletId(order.getUserId());
        //sell btc in exchange of usdt
        int coin1=walletservice.getAssetCoins(walletId,coins[0]);
        //buying btc with usdt
        int coin2 = walletservice.getAssetCoins(walletId,coins[1]);
        if(order.getSide().equals("buy")){
            if(order.getTotal()>coin2*tokens.get(order.getOrderPair()).getPrice());
                return false;
        }
        else{
            if(order.getAmount()>coin1*tokens.get(order.getOrderPair()).getPrice())
                return false;
        }
        return true;
    }

}
//[ethcad, usdtusd, btcusd, btccad]