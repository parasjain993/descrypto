package deshaw.dae.descrypto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.AssetsAvail;
import deshaw.dae.descrypto.domain.PriceResponse.PriceResponse;
import deshaw.dae.descrypto.domain.Summary24hResponse.Summary24h;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.TradingPairs;
import deshaw.dae.descrypto.mappers.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private DashboardCache TokenCache = DashboardCache.getDashboardCache();

    @Value("${price.url}")
    private String PriceApiUrl;

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<String> getAllTradingPairID(){
        List<TradingPairs> currTradingPairs = dashboardMapper.getAllTradingPairsAvail();
        List<String> TradingPairIds = new ArrayList<>();
        for (TradingPairs currTradingPair: currTradingPairs) {
            TradingPairIds.add(currTradingPair.PairSymbol());
        }
        return  TradingPairIds;
    }

    @Override
    public AssetDetails getCoinDetailsByID(String CoinId) {

        String PriceUrl = PriceApiUrl + CoinId + "/price";
        PriceResponse priceResponse = restTemplate.getForObject(PriceUrl, PriceResponse.class);

        String summary24hApiUrl = PriceApiUrl + CoinId +"/summary";
        Summary24h summary24hResponse = restTemplate.getForObject(summary24hApiUrl, Summary24h.class);
        return new AssetDetails(CoinId, priceResponse.getPrice(), summary24hResponse);

    }

    @Override
    public List<AssetDetails> getCoinDetails() {
        List <AssetDetails> Dash = new ArrayList<>();
        List <String> PairIds = getAllTradingPairID();
        for (String PairId: PairIds){
            AssetDetails coin = getCoinDetailsByID(PairId);
            TokenCache.addTokenDetails(PairId, coin);
            Dash.add(coin);
        }
        return Dash;

    }

    @Override
    public List<AssetsAvail> getAllAssetsAvail(){
        return dashboardMapper.getAllAssetsAvail();
    }

    @Override
    public  List<TradingPairs> getAllTradingPairs(){
        return dashboardMapper.getAllTradingPairsAvail();
    }

    @Override
    public TradingPairs getTradingPairbyId(String PairID){
        return dashboardMapper.getTradingPairbyId(PairID);
    }

    @Override
    public AssetsAvail getAssetById(String assetID){
        return dashboardMapper.getAssetByid(assetID);
    }


}
