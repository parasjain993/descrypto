package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.CrossMarginWallet;
import deshaw.dae.descrypto.domain.BorrowWallet;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.mappers.CrossMarginWalletMapper;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.mappers.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
public class CrossMarginWalletServiceImpl implements CrossMarginWalletService{
    @Autowired
    private CrossMarginWalletMapper CrossMarginWalletMapper;
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private WalletMapper walletmapper;

    private DashboardCache TokenCache = DashboardCache.getDashboardCache();

    @Override
    public HashMap<String, Float> findMarginAssetsForUser(int userId) {
        HashMap<String,Float> userAssetsMap = new HashMap<>();
        List<CrossMarginWallet> userAssetsList =  CrossMarginWalletMapper.findMarginAssetsForUser(userId);
        for(CrossMarginWallet w: userAssetsList) {
            userAssetsMap.put(w.getAssetName(), (float) w.getAssetCoins());
        }
        return userAssetsMap;
    }
    
    @Override
    public HashMap<String, Float> findBorrowedAssetsForUser(int userId) {
        HashMap<String,Float> userAssetsMap = new HashMap<>();
        List<CrossMarginWallet> userAssetsList =  CrossMarginWalletMapper.findBorrowedAssetsForUser(userId);
        for(CrossMarginWallet w: userAssetsList) {
            userAssetsMap.put(w.getAssetName(), (float) w.getAssetCoins());
        }
        return userAssetsMap;
    }

    public float totalCrossWalletValue(int userId) {
        HashMap<String, Float> marginAssetsOfUser =  findMarginAssetsForUser(userId);
        float total_worth = 0;
        for (String assets : marginAssetsOfUser.keySet()) {
            total_worth = (float) (total_worth + TokenCache.TokenCache.get(assets + "usdt").getPrice() * marginAssetsOfUser.get(assets));
        }
        return total_worth;
    }


    public void liquidateAssets(int userId) {
        float debt= borrowedWalletValue(userId)+ interest(userId);
        float newMarginWalletValue= totalCrossWalletValue(userId)- debt;
        CrossMarginWalletMapper.liquidateMarginwallet(userId, newMarginWalletValue);

    }

    public float interest(int userId){
        HashMap<String, Float> borrowedAssetsOfUser =  findBorrowedAssetsForUser(userId);
        float total_interest = 0;
        for (String assets : borrowedAssetsOfUser.keySet()) {
            total_interest = (float) (total_interest + TokenCache.TokenCache.get(assets + "usdt").getPrice() *findBorrowWallet(userId, assets).getInterest());
        }
        return total_interest;
    }

    public float borrowedWalletValue(int userId) {
        HashMap<String, Float> borrowedAssetsOfUser =  findBorrowedAssetsForUser(userId);
        float total_worth = 0;
        if (borrowedAssetsOfUser.isEmpty()) return 0;
        for (String assets : borrowedAssetsOfUser.keySet()) {
            total_worth = (float) (total_worth + TokenCache.TokenCache.get(assets + "usdt").getPrice() * borrowedAssetsOfUser.get(assets));
        }
        return total_worth;
    }

    public float marginWalletValue(int userId) {
        float totalCrossWalletValue= totalCrossWalletValue(userId);
        float borrowedWalletValue= borrowedWalletValue(userId);
        return totalCrossWalletValue-borrowedWalletValue;
    }

    @Override
    public void repayFund(int userId, String assetName,float interestRepaid, float amountToBeRepaid) {
        BorrowWallet borrowWallet=findBorrowWallet(userId, assetName);

        if(borrowWallet.getInterest()> amountToBeRepaid){
            interestRepaid= amountToBeRepaid;
            amountToBeRepaid=0;
        }
        else{
            interestRepaid= borrowWallet.getInterest();
            amountToBeRepaid= amountToBeRepaid- borrowWallet.getInterest();
        }
        CrossMarginWalletMapper.repayFund(userId, assetName, interestRepaid, amountToBeRepaid);
    }

    @Override
    public void transferFundtoMargin(int userId, String assetName, float amountToBeTransferred) {
        CrossMarginWalletMapper.transferFundtoMargin(userId, assetName, amountToBeTransferred);
    }

     @Override
    public void transferFundtoSpot(int userId, String assetName, float amountToBeTransferred) {
         CrossMarginWalletMapper.transferFundtoSpot(userId, assetName, amountToBeTransferred);
    }
    @Override
    public void borrowFund(int userId, String assetName, float amountToBeBorrowed) {
        CrossMarginWalletMapper.borrowFund(userId, assetName, amountToBeBorrowed);
    }

      @Override
    public CrossMarginWallet findMarginWallet(int userId, String assetName) {
        return CrossMarginWalletMapper.findMarginWallet(userId, assetName);
    }
      @Override
    public BorrowWallet findBorrowWallet(int userId, String assetName) {
        return CrossMarginWalletMapper.findBorrowWallet(userId, assetName);
    }

    @Override
    public void addNewMarginWallet(int userId, String assetName, float amountToBeAdded) {
        CrossMarginWalletMapper.addNewMarginWallet(userId, assetName, amountToBeAdded);
    }
    @Override
    public void addNewBorrowedWallet(int userId, String assetName, float amountToBeAdded, float interest) {
        CrossMarginWalletMapper.addNewBorrowedWallet(userId, assetName, amountToBeAdded, 0);
    }

    @Scheduled(fixedRate = 3600000)
    private void updateBorrowedMoney() {
        List<User> users = usermapper.getAllUsers();
        float rate = (float) 0.02;
        for(User user: users){
            HashMap<String, Float> borrowedAssets = findBorrowedAssetsForUser(user.getUserId());
            for(String asset: borrowedAssets.keySet()){
                BorrowWallet borrowWallet= findBorrowWallet(user.getUserId(), asset);
                float amount = borrowedAssets.get(asset);
                float interest = borrowWallet.getInterest()+ (rate*amount/100);
                CrossMarginWalletMapper.updateInterest(user.getUserId(),interest, asset);
            }

        }

    }

    @Scheduled(fixedRate = 60000)
    private void updateMR() {
        List<User> users = usermapper.getAllUsers();
        for (User user: users) {
            float totalAssets = totalCrossWalletValue(user.getUserId());
            float borrowedAssets =borrowedWalletValue(user.getUserId());
            float interest= interest(user.getUserId());
            float marginRatio = 999;
            if(borrowedAssets +interest!=0){
                marginRatio = totalAssets/(borrowedAssets+interest);
            }
            usermapper.updateMarginRatio(user.getUserId(),marginRatio);
        }
    }



}




