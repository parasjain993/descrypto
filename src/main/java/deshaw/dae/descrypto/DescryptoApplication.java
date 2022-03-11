package deshaw.dae.descrypto;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.controllers.OrderControllers.Market;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;


@SpringBootApplication
@MapperScan("deshaw.dae.descrypto.mappers")
public class DescryptoApplication {
	public static HashMap<String,Market> markets=new HashMap<>();
	public static void main(String[] args) {

			SpringApplication.run(DescryptoApplication.class, args);
			 Market market1=new Market();
			 market1.initiate("usdtusd");
			 Market market2=new Market();
			 market2.initiate("btccad");


	}
}
