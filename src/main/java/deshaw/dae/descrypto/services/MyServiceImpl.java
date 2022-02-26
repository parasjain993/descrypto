package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import deshaw.dae.descrypto.services.MyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyServiceImpl implements MyService {






    public TokenDetails getCoinDetailsByID(String CoinId) {
        String PriceApiUrl= "https://api.cryptowat.ch/markets/kraken/" + CoinId + "/price";
        String PriceResponse = restTemplate.getForObject(PriceApiUrl, String.class);

        String summary24hApiUrl = "https://api.cryptowat.ch/markets/kraken/" + CoinId +"/summary";
        String summary24hResponse = restTemplate.getForObject(summary24hApiUrl, String.class);


        try {
            JsonNode Pricenode = objectMapper.readTree(PriceResponse);
            String Price = String.valueOf(Pricenode.get("result").get("price"));

            JsonNode Summary24hNode = objectMapper.readTree(summary24hResponse);
            // filtering out information from the api response and mapping to appropriate attributes
            String Volume = String.valueOf(Summary24hNode.get("result").get("volume"));
            String highPrice = String.valueOf(Summary24hNode.get("result").get("price").get("high"));
            String lowPrice = String.valueOf(Summary24hNode.get("result").get("price").get("low"));
            String absChange = String.valueOf(Summary24hNode.get("result").get("price").get("change").get("absolute"));
            String percentageChange = String.valueOf(Summary24hNode.get("result").get("price").get("change").get("percentage"));

            return new TokenDetails(CoinId, Double.parseDouble(Price), new Summary24h(
                    Double.parseDouble(Volume),
                    Double.parseDouble(highPrice),
                    Double.parseDouble(lowPrice),
                    Double.parseDouble(absChange),
                    Double.parseDouble(percentageChange)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to fetch data for pair with id: " + CoinId);
        }
    }


    public List<TokenDetails> getCoinDetails(List<String> CoinIds) {
        List <TokenDetails> Dash = new ArrayList<>();
        for (String CoinId: CoinIds){
            TokenDetails coin = getCoinDetailsByID(CoinId);
            TokenCache.put(CoinId, coin);
            Dash.add(coin);
        }
        return Dash;


    }
}
