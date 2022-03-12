package deshaw.dae.descrypto.domain;

public class Trade {
  private int tradeId;// primary key
    private double filled;// what amount of total amount was filled
    private double total;//total spent upon buying/ total gained upon sell
    private double price;
    private Order buy_Id;// buy order id
    private Order sell_Id;//sell order id

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

    public Order getBuy_Id() { return buy_Id; }

    public void setBuy_Id(Order Buy_Id) { this.buy_Id = Buy_Id; }

    public Order getSell_Id() { return sell_Id; }

    public void setSell_Id_Id(Order Sell_Id) { this.sell_Id = Sell_Id; }

}
