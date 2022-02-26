package deshaw.dae.descrypto.mappers;

import deshaw.dae.descrypto.domain.User;
import org.apache.ibatis.annotations.Mapper;
import deshaw.dae.descrypto.domain.Order;


@Mapper
<<<<<<< HEAD:src/main/java/deshaw/dae/descrypto/mappers/UserMapper.java
public interface UserMapper {
    void addUser(User user);
    User findByFullUsername(String username);
=======
public interface MyMapper {

    List<User> findAllUsers();
    
>>>>>>> 812a7c451dd50b272bfa9cb080d09f26f219ed6d:src/main/java/deshaw/dae/descrypto/mappers/MyMapper.java
}


