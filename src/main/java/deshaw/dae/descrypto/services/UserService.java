package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void withdrawFund(String walletId, String assetName, int withdrawalAmount);

    void addUser(User user);
    User findByFullUsername(String username);

    AssetDetails getCoinDetailsByID(String CoinId);

    List<AssetDetails> getCoinDetails(List<String> CoinIds);

    Map<String, Integer> findAssetsForUser(String walletId);

    float totalWorthCalc(String walletId);
    int getAssetCoins(String walletId, String assetName);

    void addFund(String walletId, String assetName, int amountToBeAdded);


    List<User> getAllUsers();

    void setPNL(float v, String walletId);
}

