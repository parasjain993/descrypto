package deshaw.dae.descrypto.domain.Summary24hResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
