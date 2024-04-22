package app.security;

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
                .allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://fitculture.tech", "https://www.fitculture.tech", "https://fitculture-fe-694aa31362b7.herokuapp.com");
    }
}
