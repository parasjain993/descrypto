package deshaw.dae.descrypto.domain.Summary24hResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price24h {
    @JsonProperty("last")
    private double last;
    @JsonProperty("high")
    private double high;
    @JsonProperty("low")
    private double low;
    @JsonProperty("change")
    private PriceChange priceChange;

    public double last() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double high() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double low() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }


}
