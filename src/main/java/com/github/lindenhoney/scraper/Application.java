package com.github.lindenhoney.scraper;

import com.github.lindenhoney.scraper.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication(scanBasePackages = {"com.github.lindenhoney.scraper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

