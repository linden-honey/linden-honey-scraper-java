package com.github.lindenhoney.scraper.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Validated
@ConfigurationProperties("application.scraper")
@Data
public class ScraperProperties {

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
