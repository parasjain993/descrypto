package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.domain.demo;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;

@RestController
@RequestMapping("/user")
public class fundsController {
    @Autowired
    private WalletService walletservice;
    @Autowired
    private UserService userservice;


    @PutMapping ("/addFund")
    public ResponseEntity<?> addFund(@RequestBody ObjectNode objectnode) {
        String assetName = objectnode.get("assetName").asText();
        float amountToBeAdded = objectnode.get("amountToBeAdded").floatValue();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet wallet = walletservice.findWallet(user.getUserId(), assetName);
        if(wallet == null) {
            //System.out.print("inside null wallet");
            walletservice.addNewWallet(user.getUserId(), assetName, amountToBeAdded);
        }
        else {
            walletservice.addFund(user.getUserId(), assetName, amountToBeAdded);
        }
        return new ResponseEntity<>(amountToBeAdded+" coins for " + assetName + " has been added successfully in " + userName + "'s spot wallet!",HttpStatus.OK);
    }



    @PutMapping("/withdrawFunds")
    public ResponseEntity<?> withDrawFunds(@RequestBody ObjectNode objectnode) {
        String assetName = objectnode.get("assetName").asText();
        float amountToBeDeducted = objectnode.get("amountToBeDeducted").floatValue();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet wallet = walletservice.findWallet(user.getUserId(), assetName);
        if(wallet == null) {
            return new ResponseEntity<>(amountToBeDeducted + " cannot be deducted as no such " + assetName + " exists in the spot wallet of " + userName, HttpStatus.BAD_REQUEST);
        }
        else {
            float assetAvailableCoins = walletservice.getAssetCoins(user.getUserId(), assetName);
            if (assetAvailableCoins < amountToBeDeducted) {
                return new ResponseEntity<>(amountToBeDeducted + " coins cannot be deducted as total number of coins for " + assetName + " is: " + assetAvailableCoins + " which is less than the coins to be deducted",HttpStatus.BAD_REQUEST);
            } else {
                walletservice.withdrawFund(user.getUserId(), assetName, amountToBeDeducted);
                return new ResponseEntity<>(amountToBeDeducted + " has been deducted from the spot wallet of " + userName + " for asset : " + assetName,HttpStatus.OK);
            }
        }


    }


}
