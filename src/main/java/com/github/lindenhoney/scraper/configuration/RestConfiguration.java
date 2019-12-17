package com.github.lindenhoney.scraper.configuration;

import com.github.lindenhoney.scraper.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableConfigurationProperties(RestProperties.class)
@Configuration
@RequiredArgsConstructor
public class RestConfiguration implements WebFluxConfigurer {

    private final RestProperties properties;

    @Override
    public void configurePathMatching(PathMatchConfigurer config) {
        config.setUseCaseSensitiveMatch(false)
                .setUseTrailingSlashMatch(false)
                .addPathPrefix(properties.getBasePath(), HandlerTypePredicate.forBasePackageClass(Application.class));
    }
}
