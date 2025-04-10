package capstone.SportyUp.SportyUp_Server.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class GoogleConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
