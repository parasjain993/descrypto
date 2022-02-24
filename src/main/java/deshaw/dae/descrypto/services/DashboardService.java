package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.AssetDetails;

import java.util.List;


public interface DashboardService {
    List<AssetDetails> getCoinDetails(List<String> CoinIds);
    AssetDetails getCoinDetailsByID(String CoinId);
}
