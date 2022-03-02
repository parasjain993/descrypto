package deshaw.dae.descrypto.cache;

import deshaw.dae.descrypto.domain.AssetDetails;

import java.util.HashMap;
import java.util.Map;

public class DashboardCache {
    public Map<String, AssetDetails> TokenCache = new HashMap<String, AssetDetails>();

    private static  DashboardCache single_instance = null;
    private DashboardCache(){
        TokenCache = new HashMap<>();
    }
    public static DashboardCache getDashboardCache()
    {
        if (single_instance == null)
            single_instance = new DashboardCache();

        return single_instance;
    }

    public Map<String, AssetDetails> TokenCache() {
        return TokenCache;
    }

    public void setTokenCache(Map<String, AssetDetails> tokenCache) {
        TokenCache = tokenCache;
    }

    public void addTokenDetails(String tokenID, AssetDetails assetDetails){
        TokenCache.put(tokenID, assetDetails);
    }

}
