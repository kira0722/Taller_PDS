package co.com.poli.showtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ShowtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowtimeApplication.class, args);
	}

}
