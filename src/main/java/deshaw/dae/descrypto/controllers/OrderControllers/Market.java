package deshaw.dae.descrypto.controllers.OrderControllers;

import deshaw.dae.descrypto.DescryptoApplication;
import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.AssetDetails;
import java.util.*;


public class Market extends Thread{
    public List<HashMap<String,String>> orders=new ArrayList<>();

    DashboardCache cache=DashboardCache.getDashboardCache();
    Random random=new Random();
    int count=0;
    public void initiate(String pair){
        DescryptoApplication.markets.put(pair,this);
        HashMap<String,String> map=new HashMap<>();
        map.put("Pair",pair);
        this.orders.add(map);
        this.start();
    }
    public void run(){
        try {
            int x=1000;
            HashMap<String,AssetDetails> tokens;
            while (x-->0) {

                String p = this.orders.get(0).get("Pair");
                double price = 2231.2;
                HashMap<String, String> map = new HashMap<>();
                Thread.sleep(5000);
                try{
                     tokens = (HashMap<String, AssetDetails>) cache.TokenCache();
                     price = tokens.get(p).getPrice();
                 }catch (Exception e){

                }

                    double amnt = (10 + (10000 - 10) * random.nextDouble())/price;
                    double variance = random.nextDouble() + 10;
                    double newLimitPrice;
                    String side = "Buy";
                    if (count % 2 == 0)
                        side = "Sell";
                    if (side.equals("Buy"))
                        newLimitPrice = (price - (variance * price) / 100);
                    else
                        newLimitPrice = (price + (variance * price) / 100);
                    count = count + random.nextInt(5);
                    map.put("Side", side);
                    map.put("Amount", amnt + "");
                    map.put("limitPrice", newLimitPrice + "");
                    map.put("Average", price + "");
                    if(x<998)
                    this.orders.add(map);
                  //   System.out.println(this.orders.size()+" "+ currentThread().getId());
                    Thread.sleep(6000);
                }



        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static List<HashMap<String,String>> getOrders(String name){
        return DescryptoApplication.markets.get(name).orders;
    }


}

