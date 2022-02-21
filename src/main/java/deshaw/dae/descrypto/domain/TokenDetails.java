package deshaw.dae.descrypto.domain;

public class TokenDetails {
    private String tokenID;
    private double price;
    //private double marketCap;
    private double volume;
    private Summary24h summary24h;

    public TokenDetails(String tokenID) {
        this.tokenID = tokenID;
    }

    public Summary24h getSummary24h() {
        return summary24h;
    }

    public void setSummary24h(Summary24h summary24h) {
        this.summary24h = summary24h;
    }

    public TokenDetails(String tokenID, double price, Summary24h summary24h) {
        this.tokenID = tokenID;
        this.price = price;
        this.summary24h = summary24h;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
