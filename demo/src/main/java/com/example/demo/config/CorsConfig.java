package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration.
 *
 * In local development the per-controller @CrossOrigin(origins =
 * "http://localhost:3000") annotations are still present and take effect
 * (they are not removed so local dev keeps working without any env var).
 *
 * In production (spring.profiles.active=prod) this bean reads the Vercel
 * origin from the ALLOWED_ORIGIN environment variable and allows it across
 * every endpoint, so the individual @CrossOrigin annotations on each
 * controller no longer need to be updated one by one.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * The origin allowed in the production profile.
     * Falls back to http://localhost:3000 so the bean is harmless locally
     * even when no env var is set.
     */
    @Value("${app.allowed-origin:http://localhost:3000}")
    private String allowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin, "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
