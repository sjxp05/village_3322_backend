package com.example.rental.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();

            Map<String, Object> dotenvProperties = new HashMap<>();
            for (DotenvEntry entry : dotenv.entries()) {
                dotenvProperties.put(entry.getKey(), entry.getValue());
            }

            if (!dotenvProperties.isEmpty()) {
                environment.getPropertySources()
                        .addFirst(new MapPropertySource("dotenvProperties", dotenvProperties));
            }
        } catch (Exception e) {
            // Silently ignore if .env file doesn't exist or can't be loaded
        }
    }
}
