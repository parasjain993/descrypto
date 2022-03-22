package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.domain.demo;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;
@Api(description = "Endpoints for adding and withdrawing funds from spot wallet of user",tags = {"Funds of Spot Wallet"})
@RestController
@RequestMapping("/user")
public class fundsController {
    @Autowired
    private WalletService walletservice;
    @Autowired
    private UserService userservice;


    @ApiOperation(value = "Add funds in the spot wallet", tags = { "Add funds" })
    @RequestMapping(value = "/addFund", method= RequestMethod.PUT)
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
        JSONObject obj = new JSONObject();
        String message = amountToBeAdded+" coins for " + assetName + " has been added successfully in " + userName + "'s spot wallet!";
        obj.put("success_message", message);
        return new ResponseEntity<>(obj,HttpStatus.OK);
    }

    @ApiOperation(value = "Withdrawal funds from the spot wallet", tags = { "Withdrawal funds" })
    @RequestMapping(value = "/withdrawFunds", method= RequestMethod.PUT)
    public ResponseEntity<?> withDrawFunds(@RequestBody ObjectNode objectnode) {
        JSONObject obj = new JSONObject();
        String assetName = objectnode.get("assetName").asText();
        float amountToBeDeducted = objectnode.get("amountToBeDeducted").floatValue();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet wallet = walletservice.findWallet(user.getUserId(), assetName);
        if(wallet == null) {
            String message = amountToBeDeducted + " cannot be deducted as no such " + assetName + "asset exists in the spot wallet of " + userName;
            obj.put("failure_message", message);
            return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
        }
        else {
            float assetAvailableCoins = walletservice.getAssetCoins(user.getUserId(), assetName);
            if (assetAvailableCoins < amountToBeDeducted) {
                String message = amountToBeDeducted + " coins cannot be deducted as total number of coins for " + assetName + " is: " + assetAvailableCoins + " which is less than the coins to be deducted";
                obj.put("failure_message", message);
                return new ResponseEntity<>(obj,HttpStatus.BAD_REQUEST);
            } else {
                walletservice.withdrawFund(user.getUserId(), assetName, amountToBeDeducted);

                String message = amountToBeDeducted + " has been deducted from the spot wallet of " + userName + " for asset : " + assetName;
                obj.put("success_message", message);
                return new ResponseEntity<>(obj,HttpStatus.OK);
            }
        }


    }


}
