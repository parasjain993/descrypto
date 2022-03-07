package deshaw.dae.descrypto.domain;


import java.util.Map;

public class Wallet {
     private int userId;
     private String assetName;
     private int assetCoins;

     public int getUserId() {
          return userId;
     }

     public void setUserId(int userId) {
          this.userId = userId;
     }

     public int getAssetCoins() {
          return assetCoins;
     }

     public void setAssetCoins(int assetCoins) {
          this.assetCoins = assetCoins;
     }

     public String getAssetName() {
          return assetName;
     }

     public void setAssetName(String assetName) {
          this.assetName = assetName;
     }
}
