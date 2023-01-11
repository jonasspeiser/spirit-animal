package dec.team3.spiritanimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpiritanimalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiritanimalApplication.class, args);
	}

}
