package deshaw.dae.descrypto.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.demo;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class fundsController {
    @Autowired
    private WalletService walletservice;
    @Autowired
    private UserService userservice;


    @PutMapping ("/addFund")
    public ResponseEntity<Void> addFund(@RequestBody ObjectNode objectnode ) {
        String assetName = objectnode.get("assetName").asText();
        String fullName = objectnode.get("fullName").asText();
        int amountToBeAdded = objectnode.get("amountToBeAdded").asInt();
        User user = userservice.findByFullUsername(fullName);
        userservice.addFund(user.getWalletId(), assetName, amountToBeAdded);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/withdrawFunds")
    public ResponseEntity<Void> withDrawFunds(@RequestBody ObjectNode objectnode) {
        String assetName = objectnode.get("assetName").asText();
        String fullName = objectnode.get("fullName").asText();
        int amountToBeDeducted = objectnode.get("amountToBeDeducted").asInt();
        User user = userservice.findByFullUsername(fullName);
        int assetAvailableCoins = userservice.getAssetCoins(user.getWalletId(), assetName);
        if (assetAvailableCoins < amountToBeDeducted) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userservice.withdrawFund(user.getWalletId(), assetName, amountToBeDeducted);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }


}
