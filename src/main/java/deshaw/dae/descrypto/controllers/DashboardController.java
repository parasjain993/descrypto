package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.MyService;
import deshaw.dae.descrypto.services.UserService;
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
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserService userservice;

    // update after every 2minutes
    @GetMapping("/markets/summary/get")
    @Scheduled(fixedRate = 120000)
    public ResponseEntity<?> getCoinDetails(){
        // This is temporary code util data is fetched from the database //
        List<String> TemporaryCoins = List.of("btcusd", "ethcad", "usdtusd", "btccad");
        return new ResponseEntity<>(dashboardService.getCoinDetails(TemporaryCoins), HttpStatus.OK);
    }

    @GetMapping("/assets/{id}")
    public ResponseEntity<?> getAssets(){
        return new ResponseEntity<>(dashboardService.getAllAssetsAvail(), HttpStatus.OK);
    }

}
