package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    void addUser(User user);
    User findByFullUsername(String username);
}


