package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AssetsAvail {
    private Integer asset_id;
    private String symbol;
    private String name;
    private Integer fiat;
    private List<AssetMarket> assetMarkets = new ArrayList<AssetMarket>();

    public List<AssetMarket> assetMarkets() {
        return assetMarkets;
    }

    public void setAssetMarkets(List<AssetMarket> assetMarkets) {
        this.assetMarkets = assetMarkets;
    }

    public Integer asset_id() {
        return asset_id;
    }

    public void setAsset_id(Integer asset_id) {
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

    public Integer fiat() {
        return fiat;
    }

    public void setFiat(Integer fiat) {
        this.fiat = fiat;
    }



    /*public AssetMarket assetMarkets() {
        return assetMarkets;
    }

    public void setAssetMarkets(AssetMarket assetMarkets) {
        this.assetMarkets = assetMarkets;
    }*/
}
