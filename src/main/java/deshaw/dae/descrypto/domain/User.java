package deshaw.dae.descrypto.domain;

public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String walletId;
    private String pancardNo;
    private String dob;
    private String phoneNo;
    private String nationality;
    private float totalWorth;
    private float pnl;

    public float getPnl() {
        return pnl;
    }

    public void setPnl(float pnl) {
        this.pnl = pnl;
    }

    public float getTotalWorth() {
        return totalWorth;
    }

    public void setTotalWorth(float totalWorth) {
        this.totalWorth = totalWorth;
    }


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPancardNo() {
        return pancardNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPancardNo(String pancardNo) {
        this.pancardNo = pancardNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getPassword() {
        return password;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
