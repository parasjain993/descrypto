package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@EnableScheduling
@RequestMapping("/api")
public class MyController {
    @Autowired
    private MyService service;

    @GetMapping("/get/users")
    List<User> all() {
        return service.findAllUsers();
    }


    @GetMapping("/get/dashboard/assets")
    // update after every 2minutes
    @Scheduled(fixedRate = 120000)
    public ResponseEntity<?> getCoinDetails(){
        // This is temporary code util data is fetched from the database //
        List<String> TemporaryCoins = List.of("btceur", "ethcad", "usdtusd", "btccad");
        return new ResponseEntity<>(service.getCoinDetails(TemporaryCoins), HttpStatus.OK);
    }

}
