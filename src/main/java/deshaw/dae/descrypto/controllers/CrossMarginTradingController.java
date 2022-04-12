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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<?> transferFundtoMargin(@RequestBody TransferFunds transferSpotFund) {
        String assetName = transferSpotFund.getAssetName();
        float amountToBeTransferred = transferSpotFund.getAmountToBeTransferred();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet spotWallet = walletservice.findWallet(user.getUserId(), assetName);
        JSONObject obj=new JSONObject();
        if(spotWallet == null) {
            obj.put("status","failed");
            obj.put("message","Spot wallet for  " + userName + " for asset " + assetName + " does not exist!");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).borrowFunds(null)).withRel("borrow funs"),
                    linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));

        }
        CrossMarginWallet marginWallet = CrossMarginWalletService.findMarginWallet(user.getUserId(), assetName);
        if(marginWallet==null){
            CrossMarginWalletService.addNewMarginWallet(user.getUserId(), assetName, 0);
        }
        if(walletservice.getAssetCoins(user.getUserId(), assetName)< amountToBeTransferred){
            obj.put("status","failed");
            obj.put("message","Spot wallet has amount " + spotWallet.getAssetCoins() + " for asset " + assetName + ". Not enough amout to be transferred");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).borrowFunds(null)).withRel("borrow funs"),
                    linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));

        }
        else{
            CrossMarginWalletService.transferFundtoMargin(user.getUserId(), assetName, amountToBeTransferred);
            obj.put("status","success");
            obj.put("message",amountToBeTransferred +" coins for " + assetName + " have been transferred successfully to " + userName + "'s margin wallet!");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).borrowFunds(null)).withRel("borrow funs"),
                    linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));

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
    public EntityModel<?> borrowFunds(@RequestBody BorrowAmount borrowAmount) {
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
        JSONObject obj =new JSONObject();
        if(marginWallet.isEmpty()) {
            obj.put("status","failed");
            obj.put("message","Margin wallet for " + userName + " does not exist!");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));

        }
        if(marginRatio<=1.5){
            obj.put("status","failed");
            obj.put("message","Margin ratio for " + userName + " is "+marginRatio + " which is <=1.5! Can not borrow more assets");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));

        }
        float marginWalletValue= CrossMarginWalletService.marginWalletValue(user.getUserId());

        if(borrowWallet.isEmpty()) {
            CrossMarginWalletService.addNewBorrowedWallet(user.getUserId(), assetName, 0, 0);
        }
        float borrowWalletValue= CrossMarginWalletService.borrowedWalletValue(user.getUserId());
        if(2*marginWalletValue< amountInUSDT + borrowWalletValue){


            float valueInAsset= 2*marginWalletValue- borrowWalletValue;
            if(!assetName.equals("usdt")) valueInAsset= (2*marginWalletValue- borrowWalletValue)/((float) TokenCache.TokenCache.get(assetName + "usdt").getPrice());
            obj.put("status","failed");
            obj.put("message","Margin wallet for " + userName + " values "+ marginWalletValue+ " usdt and "+ borrowWalletValue +" usdt already borrowed. Can't borrow the asked amount at 3x! Maximum "+ valueInAsset +assetName+" can be borrowed");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));


        }
        else {
            CrossMarginWalletService.borrowFund(user.getUserId(), assetName, amountToBeBorrowed);
            obj.put("status","success");
            obj.put("message",amountToBeBorrowed +" coins for " + assetName + " have been borrowed successfully! ");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).repayBorrowed(null)).withRel("repay borrowed"));



        }

    }

    @PutMapping("user/repayBorrowed")
    public EntityModel<?> repayBorrowed(@RequestBody RepayAmount repayAmount) {
        String assetName = repayAmount.getAssetName();
        float amountToBeRepaid = repayAmount.getAmountToBeRepaid();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        BorrowWallet borrowWallet = CrossMarginWalletService.findBorrowWallet(user.getUserId(), assetName);
        JSONObject obj =new JSONObject();
        if(borrowWallet == null) {
            obj.put("status","failed");
            obj.put("message","Cannot repay as no borrowed wallet exists for " + assetName + " for " + userName);
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).getMarginRatio()).withRel("margin ratio"));

        }
        else {
            float borrowWalletValue= CrossMarginWalletService.borrowedWalletValue(user.getUserId());
            if(amountToBeRepaid> borrowWallet.getAssetCoins() + borrowWallet.getInterest()){
                obj.put("status","failed");
                obj.put("message",amountToBeRepaid +" " + assetName + " cannot be repaid as no repayment amount exceeds borrowed value "+ borrowWallet.getAssetCoins() + " for " +assetName);
                return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).getMarginRatio()).withRel("margin ratio"));

            }
            CrossMarginWalletService.repayFund(user.getUserId(), assetName, 0, amountToBeRepaid);
        }
        obj.put("status","success");
        obj.put("message",amountToBeRepaid +" " + assetName + " have been returned successfully! ");
        return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).getMarginRatio()).withRel("margin ratio"));

    }


    @GetMapping("user/getMarginRatio")
    public EntityModel<?> getMarginRatio() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        HashMap<String, Float> marginWallet = CrossMarginWalletService.findMarginAssetsForUser(user.getUserId());
        JSONObject obj = new JSONObject();
        if(marginWallet.isEmpty()) {
            obj.put("status","failed");
            obj.put("message","Margin wallet for " + userName + " does not exist!");
            return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).transferFundtoSpot(null)).withRel("transfer to spot"));

        }
        float marginRatio = userservice.getMarginRatio(user);
        obj.put("status","success");
        obj.put("message",user.getUserName() + "'s Margin Ratio is " + marginRatio);
        return EntityModel.of(obj,linkTo(methodOn(CrossMarginTradingController.class).transferFundtoSpot(null)).withRel("transfer to spot"));

    }
}