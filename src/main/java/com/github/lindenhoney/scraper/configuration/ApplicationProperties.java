package com.github.lindenhoney.scraper.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @Valid
    private final Map<String, ScraperProperties> scrapers = new HashMap<>();
}
