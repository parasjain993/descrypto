package deshaw.dae.descrypto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import deshaw.dae.descrypto.domain.AssetsAvail;
import deshaw.dae.descrypto.domain.PriceResponse.PriceResponse;
import deshaw.dae.descrypto.domain.Summary24hResponse.Summary24h;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.mappers.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService{
    @Autowired
    private DashboardMapper dashboardMapper;

    public Map<String, AssetDetails> TokenCache = new HashMap<String, AssetDetails>();

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AssetDetails getCoinDetailsByID(String CoinId) {
        String PriceApiUrl= "https://api.cryptowat.ch/markets/kraken/" + CoinId + "/price";
        PriceResponse priceResponse = restTemplate.getForObject(PriceApiUrl, PriceResponse.class);

        String summary24hApiUrl = "https://api.cryptowat.ch/markets/kraken/" + CoinId +"/summary";
        Summary24h summary24hResponse = restTemplate.getForObject(summary24hApiUrl, Summary24h.class);


        return new AssetDetails(CoinId, priceResponse.getPrice(), summary24hResponse);

    }

    @Override
    public List<AssetDetails> getCoinDetails(List<String> CoinIds) {
        List <AssetDetails> Dash = new ArrayList<>();
        for (String CoinId: CoinIds){
            AssetDetails coin = getCoinDetailsByID(CoinId);
            TokenCache.put(CoinId, coin);
            Dash.add(coin);
        }
        return Dash;

    }

    @Override
    public List<AssetsAvail> getAllAssetsAvail(){
        return dashboardMapper.getAllAssetsAvail();

    }

}
