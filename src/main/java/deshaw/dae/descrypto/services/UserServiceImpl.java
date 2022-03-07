package deshaw.dae.descrypto.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.PriceResponse.PriceResponse;
import deshaw.dae.descrypto.domain.Summary24hResponse.Summary24h;
import deshaw.dae.descrypto.domain.Wallet;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper usermapper;

    private DashboardCache TokenCache = DashboardCache.getDashboardCache();

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public User findByFullUsername(String name){
        return usermapper.findByFullUsername(name);
    }
    public String getWalletId(int userId){
        return usermapper.findByUserId(userId).getWalletId();
    }
    @Override
    public List<User> getAllUsers() {
        return usermapper.getAllUsers();
    }

    @Override
    public void setPNL(float v, String walletId) {
        usermapper.setPNL(v, walletId);
    }

    @Override
    public void addUser(User user) {
        usermapper.addUser(user);
    }
}
