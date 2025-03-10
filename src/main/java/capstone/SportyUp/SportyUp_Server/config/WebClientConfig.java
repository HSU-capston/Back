package capstone.SportyUp.SportyUp_Server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://127.0.0.1:5000") // Flask 서버의 기본 URL을 설정합니다.
                .build();
    }
}