package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.AssetDetails;
import deshaw.dae.descrypto.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void addUser(User user);
    List<User> getAllUsers();

   // void setPNL(float v, String walletId);

    User findByUserName(String userName);

}

