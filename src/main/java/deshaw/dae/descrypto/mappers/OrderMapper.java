package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int placeLimitOrder(Order newLimitOrder);
    int placeMarketOrder(Order newMarketOrder);
}
