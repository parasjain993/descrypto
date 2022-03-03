package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.mappers.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{
    @Autowired
    private WalletMapper walletMapper;


    @Override
    public int getAssetCoins(String walletId, String assetName) {
        return walletMapper.getAssetCoins(walletId, assetName);
    }
}
