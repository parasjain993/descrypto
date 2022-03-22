package deshaw.dae.descrypto.controllers.OrderControllers;

import deshaw.dae.descrypto.domain.Trade;
import deshaw.dae.descrypto.services.TradeServices.TradeService;
import deshaw.dae.descrypto.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(description = "Endpoints for handling trades",tags = {"Trades Handling"})
@RestController
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private TradeService service;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Endpoint for getting trade history details", tags = { "Trade History" })
    @PostMapping("/details")
    CollectionModel<EntityModel<Trade>> showTradeHistory(@RequestBody JSONObject data) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        int userId=userService.findByUserName(userName).getUserId();

        List<EntityModel<Trade>> trades = service.tradeHistory(data,userId).stream()
                .map(trade -> EntityModel.of(trade,
                        linkTo(methodOn(TradeController.class).showTradeHistory(data)).withRel("TradeHistory")))
                .collect(Collectors.toList());

        return CollectionModel.of(trades, linkTo(methodOn(TradeController.class).showTradeHistory(data)).withSelfRel());

    }

}
