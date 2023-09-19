package com.indocyber;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
@EnableEncryptableProperties
public class TrollMarketApplication {

	public static void main(String[] args) throws IOException {

//		RMISocketFactory.setSocketFactory(RMISocketFactory.getDefaultSocketFactory());
		SpringApplication.run(TrollMarketApplication.class, args);
	}

}
