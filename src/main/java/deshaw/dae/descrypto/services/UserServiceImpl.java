package deshaw.dae.descrypto.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.PriceResponse.PriceResponse;
import deshaw.dae.descrypto.domain.Summary24hResponse.Summary24h;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper usermapper;
    public HashMap<String, AssetDetails> TokenCache = new HashMap<String, AssetDetails>();

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public User findByFullUsername(String name){
        return usermapper.findByFullUsername(name);
    }



    @Override
    public AssetDetails getCoinDetailsByID(String CoinId) {
        String PriceApiUrl= "https://api.cryptowat.ch/markets/kraken/" + CoinId + "/price";
        PriceResponse priceResponse = restTemplate.getForObject(PriceApiUrl, PriceResponse.class);

        String summary24hApiUrl = "https://api.cryptowat.ch/markets/kraken/" + CoinId +"/summary";
        Summary24h summary24hResponse = restTemplate.getForObject(summary24hApiUrl, Summary24h.class);


        return new AssetDetails(CoinId, priceResponse.getPrice(), summary24hResponse);

    }

    @Override
    public List<AssetDetails> getCoinDetails(List<String> CoinIds) {
        List<AssetDetails> Dash = new ArrayList<>();
        for (String CoinId: CoinIds){
            AssetDetails coin = getCoinDetailsByID(CoinId);
            TokenCache.put(CoinId, coin);
            Dash.add(coin);
        }
        return Dash;

    }
   @Override
   public HashMap<String, Integer> findAssetsForUser(String walletId) {
       HashMap<String,Integer> h = new HashMap<String, Integer>();
        List<Wallet> l =  usermapper.findAssetsForUser(walletId);
        for(Wallet w: l) {

            h.put(w.getAssetName(), w.getAssetCoins());
        }
        return h;
   }
    @Override
    public float totalWorthCalc(String walletId) {
        System.out.print("insdie total worth calc");
        HashMap<String, Integer> assetsForUser =  findAssetsForUser(walletId);

        float total_worth = 0;
        for (String assets : assetsForUser.keySet()) {
            total_worth = (float) (total_worth + TokenCache.get(assets).getPrice() * assetsForUser.get(assets));
        }

        usermapper.setTotalWorth(walletId, total_worth);
        return total_worth;
    }

    @Override
    public int getAssetCoins(String walletId, String assetName) {
        return usermapper.getAssetCoins(walletId, assetName);
    }


    @Override
    public void addFund(String walletId, String assetName, int amountToBeAdded) {
        usermapper.addFund(walletId, assetName, amountToBeAdded);
    }

    @Override
    public List<User> getAllUsers() {
        return usermapper.getAllUsers();
    }

    @Override
    public void setPNL(float v, String walletId) {
        usermapper.setPNL(v, walletId);
    }

    @Override
    public void withdrawFund(String walletId, String assetName, int withdrawalAmount) {
        usermapper.withdrawFund(walletId, assetName,withdrawalAmount);
    }

    @Override
    public void addUser(User user) {
        usermapper.addUser(user);
    }
}
