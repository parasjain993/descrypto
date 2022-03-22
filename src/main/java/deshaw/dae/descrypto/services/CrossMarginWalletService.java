package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.CrossMarginWallet;
import deshaw.dae.descrypto.domain.BorrowWallet;

import java.util.HashMap;

public interface CrossMarginWalletService {


    HashMap<String, Float> findMarginAssetsForUser(int userId);

    HashMap<String, Float> findBorrowedAssetsForUser(int userId);
    CrossMarginWallet findMarginWallet(int userId, String assetName);

    BorrowWallet findBorrowWallet(int userId, String assetName);

    void addNewMarginWallet(int userId, String assetName, float amountToBeAdded);

    void addNewBorrowedWallet(int userId, String assetName, float amountToBeAdded, float interest);

    void liquidateAssets(int userId);

    void transferFundtoMargin(int userId, String assetName, float amountToBeAdded);

    void transferFundtoSpot(int userId, String assetName, float amountToBeAdded);

    void borrowFund(int userId, String assetName, float amountToBeBorrowed);

    void repayFund(int userId, String assetName, float interestRepaid, float amountToBeRepaid);

    float marginWalletValue(int userId);

    float borrowedWalletValue(int userId);

    float totalCrossWalletValue(int userId);



}
