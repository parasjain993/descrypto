package deshaw.dae.descrypto.services.TradeServices;

import deshaw.dae.descrypto.domain.Trade;
import org.json.simple.JSONObject;

import java.util.List;

public interface TradeService {

    List<Trade> tradeHistory(JSONObject data,int userId);
    void createTrade(Trade trade);
}
