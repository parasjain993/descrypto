package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Wallet;
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
    User findByFullUsername(String username);

    List<Wallet> findAssetsForUser(String walletId);

    void setTotalWorth(String walletId, float total_worth);
    int getAssetCoins(String walletId, String assetName);
    List<User> allUsers();

    void addFund(String walletId, String assetName, int amountToBeAdded);

    void withdrawFund(String walletId, String assetName, int withdrawalAmount);
    List<User> getAllUsers();
    void setPNL(float pnl, String walletId);
}


