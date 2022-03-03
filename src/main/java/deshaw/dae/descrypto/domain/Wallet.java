package deshaw.dae.descrypto.domain;


import java.util.Map;

public class Wallet {
     private String walletId;
     private String assetName;
     private int assetCoins;

     public String getWalletId() {
          return walletId;
     }

     public void setWalletId(String walletId) {
          this.walletId = walletId;
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
