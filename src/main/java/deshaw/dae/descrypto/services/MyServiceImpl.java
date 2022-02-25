package deshaw.dae.descrypto.services;
import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.mappers.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyServiceImpl implements MyService {

    int a = 1;

    // Cache to store coin details

    @Autowired
    private MyMapper mapper;

    @Override
    public List<User> findAllUsers() {
        return mapper.findAllUsers();
    }
}
