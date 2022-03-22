package deshaw.dae.descrypto.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Orderbook {
    private List<OrderbookEntry> Asks;
    private List<OrderbookEntry> Bids;
    public static long seqNumber=1;

    public List<OrderbookEntry> Asks() {
        return Asks;
    }

    public void setAsks(List<OrderbookEntry> asks) {
        Asks = asks;
    }

    public List<OrderbookEntry> Bids() {
        return Bids;
    }

    public void setBids(List<OrderbookEntry> bids) {
        Bids = bids;
    }

    public long seqNumber() {
        return seqNumber;
    }

}
