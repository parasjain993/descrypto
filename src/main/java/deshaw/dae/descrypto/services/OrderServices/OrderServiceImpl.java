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
    @Autowired
    private WalletService walletservice;

    private DashboardCache cache=DashboardCache.getDashboardCache();
    Map<String, AssetDetails> tokens;
    public int placeLimitOrder(Order newLimitOrder){
        if(newLimitOrder.getLimitPrice()==0.0)
            return -1;
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
            newLimitOrder.setAverage(3212.2);
        newLimitOrder.setOrderType("limit");
       if(!ValidateWorth(newLimitOrder))
           return -1;

       return mapper.placeLimitOrder(newLimitOrder);

    }
    public int placeMarketOrder(Order newMarketOrder){
        String coins[]=newMarketOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newMarketOrder.setAverage(tokens.get(pair).getPrice());
        else
            newMarketOrder.setAverage(3212.2);

        newMarketOrder.setFilled(0.0);
        newMarketOrder.setOrderStatus("open");
        newMarketOrder.setOrderType("market");
        double total = newMarketOrder.getAverage()*newMarketOrder.getAmount();
        newMarketOrder.setTotal(total);
        if(!ValidateWorth(newMarketOrder))
           return -1;
        int status=mapper.placeMarketOrder(newMarketOrder);// save
        return status;
    }

    public int placeStopLossMarketOrder(Order newSLMarketOrder){
        if(newSLMarketOrder.getTriggerPrice()==0.0){
            return -1;
        }
        String coins[]=newSLMarketOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];
        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newSLMarketOrder.setAverage(tokens.get(pair).getPrice());
        else
            newSLMarketOrder.setAverage(3212.2);

        newSLMarketOrder.setFilled(0.0);
        newSLMarketOrder.setOrderStatus("open");
        newSLMarketOrder.setOrderType("SLmarket");
        newSLMarketOrder.setTotal(newSLMarketOrder.getAmount()*newSLMarketOrder.getTriggerPrice());
        if(!ValidateWorth(newSLMarketOrder))
            return -1;
        int status=mapper.placeStopLossMarketOrder(newSLMarketOrder);
        System.out.println(status);
        return status;
    }
    public int placeStopLossLimitOrder(Order newSLLimitOrder){
        if(newSLLimitOrder.getTriggerPrice()==0.0||newSLLimitOrder.getLimitPrice()==0.0){
            return -1;
        }
        String coins[]=newSLLimitOrder.getOrderPair().split("-");
        String pair=coins[0]+coins[1];

        tokens=cache.TokenCache();
        if(tokens!=null&&tokens.get(pair)!=null)
            newSLLimitOrder.setAverage(tokens.get(pair).getPrice());
        else
            newSLLimitOrder.setAverage(3212.2);
        newSLLimitOrder.setFilled(0.0);
        newSLLimitOrder.setOrderStatus("open");
        double total = newSLLimitOrder.getLimitPrice()*newSLLimitOrder.getAmount();
        newSLLimitOrder.setTotal(total);

        newSLLimitOrder.setOrderType("SLlimit");
        if(!ValidateWorth(newSLLimitOrder))
            return -1;
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
        String coins[]=order.getOrderPair().split("-");//btc-cad
        int userId=order.getUserId();
        String pair=coins[0]+coins[1];
        try {
            int coin1 = (int)walletservice.getAssetCoins(userId,coins[0]);
            int coin2 = (int) walletservice.getAssetCoins(userId,coins[1]);

            if(order.getSide().equals("buy")){
                if(order.getTotal()>coin2*tokens.get(pair).getPrice())
                return false;
            }
            else{

                if(order.getAmount()>coin1*tokens.get(pair).getPrice())
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