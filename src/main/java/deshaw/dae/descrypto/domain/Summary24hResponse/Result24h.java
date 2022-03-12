package deshaw.dae.descrypto.domain.Summary24hResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result24h {
    @JsonProperty("price")
    private Price24h price24h;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("volumeQuote")
    private double volumeQuote;

    public Price24h price24h() {
        return price24h;
    }

    public void setPrice24h(Price24h price24h) {
        this.price24h = price24h;
    }

    public double volume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double volumeQuote() {
        return volumeQuote;
    }

    public void setVolumeQuote(double volumeQuote) {
        this.volumeQuote = volumeQuote;
    }
}
