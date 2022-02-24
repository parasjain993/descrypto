package deshaw.dae.descrypto.domain;

public class Order {

    private int  orderId;//primary key
    private int userId;//which user has placed
    private String orderPair;//btcusdt
    private double amount;//what amount of your usdt wallet eg:10% 25%...
    private double limitPrice;//trade will start at this price for limit and current for market
    private String orderType;//buy or sell
    private String orderStatus;//open, partially-filled, filled, cancelled

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

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

    public String getOrderPair() {
        return orderPair;
    }

    public void setOrderPair(String orderPair) {
        this.orderPair = orderPair;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
