package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.Wallet;

import java.util.List;

public interface WalletMapper {
    List<Wallet> findAssetsForUser(String walletId);
    void addFund(String walletId, String assetName, int amountToBeAdded);
    int getAssetCoins(String walletId, String assetName);
    void withdrawFund(String walletId, String assetName, int withdrawalAmount);
}
