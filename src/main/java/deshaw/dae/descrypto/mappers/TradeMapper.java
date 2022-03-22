package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Trade;
import org.json.simple.JSONObject;

import java.util.List;

public interface TradeMapper {
    List<Trade> tradeHistory(JSONObject data,int userId);
    void createTrade(Trade trade);
}
