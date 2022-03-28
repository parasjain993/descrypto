package deshaw.dae.descrypto;

import deshaw.dae.descrypto.cache.DashboardCache;
import deshaw.dae.descrypto.controllers.OrderControllers.Market;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.PathSelectors.regex;


@EnableWebMvc
@SpringBootApplication
@EnableSwagger2
@MapperScan("deshaw.dae.descrypto.mappers")
public class DescryptoApplication {
	public static HashMap<String,Market> markets=new HashMap<>();
	public static void main(String[] args) {

			SpringApplication.run(DescryptoApplication.class, args);
			 Market market1=new Market();
			 market1.initiate("ethusdt");
			 Market market2=new Market();
			 market2.initiate("btccad");
			 Market market3=new Market();
			 market3.initiate("btcusdt");


	}

}
