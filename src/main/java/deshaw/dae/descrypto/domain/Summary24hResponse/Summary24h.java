package deshaw.dae.descrypto.domain.Summary24hResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

class PriceChange{
    private double percentage;
    private double absolute;

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getAbsolute() {
        return absolute;
    }

    public void setAbsolute(double absolute) {
        this.absolute = absolute;
    }


}
class Price24h{
    private double last;
    private double high;
    private double low;

    @JsonProperty("change")
    private PriceChange priceChange;

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }



}

class Result24h{
    public Price24h getPrice24h() {
        return price24h;
    }

    public void setPrice24h(Price24h price24h) {
        this.price24h = price24h;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolumeQuote() {
        return volumeQuote;
    }

    public void setVolumeQuote(double volumeQuote) {
        this.volumeQuote = volumeQuote;
    }

    @JsonProperty("price")
    private Price24h price24h;
    private double volume;
    private double volumeQuote;
}
public class Summary24h {
    @JsonProperty("result")
    private  Result24h result24h;

    public Result24h getResult24h() {
        return result24h;
    }

    public void setResult24h(Result24h result24h) {
        this.result24h = result24h;
    }
}
