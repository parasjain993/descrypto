package deshaw.dae.descrypto.domain;

import java.sql.Timestamp;

public class Trade {
    private int tradeId;// primary key
    private double filled;// what amount of total amount was filled
    private double total;//total spent upon buying/ total gained upon sell
    private double price;
    private int buy_Id;// buy order id
    private int sell_Id;//sell order id
    private Timestamp timestamp;// when trade was executed

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int TradeId) {
        this.tradeId = TradeId;
    }

    public double getFilled() {
        return filled;
    }

    public void setFilled(double Filled) {
        this.filled = Filled;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = Price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double Total) {
        this.total = Total;
    }

    public int getBuy_Id() { return buy_Id; }

    public void setBuy_Id(int Buy_Id) { this.buy_Id = Buy_Id; }

    public int getSell_Id() { return sell_Id; }

    public void setSell_Id(int Sell_Id) { this.sell_Id = Sell_Id; }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp time) { this.timestamp = time; }

}
