package deshaw.dae.descrypto.services.OrderServices;

import java.io.BufferedWriter;

public interface DummyService {
    void generateDummyBuy_btccad(double price, BufferedWriter out, double amnt);
    void generateDummySell_btccad(double price,BufferedWriter out,double amnt);
}
