package deshaw.dae.descrypto.services.TradeServices;

import deshaw.dae.descrypto.domain.Trade;
import deshaw.dae.descrypto.mappers.TradeMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper mapper;

    public List<Trade> tradeHistory(JSONObject data,int userId) {
        return mapper.tradeHistory(data,userId);
    }

    public void createTrade(Trade trade) { mapper.createTrade(trade); }
}
