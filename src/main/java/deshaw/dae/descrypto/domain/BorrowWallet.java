package deshaw.dae.descrypto.domain;

public class BorrowWallet {

    private int userId;
    private String assetName;
    private float assetCoins;
    private float interest;

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

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }
}