package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Trade;

import java.util.List;

public interface TradeMapper {
    List<Trade> tradeHistory(int userId);
    List<Trade> partialTrade(int orderId);
}
