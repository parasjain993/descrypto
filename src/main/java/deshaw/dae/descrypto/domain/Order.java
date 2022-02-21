package deshaw.dae.descrypto.domain;

public class Order {

    private int userId;//which user has placed
    private String orderId;//btcusdt
    private double amount;//what amount of your usdt wallet eg:10% 25%...
    private double limitPrice;//trade will start at this price for limit and current for market
    private String orderType;//buy or sell

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
