package deshaw.dae.descrypto.services;

import java.util.HashMap;

public interface WalletService {


    HashMap<String, Integer> findAssetsForUser(String walletId);

    float totalWorthCalc(String walletId);

    int getAssetCoins(String walletId, String assetName);

    void withdrawFund(String walletId, String assetName, int withdrawalAmount);

    void addFund(String walletId, String assetName, int amountToBeAdded);
}
