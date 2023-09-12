package com.indocyber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.rmi.server.RMISocketFactory;

@SpringBootApplication
@EnableCaching
public class TrollMarketApplication {

	public static void main(String[] args) throws IOException {

//		RMISocketFactory.setSocketFactory(RMISocketFactory.getDefaultSocketFactory());
		SpringApplication.run(TrollMarketApplication.class, args);
	}

}
