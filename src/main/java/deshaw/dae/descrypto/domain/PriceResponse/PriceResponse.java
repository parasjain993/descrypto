package deshaw.dae.descrypto.domain.PriceResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceResponse {
    @JsonProperty("result")
    private result resultPrice;

    public result getResultPrice() {
        return resultPrice;
    }

    public void setResultPrice(result resultPrice) {
        this.resultPrice = resultPrice;
    }

    public double getPrice() {
        return resultPrice.getPrice();
    }
}
