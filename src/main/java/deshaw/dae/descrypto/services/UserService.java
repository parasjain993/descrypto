package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.User;

public interface UserService {
    void addUser(User user);
    User findByUsername(String username);
}

