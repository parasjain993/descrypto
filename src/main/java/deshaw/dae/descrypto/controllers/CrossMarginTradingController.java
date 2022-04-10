package deshaw.dae.descrypto.controllers;


import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.*;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import deshaw.dae.descrypto.services.CrossMarginWalletService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@EnableScheduling
@RestController
@RequestMapping("/")
public class CrossMarginTradingController {
    @Autowired
    private WalletService walletservice;
    @Autowired
    private UserService userservice;
    @Autowired
    private CrossMarginWalletService CrossMarginWalletService;


    private DashboardCache TokenCache = DashboardCache.getDashboardCache();

    @PutMapping ("user/transferFundstoMargin")
    public Object transferFundtoMargin(@RequestBody TransferFunds transferSpotFund) {
        String assetName = transferSpotFund.getAssetName();
        float amountToBeTransferred = transferSpotFund.getAmountToBeTransferred();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet spotWallet = walletservice.findWallet(user.getUserId(), assetName);
        JSONObject obj = new JSONObject();
        String message;
        if(spotWallet == null) {
            message = "Spot wallet for " + userName + " for asset " + assetName + " does not exist!";
            obj.put("failure-meesage",message);
           return obj;
        }
        CrossMarginWallet marginWallet = CrossMarginWalletService.findMarginWallet(user.getUserId(), assetName);
        if(marginWallet==null){
            CrossMarginWalletService.addNewMarginWallet(user.getUserId(), assetName, 0);
        }
        if(walletservice.getAssetCoins(user.getUserId(), assetName)< amountToBeTransferred){
            message = "Spot wallet has amount " + spotWallet.getAssetCoins() + " for asset " + assetName + ". Not enough amout to be transferred";
            obj.put("failure-meesage",message);
            return obj;
        }
        else{
            CrossMarginWalletService.transferFundtoMargin(user.getUserId(), assetName, amountToBeTransferred);
            message = amountToBeTransferred +" coins for " + assetName + " have been transferred successfully to " + userName + "'s margin wallet!";
            obj.put("success-meesage",message);
            return obj;
        }
    }

    @PutMapping ("user/transferFundstoSpot")
    public ResponseEntity<?> transferFundtoSpot(@RequestBody TransferFunds transferMarginFund) {
        String assetName = transferMarginFund.getAssetName();
        float amountToBeTransferred = transferMarginFund.getAmountToBeTransferred();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet spotWallet = walletservice.findWallet(user.getUserId(), assetName);
        float marginRatio=userservice.getMarginRatio(user);
        CrossMarginWallet marginWallet = CrossMarginWalletService.findMarginWallet(user.getUserId(), assetName);
        if(marginWallet == null) {
           return new ResponseEntity<>("Margin wallet for " + userName + " for asset " + assetName + " does not exist!",HttpStatus.OK);
        }
        if(marginRatio<=2){
            return new ResponseEntity<>("Margin ratio is " + marginRatio + " which is <=2 no asset can be transferred to spot wallet! " ,HttpStatus.OK);
        }
        if(marginWallet.getAssetCoins()<amountToBeTransferred){
            return new ResponseEntity<>("Margin wallet has amount " + marginWallet.getAssetCoins() + " for asset " + assetName + ". Not enough amount to be transferred",HttpStatus.OK);
        }else {
            if(spotWallet==null){
                walletservice.addNewWallet(user.getUserId(), assetName, amountToBeTransferred);
            }
            CrossMarginWalletService.transferFundtoSpot(user.getUserId(), assetName, amountToBeTransferred);
        }
        return new ResponseEntity<>(amountToBeTransferred +" coins for " + assetName + " have been transferred successfully to " + userName + "'s spot wallet!",HttpStatus.OK);
    }

    @PutMapping("user/borrowAssets")
    public ResponseEntity<?> borrowFunds(@RequestBody BorrowAmount borrowAmount) {
        String assetName = borrowAmount.getAssetName();
        float amountToBeBorrowed = borrowAmount.getAmountToBeBorrowed();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        float amountInUSDT= amountToBeBorrowed;
        if (!assetName.equals("usdt"))  amountInUSDT= (float) TokenCache.TokenCache.get(assetName + "usdt").getPrice()*amountToBeBorrowed;
        BorrowWallet borrowedWallet = CrossMarginWalletService.findBorrowWallet(user.getUserId(), assetName);
        if(borrowedWallet == null) {
            CrossMarginWalletService.addNewBorrowedWallet(user.getUserId(), assetName, 0, 0);
        }
        float marginRatio=userservice.getMarginRatio(user);
        HashMap<String, Float> marginWallet = CrossMarginWalletService.findMarginAssetsForUser(user.getUserId());
        HashMap<String, Float> borrowWallet = CrossMarginWalletService.findBorrowedAssetsForUser(user.getUserId());

        if(marginWallet.isEmpty()) {
            return new ResponseEntity<>("Margin wallet for " + userName + " does not exist!",HttpStatus.OK);
        }
        if(marginRatio<=1.5){
            return new ResponseEntity<>("Margin ratio for " + userName + " is "+marginRatio + " which is <=1.5! Can not borrow more assets",HttpStatus.OK);
        }
        float marginWalletValue= CrossMarginWalletService.marginWalletValue(user.getUserId());

        if(borrowWallet.isEmpty()) {
            CrossMarginWalletService.addNewBorrowedWallet(user.getUserId(), assetName, 0, 0);
        }
        float borrowWalletValue= CrossMarginWalletService.borrowedWalletValue(user.getUserId());
        if(2*marginWalletValue< amountInUSDT + borrowWalletValue){
            float valueInAsset= 2*marginWalletValue- borrowWalletValue;
            if(!assetName.equals("usdt")) valueInAsset= (2*marginWalletValue- borrowWalletValue)/((float) TokenCache.TokenCache.get(assetName + "usdt").getPrice());
            return new ResponseEntity<>("Margin wallet for " + userName + " values "+ marginWalletValue+ " usdt and "+ borrowWalletValue +" usdt already borrowed. Can't borrow the asked amount at 3x! Maximum "+ valueInAsset +assetName+" can be borrowed",HttpStatus.OK);
        }
        else {
            CrossMarginWalletService.borrowFund(user.getUserId(), assetName, amountToBeBorrowed);
            return new ResponseEntity<>(amountToBeBorrowed +" coins for " + assetName + " have been borrowed successfully! " ,HttpStatus.OK);
        }

    }

    @PutMapping("user/repayBorrowed")
    public ResponseEntity<?> repayBorrowed(@RequestBody RepayAmount repayAmount) {
        String assetName = repayAmount.getAssetName();
        float amountToBeRepaid = repayAmount.getAmountToBeRepaid();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        BorrowWallet borrowWallet = CrossMarginWalletService.findBorrowWallet(user.getUserId(), assetName);
        if(borrowWallet == null) {
            return new ResponseEntity<>("Cannot repay as no borrowed wallet exists for " + assetName + " for " + userName,HttpStatus.OK);
        }
        else {
            float borrowWalletValue= CrossMarginWalletService.borrowedWalletValue(user.getUserId());
            if(amountToBeRepaid> borrowWallet.getAssetCoins() + borrowWallet.getInterest()){
                return new ResponseEntity<>(amountToBeRepaid +" " + assetName + " cannot be repaid as no repayment amount exceeds borrowed value "+ borrowWallet.getAssetCoins() + " for " +assetName,HttpStatus.OK);
            }
            CrossMarginWalletService.repayFund(user.getUserId(), assetName, 0, amountToBeRepaid);
        }
        return new ResponseEntity<>(amountToBeRepaid +" " + assetName + " have been returned successfully! " ,HttpStatus.OK);
    }


    @GetMapping("user/getMarginRatio")
    public ResponseEntity<?> getMarginRatio() {
      
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        HashMap<String, Float> marginWallet = CrossMarginWalletService.findMarginAssetsForUser(user.getUserId());
        if(marginWallet.isEmpty()) {
            return new ResponseEntity<>("Margin wallet for " + userName + " does not exist!",HttpStatus.OK);
        }
        float marginRatio = userservice.getMarginRatio(user);
        return new ResponseEntity<>(user.getUserName() + "'s Margin Ratio is " + marginRatio,HttpStatus.OK);
    }
}
