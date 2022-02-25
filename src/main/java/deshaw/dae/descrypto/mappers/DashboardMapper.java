package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.AssetsAvail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {
    List<AssetsAvail> getAllAssetsAvail();

}
