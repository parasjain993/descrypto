package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.CrossMarginWallet;
import deshaw.dae.descrypto.domain.BorrowWallet;

import java.util.List;

public interface CrossMarginWalletMapper {

    void transferFundtoMargin(int userId, String assetName, int amountToBeTransferred);
    void transferFundtoSpot(int userId, String assetName, int amountToBeTransferred);
    float getMarginAssetCoins(int userId, String assetName);
    float getBorrowedAssetCoins(int userId, String assetName);

    CrossMarginWallet findMarginWallet(int userId, String assetName);
    BorrowWallet findBorrowWallet(int userId, String assetName);

    void liquidateMarginwallet(int userId, float newMarginWalletValue);
    void addNewMarginWallet(int userId, String assetName, int amountToBeAdded);
    void addNewBorrowedWallet(int userId, String assetName, int amountToBeAdded, float interest);

    List<CrossMarginWallet> findMarginAssetsForUser(int userId);
    List<BorrowWallet> findBorrowedAssetsForUser(int userId);
    void updateInterest(int userId, float interest, String assetName);

    void borrowFund(int userId, String assetName, float amountToBeBorrowed);
    void repayFund(int userId, String assetName, float interestRepaid, float amountToBeBorrowed);
}
