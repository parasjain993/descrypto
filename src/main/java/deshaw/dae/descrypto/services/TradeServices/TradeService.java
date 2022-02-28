package deshaw.dae.descrypto.services.TradeServices;

import deshaw.dae.descrypto.domain.Trade;

import java.util.List;

public interface TradeService {

    List<Trade> tradeHistory(int userId);
    List<Trade> partialTrade(int orderId);
}
