package deshaw.dae.descrypto.controllers;
import deshaw.dae.descrypto.services.OrderbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@Api(description = "Endpoint for Orderbook",tags = {"Orderbook"})

@RequestMapping("/market")
public class OrderbookController {
    @Autowired
    private OrderbookService orderbookService;

    @ApiOperation(value = "get orderbook for the given pair symbol at given instant")
    @GetMapping("/{pairSym}/orderbook")
    public EntityModel<?> GetOrderBook(@PathVariable("pairSym") String pairSym){
        return  EntityModel.of(orderbookService.allOpenOrdersbyPair(pairSym),
                linkTo(methodOn(DashboardController.class).getCoinDetails()).withRel("get Market Summary"));
    }
}
