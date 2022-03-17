package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.AssetsAvail;
import deshaw.dae.descrypto.domain.TradingPairs;

import java.util.List;


public interface DashboardService {
    List<AssetDetails> getCoinDetails();
    AssetDetails getCoinDetailsByID(String CoinId);
    List<AssetsAvail> getAllAssetsAvail();
    List<TradingPairs> getAllTradingPairs();
    List<String> getAllTradingPairID();
    TradingPairs getTradingPairbyId(String PairID);
    AssetsAvail getAssetById(String assetID);
}
