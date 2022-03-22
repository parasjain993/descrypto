package deshaw.dae.descrypto.mappers;
import deshaw.dae.descrypto.domain.OrderbookEntry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderbookMapper {
    List<OrderbookEntry> allUserOrdersbyPair(String orderPair);
    List<OrderbookEntry> allUserBuyOrdersbyPair(String orderPair);
    List<OrderbookEntry> allUserSellOrdersbyPair(String orderPair);
}
