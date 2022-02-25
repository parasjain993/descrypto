package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.TokenDetails;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import java.util.List;


public interface MyService {

    List<User> findAllUsers();

    List<TokenDetails> getCoinDetails(List<String> CoinIds);
    TokenDetails getCoinDetailsByID(String CoinId);

}
