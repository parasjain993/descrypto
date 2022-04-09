package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.domain.*;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.javassist.compiler.ast.Pair;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.HashMap;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(description = "Endpoints for adding and withdrawing funds from spot wallet of user",tags = {"Funds of Spot Wallet"})
@RestController
@RequestMapping("/user")
public class fundsController {
    @Autowired
    private WalletService walletservice;
    @Autowired
    private UserService userservice;


    @ApiOperation(value = "Add funds in the spot wallet")
    @RequestMapping(value = "/addFund", method= RequestMethod.PUT)
    public EntityModel<?> addFund(@RequestBody AddFund addfund) {
        String assetName = addfund.getAssetName();
        float amountToBeAdded = addfund.getAmountToBeAdded();
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
        String message = Float.toString(amountToBeAdded)+" coins for " + assetName + " has been added successfully in " + userName + "'s spot wallet!";
        obj.put("success_message", message);
        obj.put("Status", HttpStatus.OK);
        return EntityModel.of(obj,
                linkTo(methodOn(totalWorthController.class).totalWorth()).withRel("TotalWorth")
                );
    }
    @ApiOperation(value = "Information about Spot wallet funds")
    @RequestMapping(value = "/spotwallet-funds", method = RequestMethod.GET)
        public List<FundsInfo> spotWalletFunds() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            User user = userservice.findByUserName(userName);
            List<FundsInfo> l = walletservice.fundsInfo(user.getUserId());
            return l;
        }

    @ApiOperation(value = "Withdrawal funds from the spot wallet")
    @RequestMapping(value = "/withdrawFunds", method= RequestMethod.PUT)
    public EntityModel<?> withDrawFunds(@RequestBody WithdrawFund withdrawfund) {
        JSONObject obj = new JSONObject();
        String assetName = withdrawfund.getAssetName();
        float amountToBeDeducted = withdrawfund.getAmountToBeDeducted();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        Wallet wallet = walletservice.findWallet(user.getUserId(), assetName);
        if(wallet == null) {
            String message = Float.toString(amountToBeDeducted) + " cannot be deducted as no such " + assetName + "asset doesn't exists in the spot wallet of " + userName;
            obj.put("failure_message", message);
            obj.put("Status", HttpStatus.BAD_REQUEST);
            return EntityModel.of(obj,
                    linkTo(methodOn(fundsController.class).addFund(null)).withRel("Try adding the asset in the wallet")
            );
        }
        else {
            float assetAvailableCoins = walletservice.getAssetCoins(user.getUserId(), assetName);
            if (assetAvailableCoins < amountToBeDeducted) {

                String message = Float.toString(amountToBeDeducted) + " coins cannot be deducted as total number of coins for " + assetName + " is: " +Float.toString(assetAvailableCoins) + " which is less than the coins to be deducted";

                obj.put("failure_message", message);
                obj.put("Status", HttpStatus.BAD_REQUEST);
                return EntityModel.of(obj,
                        linkTo(methodOn(fundsController.class).addFund(null)).withRel("Try adding more coins in the wallet before withdrawing")
                );

            } else {
                walletservice.withdrawFund(user.getUserId(), assetName, amountToBeDeducted);
                float amt = walletservice.getAssetCoins(user.getUserId(), assetName);
                if(amt == 0) {
                    walletservice.removeAsset(user.getUserId(), assetName);
                }
                String message = Float.toString(amountToBeDeducted) + " has been deducted from the spot wallet of " + userName + " for asset : " + assetName;
                obj.put("success_message", message);
                obj.put("Status", HttpStatus.OK);
                return EntityModel.of(obj,
                        linkTo(methodOn(totalWorthController.class).totalWorth()).withRel("TotalWorth")
                );

            }
        }


    }


}
