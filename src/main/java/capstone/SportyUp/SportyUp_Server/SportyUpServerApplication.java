package capstone.SportyUp.SportyUp_Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SportyUpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportyUpServerApplication.class, args);
	}

}
