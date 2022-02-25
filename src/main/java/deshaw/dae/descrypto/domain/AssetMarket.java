package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AssetMarket {
    private int id;
    private int asset_id;
    private String exchange;
    private String pair;
    private String type;

    public int id() {
        return id;
    }

    public int asset_id() {
        return asset_id;
    }

    public String exchange() {
        return exchange;
    }

    public String pair() {
        return pair;
    }

    public String type() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AssetMarket(int id, int asset_id, String exchange, String pair, String type) {
        this.id = id;
        this.asset_id = asset_id;
        this.exchange = exchange;
        this.pair = pair;
        this.type = type;
    }
}
