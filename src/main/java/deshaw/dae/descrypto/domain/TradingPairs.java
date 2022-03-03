package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TradingPairs {
    private Integer PairID;
    private String PairSymbol;
    private String Asset1ID;
    private String Asset2ID;

    public Integer PairID() {
        return PairID;
    }

    public void setPairID(Integer pairID) {
        PairID = pairID;
    }

    public String PairSymbol() {
        return PairSymbol;
    }

    public void setPairSymbol(String pairSymbol) {
        PairSymbol = pairSymbol;
    }

    public String Asset1ID() {
        return Asset1ID;
    }

    public void setAsset1ID(String asset1ID) {
        Asset1ID = asset1ID;
    }

    public String Asset2ID() {
        return Asset2ID;
    }

    public void setAsseet2ID(String asset2ID) {
        Asset2ID = asset2ID;
    }
}
