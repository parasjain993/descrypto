package deshaw.dae.descrypto.mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortfolioWorth {
    void updatePortfolioWorth(float totalWorth, int userId);
}
