package deshaw.dae.descrypto.domain.PriceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class result {
    @JsonProperty(value = "price")
    double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
