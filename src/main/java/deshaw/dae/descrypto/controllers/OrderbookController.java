package deshaw.dae.descrypto.controllers;
import deshaw.dae.descrypto.services.OrderbookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market")
public class OrderbookController {
    @Autowired
    private OrderbookService orderbookService;

    @ApiOperation(value = "get orderbook for the given pair symbol at given instant", tags = {"Orderbook"})
    @GetMapping("/{pairSym}/orderbook")
    public ResponseEntity<?> GetOrderBook(@PathVariable("pairSym") String pairSym){
        return new ResponseEntity<>(orderbookService.allOpenOrdersbyPair(pairSym), HttpStatus.OK);
    }





}
