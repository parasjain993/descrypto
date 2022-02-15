package deshaw.dae.descrypto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("deshaw.dae.descrypto.mappers")
public class DescryptoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DescryptoApplication.class, args);
	}

}
