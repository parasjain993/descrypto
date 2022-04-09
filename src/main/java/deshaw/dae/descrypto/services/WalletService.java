package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.Wallet;

import java.util.HashMap;

public interface WalletService {


    HashMap<String, Float> findAssetsForUser(int userId);

    float totalWorthCalc(int userId);

    float getAssetCoins(int userId, String assetName);

    void withdrawFund(int userId, String assetName, float withdrawalAmount);

    void addFund(int userId, String assetName, float amountToBeAdded);

    Wallet findWallet(int userId, String assetName);

    void addNewWallet(int userId, String assetName, float amountToBeAdded);

    void updateTotalWorth();

    void removeAsset(int userId, String assetName);
}
