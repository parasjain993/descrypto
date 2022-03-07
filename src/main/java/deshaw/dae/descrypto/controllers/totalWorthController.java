package deshaw.dae.descrypto.controllers;


import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@EnableScheduling
@RequestMapping("/user")
public class totalWorthController {
    @Autowired
    private UserService userservice;
    @Autowired
    private WalletService walletservice;

//    @GetMapping("/pnl")
//    @Scheduled(fixedRate = 120000)
//    public ResponseEntity<?> pnlCalc() {
//        List<User> allUsers = userservice.getAllUsers();
//        for(User user: allUsers) {
//            float prevTotalWorth = user.getTotalWorth();
//            float  currTotalWorth = walletservice.totalWorthCalc(user.getUserId());
//            userservice.setPNL(currTotalWorth - prevTotalWorth, user.getWalletId());
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @GetMapping("/{userName}/totalWorth")
    public ResponseEntity<?> totalWorth(@PathVariable String userName) {
        User user = userservice.findByUserName(userName);
        return new ResponseEntity<>(walletservice.totalWorthCalc(user.getUserId()), HttpStatus.OK);
    }
}
