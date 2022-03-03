package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.mappers.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HashMap<String, Integer> findAssetsForUser(String walletId) {
        HashMap<String,Integer> userAssetsMap = new HashMap<String, Integer>();
        List<Wallet> userAssetsList =  walletmapper.findAssetsForUser(walletId);
        for(Wallet w: userAssetsList) {
            userAssetsMap.put(w.getAssetName(), w.getAssetCoins());
        }
        return userAssetsMap;
    }
    @Override
    public float totalWorthCalc(String walletId) {
        HashMap<String, Integer> assetsOfUser =  findAssetsForUser(walletId);
        float total_worth = 0;
        for (String assets : assetsOfUser.keySet()) {
            total_worth = (float) (total_worth + TokenCache.TokenCache.get(assets).getPrice() * assetsOfUser.get(assets));
        }
        usermapper.setTotalWorth(walletId, total_worth);
        return total_worth;
    }

    @Override
    public int getAssetCoins(String walletId, String assetName) {
        return walletmapper.getAssetCoins(walletId, assetName);
    }
    @Override
    public void withdrawFund(String walletId, String assetName, int withdrawalAmount) {
        walletmapper.withdrawFund(walletId, assetName,withdrawalAmount);
    }
    @Override
    public void addFund(String walletId, String assetName, int amountToBeAdded) {
        walletmapper.addFund(walletId, assetName, amountToBeAdded);
    }
}
