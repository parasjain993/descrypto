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
import org.springframework.web.bind.annotation.PathVariable;
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
        return new ResponseEntity<>(dashboardService.getCoinDetails(), HttpStatus.OK);
    }

    @GetMapping("/assets/get")
    public ResponseEntity<?> getAssets(){
        return new ResponseEntity<>(dashboardService.getAllAssetsAvail(), HttpStatus.OK);
    }

    @GetMapping("/pairs/get")
    public ResponseEntity<?> getPairs(){
        return new ResponseEntity<>(dashboardService.getAllTradingPairs(), HttpStatus.OK);
    }

    @GetMapping("/assets/{assetId}")
    public ResponseEntity<?> getAssetById(@PathVariable("assetId") String assetID){
        return new ResponseEntity<>(dashboardService.getAssetById(assetID), HttpStatus.OK);
    }

    @GetMapping("pairs/{pairId}")
    public  ResponseEntity<?> getPairById(@PathVariable("pairId") String pairId){
        return new ResponseEntity<>(dashboardService.getTradingPairbyId(pairId), HttpStatus.OK);
    }




}
