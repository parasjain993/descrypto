package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.MyService;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Endpoint for getting current market summary for all supported pairs using external api", tags = { "Market Summary" })
    public ResponseEntity<?> getCoinDetails(){
        return new ResponseEntity<>(dashboardService.getCoinDetails(), HttpStatus.OK);
    }

    @ApiOperation(value = "getting all individual assets from database", tags = {"Assets"})
    @GetMapping("/assets/get")
    public ResponseEntity<?> getAssets(){
        return new ResponseEntity<>(dashboardService.getAllAssetsAvail(), HttpStatus.OK);
    }

    @ApiOperation(value = "getting all supported pairs from database", tags = {"Pairs"})
    @GetMapping("/pairs/get")
    public ResponseEntity<?> getPairs(){
        return new ResponseEntity<>(dashboardService.getAllTradingPairs(), HttpStatus.OK);
    }


    @ApiOperation(value = "Get individual asset detail by id", tags = {"Assets"})
    @GetMapping("/assets/{assetId}")
    public ResponseEntity<?> getAssetById(@PathVariable("assetId") String assetID){
        return new ResponseEntity<>(dashboardService.getAssetById(assetID), HttpStatus.OK);
    }

    @ApiOperation(value = "get details about given pair by its symbol if it exists in market", tags ={"Pairs"})
    @GetMapping("pairs/{pairId}")
    public  ResponseEntity<?> getPairById(@PathVariable("pairId") String pairId){
        return new ResponseEntity<>(dashboardService.getTradingPairbyId(pairId), HttpStatus.OK);
    }




}
