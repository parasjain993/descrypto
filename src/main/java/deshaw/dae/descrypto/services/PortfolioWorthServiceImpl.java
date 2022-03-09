package deshaw.dae.descrypto.services;

import deshaw.dae.descrypto.domain.User;
import deshaw.dae.descrypto.mappers.PortfolioWorth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
public class PortfolioWorthServiceImpl implements PortfolioWorthService{
    @Autowired
    private PortfolioWorth portfolioWorth;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Override
    public void updatePortfolioWorth() {
        List<User> users = userService.getAllUsers();
        for(User user: users) {
            float totalWorth  = walletService.totalWorthCalc(user.getUserId());
            portfolioWorth.updatePortfolioWorth(totalWorth, user.getUserId());
        }
    }
}
