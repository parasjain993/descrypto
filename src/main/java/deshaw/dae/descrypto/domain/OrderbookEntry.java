package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderbookEntry {


    private double amount;//amount of token to buy/sell
    private double limitPrice;//trade will start at this price for limit and current for market

    public OrderbookEntry(double amount, double limitPrice) {
        this.amount = amount;
        this.limitPrice = limitPrice;
    }

    public double amount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double limitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }
}
