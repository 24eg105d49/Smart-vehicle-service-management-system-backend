package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration.
 *
 * Configured to seamlessly allow requests from all Vercel domains (production,
 * preview, branch deployments), local development, and any explicitly configured
 * origin in ALLOWED_ORIGIN.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.allowed-origin:http://localhost:3000}")
    private String allowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> patterns = new ArrayList<>();

        // Always allow localhost
        patterns.add("http://localhost:3000");
        patterns.add("http://localhost:*");

        // Automatically allow any Vercel deployment (preview or production)
        patterns.add("https://*.vercel.app");
        patterns.add("https://vercel.app");

        // Allow explicitly configured origin, sanitized of trailing slashes
        if (allowedOrigin != null && !allowedOrigin.trim().isEmpty() && !"*".equals(allowedOrigin.trim())) {
            String sanitized = allowedOrigin.trim().replaceAll("/+$", "");
            if (!patterns.contains(sanitized)) {
                patterns.add(sanitized);
            }
        }

        registry.addMapping("/**")
                .allowedOriginPatterns(patterns.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

