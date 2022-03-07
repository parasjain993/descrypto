package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.Wallet;

import java.util.HashMap;

public interface WalletService {


    HashMap<String, Integer> findAssetsForUser(int userId);

    float totalWorthCalc(int userId);

    int getAssetCoins(int userId, String assetName);

    void withdrawFund(int userId, String assetName, int withdrawalAmount);

    void addFund(int userId, String assetName, int amountToBeAdded);

    Wallet findWallet(int userId, String assetName);

    void addNewWallet(int userId, String assetName, int amountToBeAdded);
}
