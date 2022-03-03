package deshaw.dae.descrypto.controllers.OrderControllers;

import java.io.*;
import java.util.*;

import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.services.OrderServices.DummyServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController ;
import deshaw.dae.descrypto.cache.DashboardCache;

@RestController

@RequestMapping("/")
public class DummyOrderController {
    int count=0;
    Random random=new Random();
    DashboardCache cache;
    DummyServiceImpl dummyService=new DummyServiceImpl();

    @Scheduled(fixedRate = 130000)//every 2.5 mins
    void getCurrentPrice(){
        cache=DashboardCache.getDashboardCache();
        Map<String, AssetDetails> tokens=cache.TokenCache();
        double currentPrice=tokens.get("btccad")!=null?tokens.get("btccad").getPrice():0;
        generate(currentPrice);
    }
    void generate(double currentPrice1) {

        if(currentPrice1!=0) {

            try {
                BufferedWriter out = new BufferedWriter(
                        new FileWriter("btccad_dummyOrder.txt", true));

                if (out != null) {
                    double amnt = random.nextDouble() + 0.12777;
                    if (count % 2 == 0)
                        generateDummyBuy_btccad(currentPrice1, out, amnt);
                    else
                        generateDummySell_btccad(currentPrice1, out, amnt);
                    count = count + random.nextInt(4);
                }
            } catch (IOException e) {
                System.out.println("exception occurred" + e);
            }
        }

    }
    //pair 1
    void generateDummyBuy_btccad(double price,BufferedWriter out,double amnt){
        dummyService.generateDummyBuy_btccad(price,out,amnt);

    }
    void generateDummySell_btccad(double price,BufferedWriter out,double amnt){
        dummyService.generateDummySell_btccad(price,out,amnt);
    }


}
