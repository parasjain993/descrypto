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
        this.count=0;
        this.start();
    }
    public void run(){
        try {
            int x=50;
            HashMap<String,AssetDetails> tokens;
            while (x-->0){
                double amnt = random.nextDouble() + 0.12777;
                String p=this.orders.get(0).get("Pair");
                double price=5662.1;
                double temp=-1;
                HashMap<String,String> map=new HashMap<>();
                tokens= (HashMap<String, AssetDetails>) cache.TokenCache();

                if(tokens!=null) {
                    try {
                        temp=tokens.get(p).getPrice();
                    }catch (Exception e)
                    {
                    }
                }
                Thread.sleep(5000);
                if(temp!=-1)
                    price=temp;
                double variance=random.nextDouble()+4.0;
                double newLimitPrice=price-variance;
                String side="Buy";
                if(count%2==0)
                    side="Sell";
                count=count+random.nextInt(5);
                map.put("Side",side);
                map.put("Amount",amnt+"");
                map.put("limitPrice",newLimitPrice+"");
                map.put("Average",price+"");
               this.orders.add(map);
              //  System.out.println(this.orders.size()+" "+ currentThread().getId());
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

