package deshaw.dae.descrypto.controllers;


import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.UserService;
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

    @GetMapping("/pnl")
    @Scheduled(fixedRate = 120000)
    public ResponseEntity<?> pnlCalc() {
        List<User> allUsers = userservice.getAllUsers();
        for(User user: allUsers) {
            float prevTotalWorth = user.getTotalWorth();
            float  currTotalWorth = userservice.totalWorthCalc(user.getWalletId());
            userservice.setPNL(currTotalWorth - prevTotalWorth, user.getWalletId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{fullName}/totalWorth")
    public ResponseEntity<?> totalWorth(@PathVariable String fullName) {
        User user = userservice.findByFullUsername(fullName);
        System.out.print(user.getWalletId());
        return new ResponseEntity<>(userservice.totalWorthCalc(user.getWalletId()), HttpStatus.OK);
    }
}
