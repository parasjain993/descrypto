package deshaw.dae.descrypto.controllers;


import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.PortfolioWorthService;
import deshaw.dae.descrypto.services.UserService;
import deshaw.dae.descrypto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@EnableScheduling
@RequestMapping("/user")
public class totalWorthController {
    @Autowired
    private UserService userservice;
    @Autowired
    private WalletService walletservice;
    @Autowired
    private PortfolioWorthService portfolioWorthService;

    @GetMapping("/updatePortfolioWorth")
    @Scheduled(fixedRate = 120000)
    public ResponseEntity<?> updatePortfolioWorthForAllUsers() {
        portfolioWorthService.updatePortfolioWorth();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/totalWorth")
    public ResponseEntity<?> totalWorth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userservice.findByUserName(userName);
        return new ResponseEntity<>("Total worth of " + userName + " is (in usdt): " + Float.toString(walletservice.totalWorthCalc(user.getUserId())), HttpStatus.OK);
    }
}
