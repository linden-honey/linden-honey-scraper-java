package com.github.lindenhoney.scraper.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class RestConfiguration implements WebFluxConfigurer {
    @Override
    public void configurePathMatching(PathMatchConfigurer config) {
        config.setUseCaseSensitiveMatch(false)
                .setUseTrailingSlashMatch(false)
                .addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
