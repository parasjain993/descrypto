package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.AssetsAvail;
import deshaw.dae.descrypto.domain.TradingPairs;

import java.util.List;


public interface DashboardService {
    List<AssetDetails> getCoinDetails(List<String> CoinIds);
    AssetDetails getCoinDetailsByID(String CoinId);
    List<AssetsAvail> getAllAssetsAvail();
    List<TradingPairs> getAllTradingPairs();
}
