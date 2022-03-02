package deshaw.dae.descrypto.controllers.OrderControllers;

import java.io.*;
import java.util.Random;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import deshaw.dae.descrypto.domain.AssetDetails;

@RestController

@RequestMapping("/")
public class DummyOrderController {
    int count=0;
    Random random=new Random();
    //AssetDetails details=new AssetDetails();
    @Scheduled(fixedRate = 30000 )
    void generate() {

          double currentPrice1=34.45;
          double currentPrice2=45.11;

        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("dummy_order.txt", true));

            if(out!=null){
                if(count%2==0)
                generateDummyBuy_btcusdt(currentPrice1,out);
                else
                generateDummySell_btcusdt(currentPrice1,out);
                count=count+random.nextInt(4);
            }
        }
        catch (IOException e) {
            System.out.println("exception occurred" + e);
        }


    }
    //pair 1
    void generateDummyBuy_btcusdt(double price,BufferedWriter out){
        double variance=random.nextDouble()+4.0;
        double newLimitPrice=price-variance;
        String str="Buy : "+newLimitPrice+"\n";
        try {
            out.write(str);
            out.close();
        }catch (Exception e){
            System.out.println("could not write");
        }

    }
    void generateDummySell_btcusdt(double price,BufferedWriter out){
        double variance=random.nextDouble()+4.0;
        double newLimitPrice=price+variance;
        String str="Sell : "+newLimitPrice+"\n";
        try {
            out.write(str);
            out.close();
        }catch (Exception e){
            System.out.println("could not write");
        }
    }


}
