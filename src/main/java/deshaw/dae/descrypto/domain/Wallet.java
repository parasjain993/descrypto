package deshaw.dae.descrypto.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
public class Wallet {
     private int userId;
     private String assetName;
     private float assetCoins;
     public int getUserId() {
          return userId;
     }

     public void setUserId(int userId) {
          this.userId = userId;
     }

     public float getAssetCoins() {
          return assetCoins;
     }

     public void setAssetCoins(float assetCoins) {
          this.assetCoins = assetCoins;
     }

     public String getAssetName() {
          return assetName;
     }

     public void setAssetName(String assetName) {
          this.assetName = assetName;
     }
}
