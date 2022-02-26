package deshaw.dae.descrypto.domain;

import deshaw.dae.descrypto.domain.PriceResponse.PriceResponse;
import deshaw.dae.descrypto.domain.Summary24hResponse.Summary24h;

public class AssetDetails {
    private String tokenID;
    private double price;
    //private double marketCap;
    private Summary24h summary24h;

    public AssetDetails(String tokenID) {
        this.tokenID = tokenID;
    }

    public AssetDetails(String coinId, Class<? extends PriceResponse> aClass, Summary24h summary24h) {
    }

    public Summary24h getSummary24h() {
        return summary24h;
    }

    public void setSummary24h(Summary24h summary24h) {
        this.summary24h = summary24h;
    }

    public AssetDetails(String tokenID, double price, Summary24h summary24h) {
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
