package com.github.lindenhoney.scraper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "linden-honey")
public class ApplicationProperties {

    @Valid
    private final Map<String, ScraperProperties> scrapers = new HashMap<>();

    @Getter
    @Setter
    public static class ScraperProperties {

        @NotBlank
        private String baseUrl;

        @Valid
        private final RetryProperties retry = new RetryProperties();

        @Getter
        @Setter
        public static class RetryProperties {
            private int maxRetries;
            private Duration firstBackoff;
            private Duration maxBackoff;
        }
    }
}
