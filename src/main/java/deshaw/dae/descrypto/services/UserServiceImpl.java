package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.mappers.UserMapper;
import deshaw.dae.descrypto.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper usermapper;
    @Override
    public User findByFullUsername(String name){
        return usermapper.findByFullUsername(name);
    }

    @Override
    public void addUser(User user) {
        usermapper.addUser(user);
    }
}
