package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.services.UserServiceImpl;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.javassist.compiler.ast.Pair;

import java.sql.ResultSet;
import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    void addUser(User user);
    List<User> getAllUsers();

    User findByUserName(String userName);

    void setPNL(float pnl, String walletId);
    User findByUserId(int userId);

    void updateTotalWorth();
}


