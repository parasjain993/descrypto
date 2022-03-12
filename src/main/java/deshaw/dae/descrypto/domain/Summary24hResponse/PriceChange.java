package deshaw.dae.descrypto.domain.Summary24hResponse;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PriceChange {
    @JsonProperty("percentage")
    private double percentage;
    @JsonProperty("absolute")
    private double absolute;
    public double percentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double absolute() {
        return absolute;
    }

    public void setAbsolute(double absolute) {
        this.absolute = absolute;
    }


}
