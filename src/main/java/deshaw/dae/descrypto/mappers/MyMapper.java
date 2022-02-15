package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyMapper {

    List<User> findAllUsers();
}
