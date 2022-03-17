package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.mappers.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService{
    @Autowired
    private WalletMapper walletmapper;
    @Autowired
    private UserMapper usermapper;

    private DashboardCache TokenCache = DashboardCache.getDashboardCache();
    @Override
    public HashMap<String, Integer> findAssetsForUser(int userId) {
        HashMap<String,Integer> userAssetsMap = new HashMap<>();
        List<Wallet> userAssetsList =  walletmapper.findAssetsForUser(userId);
        for(Wallet w: userAssetsList) {
            userAssetsMap.put(w.getAssetName(), w.getAssetCoins());
        }
        return userAssetsMap;
    }
    @Override
    public float totalWorthCalc(int userId) {
        HashMap<String, Integer> assetsOfUser =  findAssetsForUser(userId);
        float total_worth = 0;
        for (String assets : assetsOfUser.keySet()) {
            total_worth = (float) (total_worth + TokenCache.TokenCache.get(assets + "usdt").getPrice() * assetsOfUser.get(assets));
        }
        return total_worth;
    }

    @Override
    public int getAssetCoins(int userId, String assetName) {
        return walletmapper.getAssetCoins(userId, assetName);
    }
    @Override
    public void withdrawFund(int userId, String assetName, int withdrawalAmount) {
        walletmapper.withdrawFund(userId, assetName,withdrawalAmount);
    }
    @Override
    public void addFund(int userId, String assetName, int amountToBeAdded) {
        walletmapper.addFund(userId, assetName, amountToBeAdded);
    }

    @Override
    public Wallet findWallet(int userId, String assetName) {
        return walletmapper.findWallet(userId, assetName);
    }

    @Override
    public void addNewWallet(int userId, String assetName, int amountToBeAdded) {
        walletmapper.addNewWallet(userId, assetName, amountToBeAdded);
    }

    @Override
    public void updateTotalWorth() {
        List<User> users = usermapper.getAllUsers();
        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        for(User user: users) {
            float totalWorth  = totalWorthCalc(user.getUserId());

        }
    }
}
