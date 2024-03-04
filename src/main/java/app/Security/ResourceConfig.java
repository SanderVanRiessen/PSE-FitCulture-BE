package app.Security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST", "PUT", "GET", "DELETE")
                .allowedOriginPatterns("http://localhost:*")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://fe-app-production-343a.up.railway.app");
    }

}
