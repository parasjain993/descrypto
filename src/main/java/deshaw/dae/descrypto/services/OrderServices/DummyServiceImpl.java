package deshaw.dae.descrypto.services.OrderServices;

import java.io.BufferedWriter;
import java.util.Random;

public class DummyServiceImpl implements DummyService{
    Random random=new Random();
    public void generateDummyBuy_btccad(double price, BufferedWriter out, double amnt){
        double variance=random.nextDouble()+4.0;
        double newLimitPrice=price-variance;
        String str="\nBuy\t"+amnt+"\t"+newLimitPrice;
        try {
            out.write(str);
            out.close();
        }catch (Exception e){
            System.out.println("could not write");
        }

    }
    public void generateDummySell_btccad(double price, BufferedWriter out, double amnt){
        double variance=random.nextDouble()+4.0;
        double newLimitPrice=price+variance;
        String str="\nSell\t"+amnt+"\t"+newLimitPrice;
        try {
            out.write(str);
            out.close();
        }catch (Exception e){
            System.out.println("could not write");
        }
    }
}
