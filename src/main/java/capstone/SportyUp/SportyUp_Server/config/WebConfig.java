package capstone.SportyUp.SportyUp_Server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/processed-files/**")
                .addResourceLocations("file:C:/Users/EliteBook/Documents/GitHub/Back/src/main/resources/cam_after_flask/");
    }
}
