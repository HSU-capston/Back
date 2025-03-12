package capstone.SportyUp.SportyUp_Server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${local-path.result}")
    private String resultPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/processed-files/**")
                .addResourceLocations("file:"+resultPath);
//        registry.addResourceHandler("/Test/**") 바탕화면에서 테스트
//                .addResourceLocations("file:C:/Users/EliteBook/Desktop/");
    }
}
