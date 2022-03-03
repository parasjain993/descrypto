package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.Trade;
import deshaw.dae.descrypto.services.TradeServices.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private TradeService service;

    @GetMapping("/tradeHistory/{userId}")
    List<Trade> showTradeHistory(@PathVariable("userId") int userId) {

        return service.tradeHistory(userId);
    }

    @GetMapping("/partialTrade/{orderId}")
    List<Trade> showPartialOrder(@PathVariable("orderId") int orderId) {
        return service.partialTrade(orderId);
    }
}
