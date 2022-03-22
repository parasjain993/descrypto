package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.controllers.OrderControllers.OrderController;
import deshaw.dae.descrypto.services.DashboardService;
import deshaw.dae.descrypto.services.MyService;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public CollectionModel<?> getCoinDetails(){
        return CollectionModel.of(dashboardService.getCoinDetails(),
                linkTo(methodOn(DashboardController.class).getPairs()).withRel("Get all Pairs"),
                linkTo(methodOn(DashboardController.class).getAssets()).withRel("returns all assets"));
    }

    @ApiOperation(value = "getting all individual assets from database", tags = {"Assets"})
    @GetMapping("/assets/get")
    public CollectionModel<?> getAssets(){
        return  CollectionModel.of(dashboardService.getAllAssetsAvail(),
                linkTo(methodOn(DashboardController.class).getAssetById("btc")).withRel("returns given asset details"),
                linkTo(methodOn(DashboardController.class).getCoinDetails()).withRel("Get Market Summary"),
                linkTo(methodOn(DashboardController.class).getPairs()).withRel("returns all Pairs")

        );
    }

    @ApiOperation(value = "getting all supported pairs from database", tags = {"Pairs"})
    @GetMapping("/pairs/get")
    public CollectionModel<?> getPairs(){
        return  CollectionModel.of(dashboardService.getAllTradingPairs(),
                linkTo(methodOn(DashboardController.class).getAssets()).withRel("Get all Assets"),
                linkTo(methodOn(DashboardController.class).getCoinDetails()).withRel("Get Market Summary"),
                linkTo(methodOn(DashboardController.class).getPairById("btcusdt")).withRel("returns Pairs by id")
        );
    }


    @ApiOperation(value = "Get individual asset detail by id", tags = {"Assets"})
    @GetMapping("/assets/{assetId}")
    public EntityModel<?> getAssetById(@PathVariable("assetId") String assetID){
        return  EntityModel.of(dashboardService.getAssetById(assetID),
                linkTo(methodOn(DashboardController.class).getAssets()).withRel("Get all Assets"),
                linkTo(methodOn(DashboardController.class).getCoinDetails()).withRel("Get Market Summary"),
                linkTo(methodOn(DashboardController.class).getPairs()).withRel("returns all Pairs")
                );
    }

    @ApiOperation(value = "get details about given pair by its symbol if it exists in market", tags ={"Pairs"})
    @GetMapping("pairs/{pairId}")
    public EntityModel<?> getPairById(@PathVariable("pairId") String pairId){
        return EntityModel.of(dashboardService.getTradingPairbyId(pairId),
                linkTo(methodOn(DashboardController.class).getPairs()).withRel("Get all Pairs"),
                linkTo(methodOn(DashboardController.class).getCoinDetails()).withRel("Get Market Summary"),
                linkTo(methodOn(DashboardController.class).getAssets()).withRel("returns all assets")
                );
    }




}
