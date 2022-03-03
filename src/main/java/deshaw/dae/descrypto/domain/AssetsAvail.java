package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AssetsAvail {
    private String asset_id;
    private String symbol;
    private String name;
    private String fiat;
    private List<AssetMarket> assetMarkets = new ArrayList<AssetMarket>();

    public List<AssetMarket> assetMarkets() {
        return assetMarkets;
    }

    public void setAssetMarkets(List<AssetMarket> assetMarkets) {
        this.assetMarkets = assetMarkets;
    }

    public String asset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String symbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String fiat() {
        return fiat;
    }

    public void setFiat(String fiat) {
        this.fiat = fiat;
    }



    /*public AssetMarket assetMarkets() {
        return assetMarkets;
    }

    public void setAssetMarkets(AssetMarket assetMarkets) {
        this.assetMarkets = assetMarkets;
    }*/
}
